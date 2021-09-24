package playground.service.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginToken {

    private final String token;

    public static LoginToken of(String token) {
        return new LoginToken(token);
    }

}
