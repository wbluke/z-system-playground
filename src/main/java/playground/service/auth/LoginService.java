package playground.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import playground.auth.JwtTokenProvider;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.auth.dto.LoginToken;
import playground.service.auth.exception.LoginFailException;
import playground.web.auth.dto.LoginRequest;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginToken login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailException::new);

        if (user.mismatchPassword(request.getPassword())) {
            throw new LoginFailException();
        }

        log.info("login success : userId = {}", user.getId());
        String token = jwtTokenProvider.createToken(user.getId().toString());
        return LoginToken.of(token);
    }

}
