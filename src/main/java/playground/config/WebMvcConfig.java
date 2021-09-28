package playground.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import playground.config.auth.JwtTokenProvider;
import playground.config.interceptor.AuthInterceptor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwtTokenProvider))
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index.html", "/css/**", "/fonts/**", "/img/**", "/js/**")
                .excludePathPatterns("/api/login/**");
    }

}
