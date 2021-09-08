package playground.service.team.dto;

import lombok.Getter;
import playground.domain.team.Team;

@Getter
public class TeamResponseDto {

    private final Long id;
    private final String name;

    public TeamResponseDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }

}
