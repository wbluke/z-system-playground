package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.domain.document.Category;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.DocumentTitleResponse;
import playground.web.document.dto.DocumentCreateRequest;
import playground.web.document.dto.DocumentInboxRequest;
import playground.web.document.dto.DocumentOutboxRequest;
import playground.web.dto.EnumResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/api/documents/outbox")
    public ResponseEntity<List<DocumentTitleResponse>> findOutboxDocuments(DocumentOutboxRequest requestDto) {
        List<DocumentTitleResponse> outboxDocumentResponses = documentService.findOutboxDocuments(requestDto);
        return ResponseEntity.ok(outboxDocumentResponses);
    }

    @GetMapping("/api/documents/inbox")
    public ResponseEntity<List<DocumentTitleResponse>> findInboxDocuments(DocumentInboxRequest requestDto) {
        List<DocumentTitleResponse> inboxDocumentResponses = documentService.findInboxDocuments(requestDto);
        return ResponseEntity.ok(inboxDocumentResponses);
    }

    @GetMapping("/api/documents/{documentId}")
    public ResponseEntity<DocumentResponse> findDocument(@PathVariable Long documentId) {
        DocumentResponse documentResponse = documentService.findDocument(documentId);
        return ResponseEntity.ok(documentResponse);
    }

    @PostMapping("/api/documents")
    public ResponseEntity<Object> createDocument(@RequestBody DocumentCreateRequest requestDto) {
        documentService.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/documents/category")
    public ResponseEntity<List<EnumResponse<Category>>> getCategories() {
        List<EnumResponse<Category>> responses = Arrays.stream(Category.values())
                .map(EnumResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
