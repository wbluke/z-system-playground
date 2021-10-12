package playground.web.document.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
@NoArgsConstructor
public class DocumentCreateRequest {

    @NotBlank(message = "문서 제목은 필수입니다.")
    private String title;

    @NotNull(message = "문서 분류는 필수입니다.")
    private Category category;

    private String contents;

    @NotEmpty(message = "결재자 지정은 필수입니다.")
    private List<Long> approverIds;

    @Builder
    private DocumentCreateRequest(String title, Category category, String contents, List<Long> approverIds) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approverIds = approverIds;
    }

    public Document toEntity(User drafter, List<User> approvers) {
        return Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafter(drafter)
                .approvalState(DRAFTING)
                .approvers(approvers)
                .build();
    }

}
