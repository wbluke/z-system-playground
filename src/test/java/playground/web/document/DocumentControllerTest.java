package playground.web.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import playground.config.interceptor.InvalidLoginTokenException;
import playground.service.document.DocumentService;
import playground.web.AbstractControllerMockUnitTest;
import playground.web.document.dto.DocumentCreateRequest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static playground.domain.document.Category.EDUCATION;
import static playground.response.ApiExceptionResponseCode.BAD_PARAMETER;
import static playground.response.ApiExceptionResponseCode.UNAUTHORIZED;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest extends AbstractControllerMockUnitTest {

    @MockBean
    private DocumentService documentService;

    @DisplayName("로그인한 경우 outbox 문서들을 성공적으로 조회한다.")
    @Test
    void findOutboxDocuments() throws Exception {
        // given
        mockLogin();

        // when
        mockMvc
                .perform(get("/api/documents/outbox")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk()); // then
    }

    @DisplayName("outbox 문서 조회 시 로그인하지 않은 상태면 예외 메시지를 반환한다.")
    @Test
    void findOutboxDocuments2() throws Exception {
        // when
        mockMvc
                .perform(get("/api/documents/outbox")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized()) // then
                .andExpect(jsonPath("$.code").value(UNAUTHORIZED.name()))
                .andExpect(jsonPath("$.message").value(InvalidLoginTokenException.DEFAULT_MESSAGE));
    }

    @DisplayName("문서 생성 시 문서 제목이 없으면 예외 메시지를 반환한다.")
    @Test
    void createDocument() throws Exception {
        // given
        mockLogin();

        DocumentCreateRequest request = DocumentCreateRequest.builder()
                .category(EDUCATION)
                .contents("내용")
                .approverIds(Arrays.asList(1L, 2L))
                .build();

        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc
                .perform(post("/api/documents")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError()) // then
                .andExpect(jsonPath("$.code").value(BAD_PARAMETER.name()))
                .andExpect(jsonPath("$.message").value("문서 제목은 필수입니다."));
    }

}
