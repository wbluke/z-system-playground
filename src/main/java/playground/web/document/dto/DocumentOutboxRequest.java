package playground.web.document.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DocumentOutboxRequest {

    @NotNull(message = "기안자 지정은 필수입니다.")
    private Long drafterId; // TODO: 2021/08/22 extract userId to loginMember

    @Builder
    private DocumentOutboxRequest(Long drafterId) {
        this.drafterId = drafterId;
    }

}
