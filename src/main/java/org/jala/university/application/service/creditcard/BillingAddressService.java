package org.jala.university.application.service.creditcard;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jala.university.application.model.BillingAddress;
import org.jala.university.services.JpaUtil;


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
