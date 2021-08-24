package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.dao.document.DocumentApprovalDao;
import playground.dao.document.DocumentDao;
import playground.dao.document.dto.DocumentTitleResponseDto;
import playground.dao.user.UserDao;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.user.User;
import playground.service.document.dto.DocumentResponseDto;
import playground.web.document.dto.DocumentCreateRequestDto;
import playground.web.document.dto.DocumentOutboxRequestDto;

import java.util.List;
import java.util.stream.Collectors;

import static playground.domain.document.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentDao documentDao;
    private final DocumentApprovalDao documentApprovalDao;
    private final UserDao userDao;

    public List<DocumentTitleResponseDto> findOutboxDocuments(DocumentOutboxRequestDto requestDto) {
        List<Document> outboxDocuments = documentDao.findStateDocumentsByDrafterId(requestDto.getDrafterId(), DRAFTING);
        return convertTitleDtoFrom(outboxDocuments);
    }

    public DocumentResponseDto findDocument(Long documentId) {
        Document document = documentDao.findById(documentId);
        User drafter = userDao.findById(document.getDrafterId());

        return new DocumentResponseDto(document, drafter);
    }

    @Transactional
    public void create(DocumentCreateRequestDto requestDto) {
        Document document = requestDto.toEntity();
        Long documentId = documentDao.save(document);

        List<Long> approverIds = requestDto.getApproverIds();
        for (int approvalOrder = 0; approvalOrder < approverIds.size(); approvalOrder++) {
            DocumentApproval documentApproval = DocumentApproval.builder()
                    .documentId(documentId)
                    .approvalState(DRAFTING)
                    .approverId(approverIds.get(approvalOrder))
                    .approvalOrder(approvalOrder)
                    .build();
            documentApprovalDao.save(documentApproval);
        }
    }

    private List<DocumentTitleResponseDto> convertTitleDtoFrom(List<Document> documents) {
        return documents.stream()
                .map(DocumentTitleResponseDto::new)
                .collect(Collectors.toList());
    }

}
