package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import org.jala.university.services.JpaUtil;
import org.jala.university.services.endpoint.modal.DatePayment;


public class DatePaymentService {

    public DatePayment saveDatePayment(DatePayment datePayment) {
        EntityManager em = JpaUtil.getEntityManager();
        em.persist(datePayment);
        em.getTransaction().commit();
        em.close();
        return datePayment;
    }

}
