package org.jala.university.application.service.service_card;



import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.jala.university.domain.entity.entity_card.LimitCreditCard;

import java.math.BigDecimal;

public class LimitCreditCardService {


    public static LimitCreditCard saveLimitCreditCard(LimitCreditCard limitCreditCard) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        //limitCreditCard = em.merge(limitCreditCard);
        em.persist(limitCreditCard);

        em.getTransaction().commit();
        em.close();
        return limitCreditCard;
    }


    public static BigDecimal getCustomLimitByCardId(int limitCardId) {
        EntityManager em = JpaUtil.getEntityManager();

        String jpql = "SELECT cl.custom_limit FROM Customlimit cl WHERE cl.fk_limit_card.id_limit_credit_card = :limitCardId";

        TypedQuery<BigDecimal> query = em.createQuery(jpql, BigDecimal.class);
        query.setParameter("limitCardId", limitCardId);

        BigDecimal customLimit = null;
        try {
            customLimit = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum limite personalizado encontrado para o cartão.");
        }

        em.close();
        return customLimit;
    }

    public static LimitCreditCard findLimitCreditByCardNumber(String numberCard) {
        EntityManager em = JpaUtil.getEntityManager();

        String jpql = "SELECT lcc " +
                "FROM CreditCard cc " +
                "INNER JOIN cc.fk_limit_card lcc " +
                "WHERE cc.numberCard = :numberCard";

        TypedQuery<LimitCreditCard> query = em.createQuery(jpql, LimitCreditCard.class);
        query.setParameter("numberCard", numberCard);

        LimitCreditCard result = query.getSingleResult();
        em.close();

        return result;
    }


    public static BigDecimal getLimitCreditCardInfo(String numberCard) {
        EntityManager em = JpaUtil.getEntityManager();

        BigDecimal customLimit = null;
        try {
            String customLimitQuery = "SELECT cl.custom_limit FROM Customlimit cl " +
                    "WHERE cl.fk_limit_card IN (SELECT cc.fk_limit_card FROM CreditCard cc " +
                    "WHERE cc.numberCard = :numberCard)";
            TypedQuery<BigDecimal> customLimitTypedQuery = em.createQuery(customLimitQuery, BigDecimal.class);
            customLimitTypedQuery.setParameter("numberCard", numberCard);

            customLimit = customLimitTypedQuery.getSingleResult();
        } catch (Exception e) {
            System.out.println("Custom limit not found for card: " + numberCard);
        }

        if (customLimit == null) {
            try {
                String defaultLimitQuery = "SELECT lcc.limit_credit FROM CreditCard cc " +
                        "INNER JOIN cc.fk_limit_card lcc " +
                        "WHERE cc.numberCard = :numberCard";
                TypedQuery<BigDecimal> defaultLimitTypedQuery = em.createQuery(defaultLimitQuery, BigDecimal.class);
                defaultLimitTypedQuery.setParameter("numberCard", numberCard);

                customLimit = defaultLimitTypedQuery.getSingleResult();
            } catch (Exception e) {
                System.out.println("Default limit not found for card: " + numberCard);
            }
        }

        em.close();
        return customLimit;
    }

    public static BigDecimal getOriginalLimit(String numberCard) {
        EntityManager em = JpaUtil.getEntityManager();
        BigDecimal limitCredit = null;

        try {
            String limitQuery = "SELECT lcc.limit_credit FROM CreditCard cc " +
                    "INNER JOIN cc.fk_limit_card lcc " +
                    "WHERE cc.numberCard = :numberCard";
            TypedQuery<BigDecimal> limitTypedQuery = em.createQuery(limitQuery, BigDecimal.class);
            limitTypedQuery.setParameter("numberCard", numberCard);

            limitCredit = limitTypedQuery.getSingleResult();
        } catch (Exception e) {
            System.out.println("Limit credit not found for card: " + numberCard);
        }

        em.close();
        return limitCredit;
    }





}
