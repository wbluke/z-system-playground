package playground.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.BaseTimeEntity;
import playground.domain.user.User;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

import static playground.domain.document.ApprovalState.APPROVED;
import static playground.domain.document.ApprovalState.CANCELED;
import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Document extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalState approvalState;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User drafter;

    @Embedded
    private final DocumentApprovals documentApprovals = new DocumentApprovals();

    @Builder
    private Document(String title, Category category, String contents, User drafter, List<User> approvers) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;

        addApprovers(approvers);
        this.approvalState = decideDocumentState();
    }

    public void approveBy(User approver, String approvalComment) {
        if (approvalState.isCompleted()) {
            throw new IllegalArgumentException("이미 처리가 완료된 문서입니다.");
        }

        documentApprovals.approveBy(approver, approvalComment);
        this.approvalState = decideDocumentState();
    }

    public void addDocumentApproval(DocumentApproval documentApproval) {
        this.documentApprovals.add(documentApproval);
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals.getApprovals();
    }

    private void addApprovers(List<User> approvers) {
        for (int index = 0; index < approvers.size(); index++) {
            DocumentApproval documentApproval = createDocumentApproval(approvers.get(index), index + 1);
            documentApprovals.add(documentApproval);
        }
    }

    private DocumentApproval createDocumentApproval(User user, int approvalOrder) {
        DocumentApproval approval = DocumentApproval.builder()
                .approver(user)
                .approvalOrder(approvalOrder)
                .build();
        approval.updateDocument(this);
        return approval;
    }

    private ApprovalState decideDocumentState() {
        if (documentApprovals.hasAllApproved()) {
            return APPROVED;
        }

        if (documentApprovals.hasCanceled()) {
            return CANCELED;
        }

        return DRAFTING;
    }

}
