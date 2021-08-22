package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import playground.dao.document.dto.DocumentTitleResponseDto;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentResponseDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/outbox/{userId}") // TODO: 2021/08/22 extract userId to loginMember
    public ResponseEntity<List<DocumentTitleResponseDto>> findOutboxDocuments(@PathVariable Long userId) {
        return ResponseEntity.ok(documentService.findOutboxDocuments(userId));
    }

    @GetMapping("/documents/{documentId}")
    public ResponseEntity<DocumentResponseDto> findDocument(@PathVariable Long documentId) {
        return ResponseEntity.ok(documentService.findDocument(documentId));
    }

}
