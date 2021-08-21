package playground.service.document.dto;

import lombok.NoArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

@NoArgsConstructor
public class DocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private ApprovalState approvalState;

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.contents = document.getContents();
        this.approvalState = document.getApprovalState();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
