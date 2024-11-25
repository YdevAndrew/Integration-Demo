package org.jala.university.application.service.service_card;

import jakarta.persistence.EntityManager;
import org.jala.university.domain.entity.entity_card.BillingAddress;


public class BillingAddressService {


    public BillingAddress saveBillingAddress(BillingAddress billingAddress) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        billingAddress = em.merge(billingAddress);
        em.getTransaction().commit();
        em.close();
        return billingAddress;
    }






}
