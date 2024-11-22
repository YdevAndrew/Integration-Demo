package org.jala.university.services.endpoint.repository;

import org.jala.university.services.endpoint.modal.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
   // @Query("SELECT c.expiration_date FROM CreditCard c WHERE c.id_credit_card = :creditCardId AND c.customer.id = :customerId")
    //String findExpirationDateByCreditCardIdAndCustomerId(@Param("creditCardId") Integer creditCardId, @Param("customerId") Integer customerId);
}
