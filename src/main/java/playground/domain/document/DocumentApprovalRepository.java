package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.user.User;

import java.util.List;

public interface DocumentApprovalRepository extends JpaRepository<DocumentApproval, Long> {

    List<DocumentApproval> findByApproverAndApprovalState(User approver, ApprovalState approvalState);

}
