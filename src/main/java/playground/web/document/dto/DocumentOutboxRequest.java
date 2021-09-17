package playground.web.document.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DocumentOutboxRequest {

    @NotNull(message = "기안자 id는 필수입니다.")
    private Long drafterId; // TODO: 2021/08/22 extract userId to loginMember

}
