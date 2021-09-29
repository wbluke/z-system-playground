package learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class DocumentTest {

    @DisplayName("문서 생성하기")
    @Test
    void create() {
        // given
        User user1 = createUser(1L, "박우빈1");
        User user2 = createUser(2L, "박우빈2");
        User user3 = createUser(3L, "박우빈3");

        // when
        Document document = createDocument(1L, "제목1", Category.EDUCATION, "내용1", user1);
        document.addApprovers(Arrays.asList(user2, user3));

        // then
        assertThat(document)
                .extracting("title", "category", "contents", "ApprovalState", "drafter.id")
                .containsExactly("제목1", Category.EDUCATION, "내용1", ApprovalState.DRAFTING, user1.getId());

        List<DocumentApproval> documentApprovals = document.getDocumentApprovals();
        assertThat(documentApprovals)
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(
                        tuple(user2.getId(), ApprovalState.DRAFTING, 1, null),
                        tuple(user3.getId(), ApprovalState.DRAFTING, 2, null)
                );
    }

    @DisplayName("문서를 결재하는 경우 내가 결재할 차례면 결재가 성공한다.")
    @Test
    void approveSuccess() {
        // given
        User user1 = createUser(1L, "박우빈1");
        User user2 = createUser(2L, "박우빈2");
        User user3 = createUser(3L, "박우빈3");

        Document document = createDocument(1L, "제목1", Category.EDUCATION, "내용1", user1);
        document.addApprovers(Arrays.asList(user2, user3));

        // when
        document.approveBy(user2);

        // then
        List<DocumentApproval> documentApprovals = document.getDocumentApprovals();
    }

    @DisplayName("문서를 결재하는 경우 내가 마지막 결재할 차례라면 문서의 상태가 결재완료로 변경된다.")
    @Test
    void approveComplete() {
        // given
        User user1 = createUser(1L, "박우빈1");
        User user2 = createUser(2L, "박우빈2");
        User user3 = createUser(3L, "박우빈3");

        Document document = createDocument(1L, "제목1", Category.EDUCATION, "내용1", user1);
        document.addApprovers(Arrays.asList(user2, user3));

        // when
        document.approveBy(user2);

        // then
    }

    @DisplayName("문서를 결재하는 경우 내가 결재할 차례가 아니면 결재가 실패한다.")
    @Test
    void approveFail() {
        // given

        // when

        // then
    }

    private User createUser(Long id, String name) {
        return User.builder()
                .id(id)
                .name(name)
                .build();
    }

    private Document createDocument(Long id, String title, Category category, String contents, User drafter) {
        return Document.builder()
                .id(id)
                .title(title)
                .category(category)
                .contents(contents)
                .approvalState(ApprovalState.DRAFTING)
                .drafter(drafter)
                .build();
    }

}
