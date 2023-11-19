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
            // 영속 엔티티 조회
            Member findMember = entityManager.find(Member.class, 150L);
            // 영속 엔티티 데이터 수정
            findMember.setName("AAAAA");
            // 준영속 상태로 분리, 영속성 컨텍스트에서 관리 안함
            entityManager.detach(findMember);

            System.out.println("===============");
            entityTransaction.commit(); // [트랜잭션] 커밋
            // update 쿼리 안나감
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
