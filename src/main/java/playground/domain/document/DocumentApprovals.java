package playground.domain.document;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class DocumentApprovals {

    @OneToMany(mappedBy = "document", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final List<DocumentApproval> approvals = new ArrayList<>();

    public void add(DocumentApproval approval) {
        this.approvals.add(approval);
    }

}
