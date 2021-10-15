package learning;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("playground");
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }

    @DisplayName("Member CRD 테스트")
    @Test
    void crd() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Member member = new Member(1L, "member1", 20);
            em.persist(member);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            em.close();
        }

        EntityManager em2 = emf.createEntityManager();
        EntityTransaction transaction2 = em2.getTransaction();

        try {
            transaction2.begin();

            Member foundMember = em2.find(Member.class, 1L);
            assertThat(foundMember)
                    .extracting("id", "userName", "age")
                    .containsExactly(1L, "member1", 20);

            em2.remove(foundMember);

            transaction2.commit();
        } catch (Exception e) {
            transaction2.rollback();
        } finally {
            em2.close();
        }

    }

    @DisplayName("엔티티의 값을 수정하기만 하면 update 쿼리가 발생한다.")
    @Test
    void update() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();


    }

    @DisplayName("1차 캐시에 저장된 엔티티는 다시 조회해도 같은 인스턴스이다.")
    @Test
    void firstCache() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();


    }

}
