package playground.dao.document.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;

@Getter
@NoArgsConstructor
public class DocumentTitleResponseDto {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;

    @Builder
    private DocumentTitleResponseDto(Long id, String title, Category category, ApprovalState approvalState) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.approvalState = approvalState;
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
