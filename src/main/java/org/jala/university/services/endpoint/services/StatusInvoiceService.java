package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import org.jala.university.services.JpaUtil;


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
