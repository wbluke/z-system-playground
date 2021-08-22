package playground.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

@RequiredArgsConstructor
@Repository
public class DocumentDao {

    private final JdbcTemplate jdbcTemplate;

    public Document findById(Long documentId) {
        String sql = "select * from document where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) ->
                        Document.builder()
                                .id(documentId)
                                .title(rs.getString("title"))
                                .category(Category.valueOf(rs.getString("category")))
                                .contents(rs.getString("contents"))
                                .approvalState(ApprovalState.valueOf(rs.getString("approval_state")))
                                .build(),
                documentId
        );
    }

}
