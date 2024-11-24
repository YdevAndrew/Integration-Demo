package org.jala.university.application.service.service_card;
//teste
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.jala.university.domain.entity.entity_card.Customlimit;
import org.jala.university.domain.entity.entity_card.LimitCreditCard;


import java.math.BigDecimal;
import java.util.logging.Logger;

public class AddCustomLimitService {

    private static final Logger LOGGER = Logger.getLogger(AddCustomLimitService.class.getName());


    public static boolean validateAndSaveOrUpdateCustomLimit(String cardNumber, BigDecimal customLimit) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            String jpqlFindLimit = "SELECT lcc FROM CreditCard cc INNER JOIN cc.fk_limit_card lcc WHERE cc.numberCard = :numberCard";
            LimitCreditCard limitCreditCard = em.createQuery(jpqlFindLimit, LimitCreditCard.class)
                    .setParameter("numberCard", cardNumber)
                    .getSingleResult();

            String jpqlFindCustomLimit = "SELECT cl FROM Customlimit cl WHERE cl.fk_limit_card = :limitCard";
            Customlimit existingCustomLimit = null;
            try {
                existingCustomLimit = em.createQuery(jpqlFindCustomLimit, Customlimit.class)
                        .setParameter("limitCard", limitCreditCard)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOGGER.info("No existing custom limits found. Creating a new one.");
            }

            if (customLimit.compareTo(limitCreditCard.getLimit_credit()) <= 0) {
                if (existingCustomLimit != null) {
                    existingCustomLimit.setCustom_limit(customLimit);
                    em.merge(existingCustomLimit);
                    LOGGER.info("Custom limit updated successfully.");
                } else {
                    Customlimit customlimit = new Customlimit(limitCreditCard, customLimit);
                    em.persist(customlimit);
                    LOGGER.info("New custom limit created successfully.");
                }
                em.getTransaction().commit();
                return true;
            } else {
                LOGGER.warning("The custom limit exceeds the limit allowed by the bank.");
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            LOGGER.severe("Error saving or updating custom limit: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {

        validateAndSaveOrUpdateCustomLimit("9490179778946087", new BigDecimal(400));
    }


}


