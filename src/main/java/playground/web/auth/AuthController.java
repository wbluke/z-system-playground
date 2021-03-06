package playground.web.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.config.auth.AuthorizationExtractor;
import playground.service.auth.AuthService;
import playground.service.auth.dto.LoginTokenResponse;
import playground.web.auth.dto.LoginRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login")
    public ResponseEntity<LoginTokenResponse> login(@Valid @RequestBody LoginRequest request) {
        log.debug("login request : {}", request);
        LoginTokenResponse loginTokenResponse = authService.login(request);

        return ResponseEntity.ok(loginTokenResponse);
    }

    @GetMapping("/api/login/token")
    public ResponseEntity<LoginTokenResponse> checkLogin(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        if (authService.isValidToken(token)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
