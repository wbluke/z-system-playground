package playground.web.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentTitleResponse;

import java.io.IOException;
import java.util.List;

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
        // given

        // when
        ResultActions result = mockMvc
                .perform(get("/api/documents/outbox")
                        .param("drafterId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());

        // then
        MvcResult mvcResult = result.andExpect(status().isOk())
                .andReturn();

        List<DocumentTitleResponse> okData = extractOkData(mvcResult);
        System.out.println(okData);
    }

    private List<DocumentTitleResponse> extractOkData(MvcResult result) throws IOException {
        return objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<DocumentTitleResponse>>() {
                }
        );
    }

}
