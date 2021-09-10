package playground.web.document.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentInboxRequestDto {

    private Long approverId; // TODO: 2021/08/22 extract userId to loginMember

}
