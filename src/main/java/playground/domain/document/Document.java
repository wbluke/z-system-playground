package playground.domain.document;

import lombok.Getter;

import java.util.List;

@Getter
public class Document {

    private Long id;

    private String title;
    private Category category;
    private String contents;

    private ApprovalState approvalState;

    private List<DocumentApproval> documentApprovals;

}
