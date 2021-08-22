package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playground.dao.DocumentDao;
import playground.domain.document.Document;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.DocumentTitleResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentDao documentDao;

    public List<DocumentTitleResponse> findOutboxDocuments() {
        return null;
    }

    public DocumentResponse findDocument(Long documentId) {
        Document document = documentDao.findById(documentId);
        return new DocumentResponse(document);
    }

}
