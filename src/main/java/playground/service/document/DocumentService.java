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

    public List<DocumentTitleResponse> findInboxDocuments(LoginUser loginUser) {
        List<Document> inboxDocuments = documentApprovalRepository.findOrderedDocumentsOf(loginUser.getId(), DRAFTING);
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
    public void create(DocumentCreateRequest requestDto, LoginUser loginUser) {
        User drafter = findUserById(loginUser.getId());

        List<Long> approverIds = requestDto.getApproverIds();
        List<User> approvers = userRepository.findAllById(approverIds);

        Document document = requestDto.toEntity(drafter, approvers);
        documentRepository.save(document);
    }

    private List<DocumentTitleResponse> convertTitleDtoFrom(List<Document> documents) {
        return documents.stream()
                .map(DocumentTitleResponse::new)
                .collect(Collectors.toList());
    }

    private Document findDocumentById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("???????????? ?????? ???????????????. documentId = %s", documentId)));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("???????????? ?????? ??????????????????. userId = %s", userId)));
    }

}
