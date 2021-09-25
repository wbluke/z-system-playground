package playground.service.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import playground.domain.user.User;

@Getter
@RequiredArgsConstructor
public class LoginTokenResponse {

    private final String token;
    private final String email;
    private final String name;

    public static LoginTokenResponse of(String token, User user) {
        return new LoginTokenResponse(token, user.getEmail(), user.getName());
    }

}
