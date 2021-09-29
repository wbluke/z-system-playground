package learning;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String name;

    @Builder
    private User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
