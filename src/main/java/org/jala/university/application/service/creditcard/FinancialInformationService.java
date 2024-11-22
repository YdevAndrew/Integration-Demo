package org.jala.university.application.service.creditcard;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jala.university.application.model.FinancialInformation;
import org.jala.university.services.JpaUtil;


public class FinancialInformationService {


    public FinancialInformation savefinancialInformation(FinancialInformation financialInformation) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        financialInformation = em.merge(financialInformation);
        em.getTransaction().commit();
        em.close();
        return financialInformation;
    }
}

