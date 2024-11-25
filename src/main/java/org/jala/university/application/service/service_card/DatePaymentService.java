package org.jala.university.application.service.service_card;

import jakarta.persistence.EntityManager;
import org.jala.university.domain.entity.entity_card.DatePayment;


public class DatePaymentService {

    public DatePayment saveDatePayment(DatePayment datePayment) {
        EntityManager em = JpaUtil.getEntityManager();
        em.persist(datePayment);
        em.getTransaction().commit();
        em.close();
        return datePayment;
    }

}
