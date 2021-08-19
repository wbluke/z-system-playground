package playground.domain.document;

import lombok.Getter;
import playground.domain.user.User;

@Getter
public class DocumentApproval {

    private Long id;

    private Document document;
    private User user;

    private Integer approvalOrder;
    private String approvalComment;

}
