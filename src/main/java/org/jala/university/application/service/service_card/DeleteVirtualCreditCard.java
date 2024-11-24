package org.jala.university.application.service.service_card;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.jala.university.domain.entity.entity_card.*;


public class DeleteVirtualCreditCard {

    public static void deleteCard(String cardNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            String jpqlCard = "SELECT cc FROM CreditCard cc WHERE cc.numberCard = :numberCard";
            var creditCard = em.createQuery(jpqlCard, CreditCard.class)
                    .setParameter("numberCard", cardNumber)
                    .getSingleResult();

            if (creditCard == null) {
                System.out.println("No credit card found for number: " + cardNumber);
                return;
            }

            String jpqlInvoices = "SELECT i FROM Invoices i WHERE i.credit_card.id_credit_card = :cardId";
            var invoicesList = em.createQuery(jpqlInvoices, Invoices.class)
                    .setParameter("cardId", creditCard.getId_credit_card())
                    .getResultList();

            for (Invoices invoice : invoicesList) {
                InvoiceBackup backup = new InvoiceBackup();
                backup.setCardNumber(creditCard.getNumberCard());
                backup.setTotalValue(invoice.getTotal_value());
                backup.setStatusInvoice(invoice.isStatus_invoice());
                backup.setNumberCard(invoice.getNumber_card());
                backup.setNumberOfInstallments(invoice.getNumber_of_installments());

                String jpqlPaymentDate = "SELECT dp FROM DatePayment dp WHERE dp.fk_id_invoice = :invoiceId";
                var paymentDateList = em.createQuery(jpqlPaymentDate, DatePayment.class)
                        .setParameter("invoiceId", invoice)
                        .getResultList();

                if (!paymentDateList.isEmpty()) {
                    backup.setDatePayment(paymentDateList.get(0).getDate_payment());
                }

                String jpqlPurchase = "SELECT pr FROM PurchaseRequest pr WHERE pr.creditCard.numberCard = :cardNumber";
                var purchaseList = em.createQuery(jpqlPurchase, PurchaseRequest.class)
                        .setParameter("cardNumber", creditCard.getNumberCard())
                        .getResultList();

                if (!purchaseList.isEmpty()) {
                    backup.setPurchaseDate(purchaseList.get(0).getDateBuy());
                }

                String jpqlStatusInvoice = "SELECT si FROM StatusInvoice si WHERE si.fk_id_invoice = :invoiceId";
                var statusInvoiceList = em.createQuery(jpqlStatusInvoice, StatusInvoice.class)
                        .setParameter("invoiceId", invoice)
                        .getResultList();

                if (!statusInvoiceList.isEmpty()) {
                    backup.setInvoiceValue(statusInvoiceList.get(0).getValue_invoice());
                }

                em.persist(backup);
            }

            String deleteDatePayments = "DELETE FROM DatePayment dp WHERE dp.fk_id_invoice IN (SELECT i FROM Invoices i WHERE i.credit_card.id_credit_card = :cardId)";
            em.createQuery(deleteDatePayments)
                    .setParameter("cardId", creditCard.getId_credit_card())
                    .executeUpdate();

            String statusInvoice = "DELETE FROM StatusInvoice si WHERE si.fk_id_invoice IN " +
                    "(SELECT i FROM Invoices i WHERE i.credit_card.numberCard = :cardNumber)";
            em.createQuery(statusInvoice)
                    .setParameter("cardNumber", creditCard.getNumberCard())
                    .executeUpdate();



            String deleteInvoices = "DELETE FROM Invoices i WHERE i.credit_card = :cardId";
            em.createQuery(deleteInvoices)
                    .setParameter("cardId", creditCard)
                    .executeUpdate();

            String deletePurchaseRequests = "DELETE FROM PurchaseRequest pr WHERE pr.creditCard.numberCard = :cardNumber";
            em.createQuery(deletePurchaseRequests)
                    .setParameter("cardNumber", creditCard.getNumberCard())
                    .executeUpdate();



            String deleteCardClient = "DELETE FROM CreditCardClient ccc WHERE ccc.id_credit_card = :cardId";
            em.createQuery(deleteCardClient)
                    .setParameter("cardId", creditCard.getId_credit_card())
                    .executeUpdate();

            String deletePassword = "DELETE FROM PasswordCreditCard pcc WHERE pcc.fk_credit_card = :cardId";
            em.createQuery(deletePassword)
                    .setParameter("cardId", creditCard)
                    .executeUpdate();

            String deletePaymentDate = "DELETE FROM PaymentDate pd WHERE pd.fk_credit_card = :cardId";
            em.createQuery(deletePaymentDate)
                    .setParameter("cardId", creditCard)
                    .executeUpdate();

            String deleteCustomLimit = "DELETE FROM Customlimit cl WHERE cl.fk_limit_card = :cardId";

            if (creditCard.getFk_limit_card() != null) {
                em.createQuery(deleteCustomLimit)
                        .setParameter("cardId", LimitCreditCardService.findLimitCreditByCardNumber(cardNumber))
                        .executeUpdate();
            }

            em.remove(creditCard);

            transaction.commit();
            System.out.println("All data related to card " + cardNumber + " has been deleted.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }



    public static void main(String[] args) {

    }
}
