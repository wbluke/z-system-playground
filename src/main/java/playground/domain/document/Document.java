package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Document {

    private Long id;

    private String title;
    private Category category;
    private String contents;

    private ApprovalState approvalState;
    private User drafter;

    private final List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Builder
    private Document(Long id, String title, Category category, String contents, ApprovalState approvalState, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approvalState = approvalState;
        this.drafter = drafter;
    }

}
