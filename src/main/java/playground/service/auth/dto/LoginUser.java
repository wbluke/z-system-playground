package playground.service.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.team.Team;
import playground.domain.user.JobPosition;
import playground.domain.user.User;

@Getter
@NoArgsConstructor
public class LoginUser {

    private Long id;
    private String email;
    private String name;
    private Team team;
    private JobPosition jobPosition;

    @Builder
    private LoginUser(Long id, String email, String name, Team team, JobPosition jobPosition) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.team = team;
        this.jobPosition = jobPosition;
    }

    public static LoginUser of(User user) {
        return LoginUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .team(user.getTeam())
                .jobPosition(user.getJobPosition())
                .build();
    }

}
