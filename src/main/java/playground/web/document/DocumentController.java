package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.config.auth.Login;
import playground.domain.document.Category;
import playground.service.auth.dto.LoginUser;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.DocumentTitleResponse;
import playground.web.document.dto.DocumentCreateRequest;
import playground.web.document.dto.DocumentInboxRequest;
import playground.web.dto.EnumResponse;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/api/documents/outbox")
    public ResponseEntity<List<DocumentTitleResponse>> findOutboxDocuments(@Login LoginUser loginUser) {
        List<DocumentTitleResponse> outboxDocumentResponses = documentService.findOutboxDocuments(loginUser);
        return ResponseEntity.ok(outboxDocumentResponses);
    }

    @GetMapping("/api/documents/inbox")
    public ResponseEntity<List<DocumentTitleResponse>> findInboxDocuments(@Login LoginUser loginUser) {
        List<DocumentTitleResponse> inboxDocumentResponses = documentService.findInboxDocuments(loginUser);
        return ResponseEntity.ok(inboxDocumentResponses);
    }

    @GetMapping("/api/documents/{documentId}")
    public ResponseEntity<DocumentResponse> findDocument(@PathVariable Long documentId) {
        DocumentResponse documentResponse = documentService.findDocument(documentId);
        return ResponseEntity.ok(documentResponse);
    }

    @PostMapping("/api/documents")
    public ResponseEntity<Object> createDocument(@Valid @RequestBody DocumentCreateRequest requestDto, @Login LoginUser loginUser) {
        documentService.create(requestDto, loginUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/documents/categories")
    public ResponseEntity<List<EnumResponse<Category>>> getCategories() {
        List<EnumResponse<Category>> responses = Arrays.stream(Category.values())
                .map(EnumResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
