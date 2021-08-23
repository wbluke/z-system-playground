package playground.dao.document;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.dao.document.dto.DocumentTitleResponseDto;
import playground.dao.user.UserDao;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DocumentDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public Document findById(Long id) {
        String sql = "select * from document where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> Document.builder()
                        .id(id)
                        .title(rs.getString("title"))
                        .category(Category.valueOf(rs.getString("category")))
                        .contents(rs.getString("contents"))
                        .drafterId(rs.getLong("drafter_id"))
                        .approvalState(ApprovalState.valueOf(rs.getString("approval_state")))
                        .build(),
                id
        );
    }

    public List<DocumentTitleResponseDto> findStateDocumentsByDrafterId(Long drafterId, ApprovalState approvalState) {
        String sql = "select * from document where drafter_id = ? and approval_state = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> DocumentTitleResponseDto.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .category(Category.valueOf(rs.getString("category")))
                        .approvalState(ApprovalState.valueOf(rs.getString("approval_state")))
                        .build(),
                drafterId,
                approvalState.name()
        );
    }

    public Long save(Document document) {
        return null;
    }

}
