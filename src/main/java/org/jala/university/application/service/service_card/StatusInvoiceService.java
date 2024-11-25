package org.jala.university.application.service.service_card;

import jakarta.persistence.EntityManager;


public class StatusInvoiceService {
    public StatusInvoiceService saveStatusInvoice(StatusInvoiceService statusInvoiceService) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            statusInvoiceService = em.merge(statusInvoiceService);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return statusInvoiceService;
    }

}
