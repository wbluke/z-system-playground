package playground.service.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

@Getter
@NoArgsConstructor
public class DocumentTitleResponseDto {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;

    public DocumentTitleResponseDto(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.approvalState = document.getApprovalState();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}