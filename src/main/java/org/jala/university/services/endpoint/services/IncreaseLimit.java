package org.jala.university.services.endpoint.services;

import jakarta.persistence.EntityManager;
import org.jala.university.services.JpaUtil;
import org.jala.university.services.endpoint.modal.Invoices;
import org.jala.university.services.endpoint.modal.LimitCreditCard;

import java.math.BigDecimal;
import java.util.List;

public class IncreaseLimit {


    public static boolean increaseLimitIfNeeded(String creditCardNumber) {
        boolean result = false;
        // Verificar se há faturas pendentes
        boolean hasOutstandingInvoices = InvoicesService.checkOutstandingInvoices(creditCardNumber);

        if (hasOutstandingInvoices) {
            System.out.println("The user has outstanding invoices, the limit will not be increased.");
            return result;
        }

        // Obter o limite atual
        BigDecimal availableLimit = LimitCreditCardService.getLimitCreditCardInfo(creditCardNumber);
        System.out.println("Available Limit: " + availableLimit);

        // Obter o total gasto no mês
        BigDecimal totalSpent = getTotalSpentInCurrentMonth(creditCardNumber);
        System.out.println("Total Spent in Current Month: " + totalSpent);

        // Verificar se o total gasto é ao menos 75% do limite
        if (totalSpent.compareTo(availableLimit.multiply(BigDecimal.valueOf(0.75))) >= 0) {
            increaseLimit(creditCardNumber);
            result = true;
        } else {
            System.out.println("Current spending has not reached 75% of the available limit.");
        }
        return result;
    }

    private static BigDecimal getTotalSpentInCurrentMonth(String creditCardNumber) {
        BigDecimal totalSpent = BigDecimal.ZERO;

        try {
            List<Invoices> invoices = InvoicesService.getCurrentMonthInvoices(creditCardNumber);
            for (Invoices invoice : invoices) {
                if (!invoice.isStatus_invoice()) {
                    totalSpent = totalSpent.add(invoice.getTotal_value());
                }
            }
        } catch (Exception e) {
            System.err.println("Error calculating total spent in the month: " + e.getMessage());
        }

        return totalSpent;
    }

    private static void increaseLimit(String creditCardNumber) {
        BigDecimal currentLimit = LimitCreditCardService.findLimitCreditByCardNumber(creditCardNumber).getLimit_credit();
        LimitCreditCard limitCreditCard = LimitCreditCardService.findLimitCreditByCardNumber(creditCardNumber);


        BigDecimal newLimit = currentLimit.multiply(BigDecimal.valueOf(1.10));


        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            limitCreditCard.setLimit_credit(newLimit);
            em.merge(limitCreditCard);
            em.getTransaction().commit();

            System.out.println("Credit card limit increased by 10%. New limit: " + newLimit);
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error increasing limit: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {

    }

}
