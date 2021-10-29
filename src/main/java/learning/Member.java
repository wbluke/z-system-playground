package learning;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    private Long id;

    @Column(name = "name")
    private String userName;
    private Integer age;

    public Member(Long id, String userName, Integer age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public void update(String name, Integer age) {
        this.userName = name;
        this.age = age;
    }

}
