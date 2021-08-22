package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playground.dao.document.DocumentDao;
import playground.dao.document.dto.DocumentTitleResponseDto;
import playground.domain.document.ApprovalState;
import playground.domain.document.Document;
import playground.service.document.dto.DocumentResponseDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentDao documentDao;

    public List<DocumentTitleResponseDto> findOutboxDocuments(Long userId) {
        return documentDao.findStateDocumentsByDrafterId(userId, ApprovalState.DRAFTING);
    }

    public DocumentResponseDto findDocument(Long documentId) {
        Document document = documentDao.findById(documentId);
        return new DocumentResponseDto(document);
    }

}
