package playground.web.document.dto;

import lombok.Getter;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
public class DocumentCreateRequest {

    @NotBlank(message = "문서 제목은 필수입니다.")
    private String title;

    @NotNull(message = "문서 분류는 필수입니다.")
    private Category category;

    private String contents;

    @NotNull(message = "기안자 지정은 필수입니다.")
    private Long drafterId;

    @NotEmpty(message = "결재자 지정은 필수입니다.")
    private List<Long> approverIds;

    public Document toEntity(User drafter) {
        return Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafter(drafter)
                .approvalState(DRAFTING)
                .build();
    }

}
