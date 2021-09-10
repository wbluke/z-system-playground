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
import playground.service.document.dto.DocumentResponseDto;
import playground.service.document.dto.DocumentTitleResponseDto;
import playground.web.document.dto.DocumentCreateRequestDto;
import playground.web.document.dto.DocumentInboxRequestDto;
import playground.web.document.dto.DocumentOutboxRequestDto;

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

    public List<DocumentTitleResponseDto> findOutboxDocuments(DocumentOutboxRequestDto requestDto) {
        User drafter = findUserById(requestDto.getDrafterId());
        List<Document> outboxDocuments = documentRepository.findByDrafterAndApprovalState(drafter, DRAFTING);
        return convertTitleDtoFrom(outboxDocuments);
    }

    public List<DocumentTitleResponseDto> findInboxDocuments(DocumentInboxRequestDto requestDto) {
        User approver = findUserById(requestDto.getApproverId());
        List<DocumentApproval> inboxDocumentApprovals = documentApprovalRepository.findByApproverAndApprovalState(approver, DRAFTING);

        List<Document> inboxDocuments = inboxDocumentApprovals.stream()
                .map(DocumentApproval::getDocument)
                .collect(Collectors.toList());

        return convertTitleDtoFrom(inboxDocuments);
    }

    public DocumentResponseDto findDocument(Long documentId) {
        Document document = findDocumentById(documentId);
        User drafter = document.getDrafter();

        return new DocumentResponseDto(document, drafter);
    }

    @Transactional
    public void create(DocumentCreateRequestDto requestDto) {
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

    private List<DocumentTitleResponseDto> convertTitleDtoFrom(List<Document> documents) {
        return documents.stream()
                .map(DocumentTitleResponseDto::new)
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
