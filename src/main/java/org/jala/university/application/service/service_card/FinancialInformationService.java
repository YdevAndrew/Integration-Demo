package org.jala.university.application.service.service_card;

import jakarta.persistence.EntityManager;
import org.jala.university.domain.entity.entity_card.FinancialInformation;


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
