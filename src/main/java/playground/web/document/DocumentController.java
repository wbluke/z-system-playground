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
import playground.service.document.dto.DocumentResponseDto;
import playground.service.document.dto.DocumentTitleResponseDto;
import playground.web.document.dto.DocumentCreateRequestDto;
import playground.web.document.dto.DocumentOutboxRequestDto;
import playground.web.dto.EnumResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentTitleResponseDto>> findOutboxDocuments(DocumentOutboxRequestDto requestDto) {
        List<DocumentTitleResponseDto> outboxDocumentResponses = documentService.findOutboxDocuments(requestDto);
        return ResponseEntity.ok(outboxDocumentResponses);
    }

    @GetMapping("/documents/{documentId}")
    public ResponseEntity<DocumentResponseDto> findDocument(@PathVariable Long documentId) {
        DocumentResponseDto documentResponse = documentService.findDocument(documentId);
        return ResponseEntity.ok(documentResponse);
    }

    @PostMapping("/documents")
    public ResponseEntity<Object> createDocument(@RequestBody DocumentCreateRequestDto requestDto) {
        documentService.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/documents/category")
    public ResponseEntity<List<EnumResponse<Category>>> getCategories() {
        List<EnumResponse<Category>> responses = Arrays.stream(Category.values())
                .map(EnumResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
