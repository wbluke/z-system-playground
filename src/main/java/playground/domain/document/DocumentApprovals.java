package playground.domain.document;

import lombok.Getter;
import playground.domain.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Embeddable
public class DocumentApprovals {

    @OneToMany(mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final List<DocumentApproval> approvals = new ArrayList<>();

    public void add(DocumentApproval approval) {
        this.approvals.add(approval);
    }

    public void approveBy(User approver, String approvalComment) {
        DocumentApproval targetApproval = findTargetApproval(approver);
        Integer targetOrder = targetApproval.getApprovalOrder();

        List<DocumentApproval> previousApprovals = findPreviousApprovals(targetOrder);
        if (hasProgressing(previousApprovals)) {
            throw new IllegalArgumentException("현재 결재할 순서가 아닙니다.");
        }

        targetApproval.approve(approvalComment);
    }

    public boolean hasAllApproved() {
        return approvals.stream()
                .allMatch(DocumentApproval::isApproved);
    }

    public boolean hasCanceled() {
        return approvals.stream()
                .anyMatch(DocumentApproval::isCanceled);
    }

    public List<DocumentApproval> getApprovals() {
        return Collections.unmodifiableList(new ArrayList<>(approvals));
    }

    private DocumentApproval findTargetApproval(User approver) {
        return approvals.stream()
                .filter(approval -> approval.isSameApprover(approver))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 문서의 결재 대상자가 아닙니다."));
    }

    private List<DocumentApproval> findPreviousApprovals(Integer targetOrder) {
        return this.approvals.stream()
                .sorted(Comparator.comparing(DocumentApproval::getApprovalOrder))
                .collect(Collectors.toList())
                .subList(0, targetOrder - 1);
    }

    private boolean hasProgressing(List<DocumentApproval> approvals) {
        return approvals.stream()
                .anyMatch(DocumentApproval::isProgressing);
    }

}
