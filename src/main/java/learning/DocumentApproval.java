package learning;

import lombok.Builder;
import lombok.Getter;

import static learning.ApprovalState.APPROVED;
import static learning.ApprovalState.DRAFTING;

@Getter
public class DocumentApproval {

    private User approver;
    private ApprovalState approvalState = DRAFTING;
    private Integer approvalOrder;
    private String approvalComment;

    @Builder
    private DocumentApproval(User approver, Integer approvalOrder, String approvalComment) {
        this.approver = approver;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public void approveWith(String approvalComment) {
        this.approvalState = APPROVED;
        this.approvalComment = approvalComment;
    }

    public boolean isApproved() {
        return approvalState.isApproved();
    }

    public boolean isProgressing() {
        return approvalState.isProgressing();
    }

    public boolean isSameApprover(User user) {
        return this.approver.isSame(user);
    }

}
