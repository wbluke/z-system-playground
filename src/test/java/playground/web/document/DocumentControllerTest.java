package playground.web.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import playground.service.document.DocumentService;
import playground.web.document.dto.DocumentCreateRequest;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static playground.domain.document.Category.EDUCATION;
import static playground.response.ApiExceptionResponseCode.BAD_PARAMETER;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private DocumentService documentService;

    @DisplayName("outbox 문서들을 성공적으로 조회한다.")
    @Test
    void findOutboxDocuments() throws Exception {
        // when
        mockMvc
                .perform(get("/api/documents/outbox")
                        .param("drafterId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk()); // then
    }

    @DisplayName("outbox 문서 조회 시 기안자 id가 없으면 예외 메시지를 반환한다.")
    @Test
    void findOutboxDocuments2() throws Exception {
        // when
        mockMvc
                .perform(get("/api/documents/outbox")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError()) // then
                .andExpect(jsonPath("$.code").value(BAD_PARAMETER.name()))
                .andExpect(jsonPath("$.message").value("기안자 지정은 필수입니다."));
    }

    @DisplayName("문서 생성 시 문서 제목이 없으면 예외 메시지를 반환한다.")
    @Test
    void createDocument() throws Exception {
        // given
        DocumentCreateRequest request = DocumentCreateRequest.builder()
                .category(EDUCATION)
                .contents("내용")
                .drafterId(1L)
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
