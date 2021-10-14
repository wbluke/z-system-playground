package playground.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.BaseTimeEntity;
import playground.domain.user.User;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static playground.domain.document.ApprovalState.APPROVED;
import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DocumentApproval extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Document document;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User approver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalState approvalState;

    @Column(nullable = false)
    private Integer approvalOrder;

    private String approvalComment;

    @Builder
    private DocumentApproval(User approver, ApprovalState approvalState, Integer approvalOrder, String approvalComment) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
        this.approvalState = DRAFTING;
    }

    public void updateDocument(Document document) {
        this.document = document;
        document.addDocumentApproval(this);
    }

    public void approve(String approvalComment) {
        this.approvalState = APPROVED;
        this.approvalComment = approvalComment;
    }

    public boolean isSameApprover(User approver) {
        return this.approver.equals(approver);
    }

    public boolean isProgressing() {
        return this.approvalState.isDrafting();
    }

    public boolean isApproved() {
        return this.approvalState.isApproved();
    }

    public boolean isCanceled() {
        return this.approvalState.isCanceled();
    }

}
