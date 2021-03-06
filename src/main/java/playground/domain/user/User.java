package playground.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.BaseTimeEntity;
import playground.domain.team.Team;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobPosition jobPosition;

    @Builder
    private User(String email, String password, String name, Team team, JobPosition jobPosition) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.team = team;
        this.jobPosition = jobPosition;
    }

    public boolean mismatchPassword(String password) {
        return !this.password.equals(password);
    }

    public String getTeamName() {
        return this.team.getName();
    }

}
