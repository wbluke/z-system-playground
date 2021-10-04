package playground.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.config.auth.JwtTokenProvider;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.auth.dto.LoginTokenResponse;
import playground.service.auth.dto.LoginUser;
import playground.service.auth.exception.LoginFailException;
import playground.web.auth.dto.LoginRequest;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginTokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(LoginFailException::new);

        if (user.mismatchPassword(request.getPassword())) {
            throw new LoginFailException();
        }

        log.info("login success : userId = {}", user.getId());
        String token = jwtTokenProvider.createToken(user.getId().toString());
        return LoginTokenResponse.of(token, user);
    }

    public boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public LoginUser findLoginUserByToken(String token) {
        Long userId = Long.valueOf(jwtTokenProvider.getPayload(token));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 정보입니다."));

        return LoginUser.of(user);
    }

}
