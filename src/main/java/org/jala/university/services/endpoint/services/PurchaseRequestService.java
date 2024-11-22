package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import org.jala.university.services.JpaUtil;
import org.jala.university.services.endpoint.modal.PurchaseRequest;

/***
 * represention from the purchase
 */

public class PurchaseRequestService {


    public static PurchaseRequest savePurchaseRequest(PurchaseRequest purchaseRequest) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        purchaseRequest = em.merge(purchaseRequest);
        em.getTransaction().commit();
        em.close();
        return purchaseRequest;
    }
}
