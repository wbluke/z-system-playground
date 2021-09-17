package playground.web.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import playground.service.document.DocumentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        MvcResult mvcResult = mockMvc
                .perform(get("/api/documents/outbox")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        // then
        Exception resolvedException = mvcResult.getResolvedException();
        assertThat(resolvedException.getMessage()).contains("기안자 id는 필수입니다.");
    }

}
