package org.jala.university.application.service.service_card;

import jakarta.persistence.EntityManager;
import org.jala.university.domain.entity.entity_card.Product;


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
