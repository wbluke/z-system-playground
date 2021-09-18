package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentApprovalRepository extends JpaRepository<DocumentApproval, Long> {

    @Query("select da from DocumentApproval da where da.approver.id = :approverId and da.approvalState = :approvalState")
    List<DocumentApproval> findByApproverIdAndApprovalState(
            @Param("approverId") Long approverId,
            @Param("approvalState") ApprovalState approvalState
    );

}
