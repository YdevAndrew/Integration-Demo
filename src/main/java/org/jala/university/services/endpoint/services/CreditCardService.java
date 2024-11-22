package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.jala.university.services.JpaUtil;
import org.jala.university.services.endpoint.modal.CreditCard;

public class CreditCardService {

    public static CreditCard saveCreditCard(CreditCard creditCard) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        creditCard = em.merge(creditCard);
        em.getTransaction().commit();
        em.close();
        return creditCard;
    }


    public static CreditCard getCreditCardByNumberCard(String numberCard) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM CreditCard c " +
                    "WHERE c.numberCard = :numberCard";

            CreditCard creditCard = (CreditCard) em.createQuery(jpql)
                    .setParameter("numberCard", numberCard)
                    .getSingleResult();
            return creditCard;
        } catch (NoResultException e) {

            return null;
        } finally {
            em.close();
        }
    }



    public static CreditCard getCvvCreditCard(String cvv) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM CreditCard c " +
                    "WHERE c.cvv = :cvv";

            CreditCard creditCard = (CreditCard) em.createQuery(jpql)
                    .setParameter("cvv", cvv)
                    .getSingleResult();
            return creditCard;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static CreditCard getStatusCard(int creditCard) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT c FROM CreditCard c WHERE c.id_credit_card = :id";

            CreditCard result = em.createQuery(jpql, CreditCard.class)
                    .setParameter("id", creditCard)
                    .getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static void activateCreditCard(int idCreditCard) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            String jpql = "UPDATE CreditCard c SET c.active = true WHERE c.id_credit_card = :id";
            em.createQuery(jpql)
                    .setParameter("id", idCreditCard)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void deactivateCreditCard(int idCreditCard) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            String jpql = "UPDATE CreditCard c SET c.active = false WHERE c.id_credit_card = :id";
            em.createQuery(jpql)
                    .setParameter("id", idCreditCard)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }





    public static void main(String[] args) {
       CreditCard creditCardss = getCreditCardByNumberCard("9323621176209686");
       System.out.println(creditCardss.getName_card());
    }

}
