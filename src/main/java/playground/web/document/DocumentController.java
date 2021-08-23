package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.dao.document.dto.DocumentTitleResponseDto;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentResponseDto;
import playground.web.document.dto.DocumentCreateRequestDto;
import playground.web.document.dto.DocumentOutboxRequestDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentTitleResponseDto>> findOutboxDocuments(DocumentOutboxRequestDto requestDto) {
        return ResponseEntity.ok(documentService.findOutboxDocuments(requestDto));
    }

    @GetMapping("/documents/{documentId}")
    public ResponseEntity<DocumentResponseDto> findDocument(@PathVariable Long documentId) {
        return ResponseEntity.ok(documentService.findDocument(documentId));
    }

    @PostMapping("/documents")
    public ResponseEntity<Object> createDocument(DocumentCreateRequestDto requestDto) {
        documentService.create(requestDto);
        return ResponseEntity.ok().build();
    }

}
