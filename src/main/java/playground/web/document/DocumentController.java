package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentResponseDto;
import playground.service.document.dto.DocumentTitleResponseDto;
import playground.web.document.dto.DocumentCreateRequestDto;
import playground.web.document.dto.DocumentOutboxRequestDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentTitleResponseDto>> findOutboxDocuments(DocumentOutboxRequestDto requestDto) {
        List<DocumentTitleResponseDto> outboxDocumentDtos = documentService.findOutboxDocuments(requestDto);
        return ResponseEntity.ok(outboxDocumentDtos);
    }

    @GetMapping("/documents/{documentId}")
    public ResponseEntity<DocumentResponseDto> findDocument(@PathVariable Long documentId) {
        DocumentResponseDto documentResponseDto = documentService.findDocument(documentId);
        return ResponseEntity.ok(documentResponseDto);
    }

    @PostMapping("/documents")
    public ResponseEntity<Object> createDocument(@RequestBody DocumentCreateRequestDto requestDto) {
        documentService.create(requestDto);
        return ResponseEntity.ok().build();
    }

}
