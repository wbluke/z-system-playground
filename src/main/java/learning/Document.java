package learning;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private ApprovalState approvalState;
    private User drafter;
    private final List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Builder
    private Document(Long id, String title, Category category, String contents, ApprovalState approvalState, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approvalState = approvalState;
        this.drafter = drafter;
    }

    public void addApprovers(List<User> users) {
        for (int index = 0; index < users.size(); index++) {
            DocumentApproval documentApproval = createDocumentApproval(users.get(index), index + 1);
            documentApprovals.add(documentApproval);
        }
    }

    public void approveBy(User user2) {

    }

    private DocumentApproval createDocumentApproval(User user, int approvalOrder) {
        return DocumentApproval.builder()
                .approver(user)
                .approvalState(ApprovalState.DRAFTING)
                .approvalOrder(approvalOrder)
                .build();
    }

}
