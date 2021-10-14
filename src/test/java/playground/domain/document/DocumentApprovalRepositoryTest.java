package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import playground.domain.user.User;
import playground.domain.user.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.domain.document.ApprovalState.DRAFTING;
import static playground.domain.document.Category.EDUCATION;
import static playground.domain.user.JobPosition.TEAM_MEMBER;

@DataJpaTest
class DocumentApprovalRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentApprovalRepository documentApprovalRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("조건에 맞는 DocumentApproval의 Document 조회하기")
    @Test
    void findDocumentsOf() {
        // given
        User drafter = createUser("drafter@test.com");
        User approver1 = createUser("approver1@test.com");
        User approver2 = createUser("approver2@test.com");

        Document document1 = createDocument("title1", EDUCATION, drafter, Arrays.asList(approver1, approver2));
        Document document2 = createDocument("title2", EDUCATION, drafter, Arrays.asList(approver1, approver2));
        Document document3 = createDocument("title3", EDUCATION, drafter, Arrays.asList(approver1, approver2));
        document3.approveBy(approver1, null);

        Document document4 = createDocument("title4", EDUCATION, drafter, Arrays.asList(approver1, approver2));
        document4.approveBy(approver1, null);
        document4.approveBy(approver2, null);

        documentRepository.saveAll(Arrays.asList(document1, document2, document3, document4));

        // when
        List<Document> results = documentApprovalRepository.findOrderedDocumentsOf(approver1.getId(), DRAFTING);

        // then
        assertThat(results).hasSize(2)
                .extracting("title")
                .containsExactly(document2.getTitle(), document1.getTitle());
    }

    private User createUser(String email) {
        User user = User.builder()
                .email(email)
                .password("p@ssw0rd")
                .jobPosition(TEAM_MEMBER)
                .build();
        return userRepository.save(user);
    }

    private Document createDocument(String title, Category category, User drafter, List<User> approvers) {
        return Document.builder()
                .title(title)
                .category(category)
                .drafter(drafter)
                .approvers(approvers)
                .build();
    }

}
