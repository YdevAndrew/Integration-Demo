package org.jala.university.application.service.service_card;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.jala.university.domain.entity.entity_card.CreditCard;
import org.jala.university.domain.entity.entity_card.CreditCardClient;
import org.jala.university.domain.entity.entity_card.PasswordCreditCard;
import org.jala.university.domain.entity.entity_card.PaymentDate;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CreditCardClientService {

    public CreditCardClient saveCreditCardClient(CreditCardClient creditCardClient) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        creditCardClient = em.merge(creditCardClient);
        em.getTransaction().commit();
        em.close();
        return creditCardClient;
    }

    public static boolean updatePaymentIsAvaliable(String creditNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            CreditCardClient creditCardClient = findByCreditCardNumber(creditNumber);

            if (creditCardClient == null || creditCardClient.getPaymentDate() == null) {
                return true;
            }

            PaymentDate currentPaymentDate = creditCardClient.getPaymentDate();
            LocalDateTime lastUpdate = currentPaymentDate.getLastUpdate();


            return lastUpdate == null || ChronoUnit.YEARS.between(lastUpdate, LocalDateTime.now()) >= 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public static CreditCardClient updateDatePayment(String creditNumber, PaymentDate newDayPayment) {
        EntityManager em = JpaUtil.getEntityManager();
        CreditCardClient creditCardClient = findByCreditCardNumber(creditNumber);

        if (creditCardClient != null) {

            PaymentDate currentPaymentDate = creditCardClient.getPaymentDate();

            if (currentPaymentDate != null) {
                LocalDateTime lastUpdate = currentPaymentDate.getLastUpdate();
                if (lastUpdate != null && ChronoUnit.YEARS.between(lastUpdate, LocalDateTime.now()) < 1) {
                    System.out.println("The last update was less than a year ago. Update not allowed.");
                    em.close();
                    return creditCardClient;
                }

                currentPaymentDate.setDayPayment(newDayPayment.getDayPayment());
                currentPaymentDate.setLastUpdate(LocalDateTime.now());
                em.getTransaction().begin();
                em.merge(currentPaymentDate);
                em.getTransaction().commit();
            } else {
                newDayPayment.setFk_credit_card(creditCardClient.getCredit_card());
                newDayPayment.setLastUpdate(LocalDateTime.now());
                creditCardClient.setPaymentDate(newDayPayment);
                em.getTransaction().begin();
                em.merge(creditCardClient);
                em.getTransaction().commit();
            }
        } else {
            System.out.println("CreditCardClient with number " + creditNumber + " not found.");
        }

        em.close();
        return creditCardClient;
    }


    public static CreditCardClient updateDatePaymentDefault(String creditNumber, PaymentDate newDayPayment) {
        EntityManager em = JpaUtil.getEntityManager();
        CreditCardClient creditCardClient = findByCreditCardNumber(creditNumber);

        if (creditCardClient != null) {
            PaymentDate currentPaymentDate = creditCardClient.getPaymentDate();
            if (currentPaymentDate != null) {
                LocalDateTime lastUpdate = currentPaymentDate.getLastUpdate();
                if (lastUpdate != null && ChronoUnit.YEARS.between(lastUpdate, LocalDateTime.now()) < 1) {
                    System.out.println("The last update was less than a year ago. Update not allowed.");
                    em.close();
                    return creditCardClient;
                }
            }

            em.getTransaction().begin();
            newDayPayment.setFk_credit_card(creditCardClient.getCredit_card());
            creditCardClient.setPaymentDate(newDayPayment);
            em.merge(creditCardClient);
            em.getTransaction().commit();
        } else {
            System.out.println("CreditCardClient with number " + creditNumber + " not found.");
        }
        em.close();
        return creditCardClient;
    }

    public static CreditCardClient updatePasswordCreditCard(String creditNumber, PasswordCreditCard passwordCreditCard) {


        EntityManager em = JpaUtil.getEntityManager();
        CreditCardClient creditCardClient = findByCreditCardNumber(creditNumber);


        if (creditCardClient != null) {
            em.getTransaction().begin();
            creditCardClient.setPasswordCreditCard(passwordCreditCard);
            passwordCreditCard.setFk_credit_card(creditCardClient.getCredit_card());
            em.merge(creditCardClient);
            em.getTransaction().commit();
        } else {
            System.out.println("CreditCardClient with number " + creditNumber + " not found.");
        }
        em.close();
        return creditCardClient;
    }

    public static CreditCardClient findByCreditCardNumber(String creditNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT ccc FROM CreditCardClient ccc WHERE ccc.credit_card.numberCard = :creditNumber",
                            CreditCardClient.class)
                    .setParameter("creditNumber", creditNumber)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
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

    public static void main(String[] args) {
        List<CreditCard> creditCards = searchCreditCardsByAccountId();

        if (creditCards.isEmpty()) {
            System.out.println("Nenhum cartão encontrado para a conta ");
        } else {
            creditCards.forEach(card ->
                    System.out.println("Cartão encontrado: " + card.getNumberCard()));
        }
        //updatePasswordCreditCard("5029101227636919", new PasswordCreditCard("544432"));
    }


    public static List<CreditCard> searchCreditCardsByAccountId() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String sql = "SELECT cc FROM CreditCard cc " +
                    "JOIN CreditCardClient ccc ON ccc.credit_card.id_credit_card = cc.id_credit_card " +
                    "WHERE ccc.fk_account_id = :accountId";
            return em.createQuery(sql, CreditCard.class)
                    .setParameter("accountId", LoggedInUser.getLogInUser())
                    .getResultList();
        } catch (NoResultException e) {
            return List.of();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }




}
