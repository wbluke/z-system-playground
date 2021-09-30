package learning;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static learning.ApprovalState.APPROVED;
import static learning.ApprovalState.DRAFTING;

@Getter
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private ApprovalState approvalState = DRAFTING;
    private User drafter;
    private final DocumentApprovals documentApprovals = new DocumentApprovals();

    @Builder
    private Document(Long id, String title, Category category, String contents, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
    }

    public void addApprovers(List<User> users) {
        for (int index = 0; index < users.size(); index++) {
            DocumentApproval documentApproval = createDocumentApproval(users.get(index), index + 1);
            documentApprovals.add(documentApproval);
        }
    }

    public void approveBy(User approver, String approvalComment) {
        documentApprovals.approveBy(approver, approvalComment);

        if (documentApprovals.areAllApproved()) {
            this.approvalState = APPROVED;
        }
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals.getApprovals();
    }

    private DocumentApproval createDocumentApproval(User user, int approvalOrder) {
        return DocumentApproval.builder()
                .approver(user)
                .approvalOrder(approvalOrder)
                .build();
    }

}
