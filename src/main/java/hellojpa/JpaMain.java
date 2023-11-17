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

        entityTransaction.begin();

        try {
            // 객체를 생성한 상태 (비영속 상태)
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloA");

            // 객체를 영속성 컨텍스트에 저장한 상태 (영속 상태)
            entityManager.persist(member);

            Member findMember = entityManager.find(Member.class, 1L); // 조회

            findMember.setName("HelloJPA"); // 수정 - 트렌젝션을 commit하는 시점에 변경됨

            entityManager.remove(findMember); // 삭제

            entityTransaction.commit();  // 이 때 DB에 쿼리가 날라감 (영속성 컨텍스트에 있는 애가 DB에 저장됨)
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}