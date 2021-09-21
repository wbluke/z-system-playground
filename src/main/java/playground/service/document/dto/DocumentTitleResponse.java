package playground.service.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DocumentTitleResponse {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;
    private LocalDateTime createdDateTime;

    public DocumentTitleResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.approvalState = document.getApprovalState();
        this.createdDateTime = document.getCreatedDateTime();
    }

    public String getCategoryText() {
        return category.getText();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
