package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

            Member member = new Member();
            member.setUsername("Member1");
            member.setTeamId(team.getId());
            entityManager.persist(member);

            //조회
            Member findMember = entityManager.find(Member.class, member.getId());
            //teamId 가져오기
            Long findTeamId = findMember.getTeamId();
            //연관관계가 없음
            Team findTeam = entityManager.find(Team.class, findTeamId);

            entityTransaction.commit(); // [트랜잭션] 커밋
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
