package playground.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import playground.service.auth.AuthService;

import static org.mockito.Mockito.when;

public abstract class AbstractControllerMockUnitTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    protected void mockLogin() {
        when(authService.isValidToken(null)).thenReturn(true);
    }

}
