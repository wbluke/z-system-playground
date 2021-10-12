package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentApprovalRepository extends JpaRepository<DocumentApproval, Long> {

    @Query("select d from DocumentApproval da inner join da.document d" +
            " where da.approver.id = :approverId and da.approvalState = :approvalState")
    List<Document> findDocumentsOf(
            @Param("approverId") Long approverId,
            @Param("approvalState") ApprovalState approvalState
    );

}
