package playground.service.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.service.user.dto.UserResponse;

import java.util.List;

@Getter
@NoArgsConstructor
public class DocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private ApprovalState approvalState;
    private UserResponse drafter;
    private List<DocumentApprovalResponse> approvers;

    public DocumentResponse(Document document, List<DocumentApprovalResponse> approvers) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.contents = document.getContents();
        this.approvalState = document.getApprovalState();

        this.drafter = new UserResponse(document.getDrafter());
        this.approvers = approvers;
    }

    public String getCategoryText() {
        return category.getText();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
