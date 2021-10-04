package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.document.DocumentApprovalRepository;
import playground.domain.document.DocumentRepository;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.auth.dto.LoginUser;
import playground.service.document.dto.DocumentApprovalResponse;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.DocumentTitleResponse;
import playground.web.document.dto.DocumentCreateRequest;
import playground.web.document.dto.DocumentInboxRequest;

import java.util.List;
import java.util.stream.Collectors;

import static playground.domain.document.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DocumentApprovalRepository documentApprovalRepository;

    public List<DocumentTitleResponse> findOutboxDocuments(LoginUser loginUser) {
        List<Document> outboxDocuments = documentRepository.findByDrafterIdAndApprovalStateOrderByIdDesc(loginUser.getId(), DRAFTING);
        return convertTitleDtoFrom(outboxDocuments);
    }

    public List<DocumentTitleResponse> findInboxDocuments(DocumentInboxRequest request) {
        List<DocumentApproval> inboxDocumentApprovals = documentApprovalRepository.findByApproverIdAndApprovalState(request.getApproverId(), DRAFTING);

        List<Document> inboxDocuments = inboxDocumentApprovals.stream()
                .map(DocumentApproval::getDocument)
                .collect(Collectors.toList());

        return convertTitleDtoFrom(inboxDocuments);
    }

    public DocumentResponse findDocument(Long documentId) {
        Document document = findDocumentById(documentId);

        List<DocumentApproval> documentApprovals = document.getDocumentApprovals();
        List<DocumentApprovalResponse> approvalResponses = documentApprovals.stream()
                .map(DocumentApprovalResponse::of)
                .sorted()
                .collect(Collectors.toList());

        return new DocumentResponse(document, approvalResponses);
    }

    @Transactional
    public void create(DocumentCreateRequest requestDto) {
        User drafter = findUserById(requestDto.getDrafterId());
        Document document = requestDto.toEntity(drafter);
        Document savedDocument = documentRepository.save(document);

        List<Long> approverIds = requestDto.getApproverIds();
        List<User> approvers = userRepository.findAllById(approverIds);

        for (int index = 0; index < approvers.size(); index++) {
            User approver = approvers.get(index);
            DocumentApproval documentApproval = createDocumentApproval(savedDocument, approver, index);

            documentApprovalRepository.save(documentApproval);
        }
    }

    private List<DocumentTitleResponse> convertTitleDtoFrom(List<Document> documents) {
        return documents.stream()
                .map(DocumentTitleResponse::new)
                .collect(Collectors.toList());
    }

    private Document findDocumentById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 문서입니다. documentId = %s", documentId)));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 사용자입니다. userId = %s", userId)));
    }

    private DocumentApproval createDocumentApproval(Document savedDocument, User approver, int approvalOrder) {
        DocumentApproval documentApproval = DocumentApproval.builder()
                .approvalState(DRAFTING)
                .approver(approver)
                .approvalOrder(approvalOrder)
                .build();
        documentApproval.updateDocument(savedDocument);
        return documentApproval;
    }

}
