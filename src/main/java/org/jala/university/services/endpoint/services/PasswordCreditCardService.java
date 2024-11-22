package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.jala.university.services.JpaUtil;
import org.jala.university.services.endpoint.modal.PasswordCreditCard;

import java.util.List;

public class PasswordCreditCardService {

    public static PasswordCreditCard savePassordCreditCard(PasswordCreditCard passwordCreditCard) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        passwordCreditCard = em.merge(passwordCreditCard);
        em.getTransaction().commit();
        em.close();
        return passwordCreditCard;
    }

    public static List<PasswordCreditCard> findPasswordsByCardNumber(String numberCard) {
        EntityManager em = JpaUtil.getEntityManager();

        String jpql = "SELECT pcc " +
                "FROM PasswordCreditCard pcc " +
                "INNER JOIN pcc.fk_credit_card cc " +
                "WHERE cc.numberCard = :numberCard";

        TypedQuery<PasswordCreditCard> query = em.createQuery(jpql, PasswordCreditCard.class);
        query.setParameter("numberCard", numberCard);

        List<PasswordCreditCard> results = query.getResultList();
        em.close();

        return results;
    }

    public static boolean hasPassword(String numberCard) {
        PasswordCreditCard passwordCreditCard = findPasswordByCardNumber(numberCard);
        return passwordCreditCard != null;
    }


    public static PasswordCreditCard findPasswordByCardNumber(String numberCard) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT pcc " +
                    "FROM PasswordCreditCard pcc " +
                    "INNER JOIN pcc.fk_credit_card cc " +
                    "WHERE cc.numberCard = :numberCard";

            TypedQuery<PasswordCreditCard> query = em.createQuery(jpql, PasswordCreditCard.class);
            query.setParameter("numberCard", numberCard);

            // Retorna o primeiro resultado encontrado ou null
            List<PasswordCreditCard> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

/*
    public static void main(String[] args) {
        String numberCard = "1416544230546163";
        List<PasswordCreditCard> passwords = findPasswordsByCardNumber(numberCard);

        if (!passwords.isEmpty()) {
            PasswordCreditCard passwordCreditCard = passwords.get(0);
            System.out.println("Password ID: " + passwordCreditCard.getId_password_credit_card());
            System.out.println("Password: " + passwordCreditCard.getPassword_credit_card());
        } else {
            System.out.println("No passwords found for card: " + numberCard);
        }
    }
 */

    public static void main(String[] args) {
        String numberCard = "5618552034641541";

        if (hasPassword(numberCard)) {
            PasswordCreditCard passwordCreditCard = findPasswordByCardNumber(numberCard);
            System.out.println("Password exists for card: " + numberCard);
            System.out.println("Password ID: " + passwordCreditCard.getId_password_credit_card());
            System.out.println("Password: " + passwordCreditCard.getPassword_credit_card());
        } else {
            System.out.println("No password found for card: " + numberCard);
        }
    }


}
