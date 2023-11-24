package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin(); // [트랜잭션] 시작
        //엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.

        try {
            Team team = new Team();
            team.setName("TeamA");
            entityManager.persist(team);

            Team team2 = new Team();
            team2.setName("TeamB");
            entityManager.persist(team2);

            Member member = new Member();
            member.setUsername("Member1");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            //조회
            Member findMember = entityManager.find(Member.class, member.getId()); // jpa가 member와 team을 join해 한번에 다 가져옴
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            entityTransaction.commit(); // [트랜잭션] 커밋
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
