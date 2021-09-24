package playground.web.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ToString
@Getter
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "이메일 형식을 확인해 주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 확인해 주세요.")
    private String password;

}
