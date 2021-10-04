package playground.service.document;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.domain.user.JobPosition;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.auth.dto.LoginUser;
import playground.service.document.dto.DocumentTitleResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static playground.domain.document.ApprovalState.APPROVED;
import static playground.domain.document.ApprovalState.DRAFTING;
import static playground.domain.document.Category.EDUCATION;
import static playground.domain.user.JobPosition.TEAM_MEMBER;

@SpringBootTest
class DocumentServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @AfterEach
    void tearDown() {
        documentRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("기안자 id와 결재상태로 문서를 역순 조회한다.")
    @Test
    void findOutboxDocuments() {
        // given
        User user1 = userRepository.save(createUser("wbluke@abc.com", "p@ssw0rd", TEAM_MEMBER));
        User user2 = userRepository.save(createUser("wbluke2@abc.com", "p@ssw0rd", TEAM_MEMBER));

        Document document1 = createDocument("title1", EDUCATION, DRAFTING, user1);
        Document document2 = createDocument("title2", EDUCATION, DRAFTING, user1);
        Document document3 = createDocument("title3", EDUCATION, APPROVED, user1);
        Document document4 = createDocument("title4", EDUCATION, DRAFTING, user2);
        documentRepository.saveAll(Arrays.asList(document1, document2, document3, document4));

        LoginUser loginUser = LoginUser.of(user1);

        // when
        List<DocumentTitleResponse> outboxDocuments = documentService.findOutboxDocuments(loginUser);

        // then
        assertThat(outboxDocuments).hasSize(2)
                .extracting("title", "category", "approvalState")
                .containsExactly(
                        tuple("title2", EDUCATION, DRAFTING),
                        tuple("title1", EDUCATION, DRAFTING)
                );
    }

    private User createUser(String email, String password, JobPosition jobPosition) {
        return User.builder()
                .email(email)
                .password(password)
                .jobPosition(jobPosition)
                .build();
    }

    private Document createDocument(String title, Category category, ApprovalState approvalState, User drafter) {
        return Document.builder()
                .title(title)
                .category(category)
                .approvalState(approvalState)
                .drafter(drafter)
                .build();
    }

}
