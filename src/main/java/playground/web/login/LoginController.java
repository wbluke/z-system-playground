package playground.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.service.login.LoginService;
import playground.service.login.dto.LoginUser;
import playground.web.login.dto.LoginRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static playground.constants.SessionKeys.SESSION_USER;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request, HttpSession httpSession) {
        log.debug("login request : {}", request);
        LoginUser loginUser = loginService.login(request);

        httpSession.setAttribute(SESSION_USER, loginUser);
        log.info("login success : userId = {}", loginUser.getId());

        return ResponseEntity.ok().build();
    }

}
