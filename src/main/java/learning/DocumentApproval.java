package learning;

import lombok.Builder;

public class DocumentApproval {

    private Document document;
    private User approver;
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;

    @Builder
    private DocumentApproval(Document document, User approver, ApprovalState approvalState, Integer approvalOrder, String approvalComment) {
        this.document = document;
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

}
