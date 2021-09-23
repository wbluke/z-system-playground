package playground.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.login.dto.LoginUser;
import playground.service.login.exception.LoginFailException;
import playground.web.login.dto.LoginRequest;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginUser login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailException::new);

        if (user.mismatchPassword(request.getPassword())) {
            throw new LoginFailException();
        }

        return LoginUser.createBy(user);
    }

}
