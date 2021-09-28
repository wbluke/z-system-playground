package playground.config.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import playground.config.auth.AuthorizationExtractor;
import playground.config.auth.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = AuthorizationExtractor.extract(request);

        return jwtTokenProvider.validateToken(token);
    }

}
