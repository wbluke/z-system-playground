package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.user.User;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByDrafterAndApprovalStateOrderByIdDesc(User drafter, ApprovalState approvalState);

}
