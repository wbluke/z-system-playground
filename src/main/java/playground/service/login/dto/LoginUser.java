package playground.service.login.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.User;

import java.io.Serializable;

@Getter
public class LoginUser implements Serializable {

    private Long id;
    private String email;
    private String name;

    @Builder
    private LoginUser(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static LoginUser createBy(User user) {
        return LoginUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}
