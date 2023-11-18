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
            // 영속
            Member member1 = new Member(200L, "A");
            Member member2 = new Member(190L, "C");

            entityManager.persist(member1);
            entityManager.persist(member2);
            //여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.

            System.out.println("======================");

            entityTransaction.commit(); // [트랜잭션] 커밋
            //커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
