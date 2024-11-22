package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.jala.university.services.JpaUtil;
import org.jala.university.services.endpoint.modal.CreditCard;
import org.jala.university.services.endpoint.modal.PaymentDate;

import java.util.List;

public class PaymentDateService {


    public static PaymentDate savePaymentDate(PaymentDate paymentDate) {
        EntityManager em = JpaUtil.getEntityManager();
        em.persist(paymentDate);
        em.getTransaction().commit();
        em.close();
        return paymentDate;
    }

    public static PaymentDate addPaymentDateForCard(String numberCard, String dayPayment) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT cc FROM CreditCard cc WHERE cc.numberCard = :numberCard";
            TypedQuery<CreditCard> query = em.createQuery(jpql, CreditCard.class);
            query.setParameter("numberCard", numberCard);
            CreditCard creditCard = query.getSingleResult();

            if (creditCard != null) {
                PaymentDate paymentDate = new PaymentDate(dayPayment, creditCard);

                em.getTransaction().begin();
                em.persist(paymentDate);
                em.getTransaction().commit();

                return paymentDate;
            } else {
                System.out.println("Credit card not found!");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public static List<PaymentDate> findPaymentDatesByCardNumber(String numberCard) {
        EntityManager em = JpaUtil.getEntityManager();

        String jpql = "SELECT pd " +
                "FROM PaymentDate pd " +
                "INNER JOIN pd.fk_credit_card cc " +
                "WHERE cc.numberCard = :numberCard";

        TypedQuery<PaymentDate> query = em.createQuery(jpql, PaymentDate.class);
        query.setParameter("numberCard", numberCard);

        List<PaymentDate> results = query.getResultList();
        em.close();

        return results;
    }



}
