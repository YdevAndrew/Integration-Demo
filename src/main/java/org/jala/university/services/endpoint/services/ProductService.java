package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import org.jala.university.services.JpaUtil;
import org.jala.university.services.endpoint.modal.Product;


public class ProductService {


    public static Product saveProductService(Product product) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        product = em.merge(product);
        em.getTransaction().commit();
        em.close();
        return product;
    }
}
