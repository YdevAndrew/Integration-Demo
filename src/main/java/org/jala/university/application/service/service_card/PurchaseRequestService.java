package org.jala.university.application.service.service_card;

import jakarta.persistence.EntityManager;
import org.jala.university.domain.entity.entity_card.PurchaseRequest;


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
