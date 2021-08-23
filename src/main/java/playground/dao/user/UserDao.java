package playground.dao.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import playground.domain.user.User;

@RequiredArgsConstructor
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public User findById(Long id) {
        String sql = "select * from user where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> User.builder()
                        .id(id)
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .build(),
                id
        );
    }

}
