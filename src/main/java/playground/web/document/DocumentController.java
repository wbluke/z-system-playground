package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.DocumentTitleResponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentTitleResponse>> findOutboxDocuments() {
        return ResponseEntity.ok(documentService.findOutboxDocuments());
    }

    @GetMapping("/documents/{documentId}")
    public ResponseEntity<DocumentResponse> findDocument(@PathVariable Long documentId) {
        return ResponseEntity.ok(documentService.findDocument(documentId));
    }

}
