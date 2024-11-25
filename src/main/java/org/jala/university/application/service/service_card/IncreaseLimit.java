package org.jala.university.application.service.service_card;


import jakarta.persistence.EntityManager;
import org.jala.university.domain.entity.entity_card.Invoices;
import org.jala.university.domain.entity.entity_card.LimitCreditCard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IncreaseLimit {


    public static boolean increaseLimitIfNeeded(String creditCardNumber) {
        boolean result = false;
        boolean hasOutstandingInvoices = InvoicesService.checkOutstandingInvoices(creditCardNumber);

        if (hasOutstandingInvoices) {
            System.out.println("The user has outstanding invoices, the limit will not be increased.");
            return result;
        }

        BigDecimal availableLimit = LimitCreditCardService.getOriginalLimit(creditCardNumber);
        System.out.println("Available Limit: " + availableLimit);
        System.out.println(availableLimit.toString());

        BigDecimal totalSpent = getTotalSpentInAllPeriods(creditCardNumber);
        System.out.println("Total Spent in Current Month: " + totalSpent);

        if (availableLimit != null && totalSpent != null) {
            if (totalSpent.compareTo(availableLimit.multiply(BigDecimal.valueOf(0.75))) >= 0) {
                increaseLimit(creditCardNumber);
                result = true;
            } else {
                System.out.println("Current spending has not reached 75% of the available limit.");
            }

        } else {
            System.out.println("Cannot proceed because available limit or total spent is null.");
        }
        System.out.println("Available Limit: " + availableLimit);
        System.out.println("Total Spent: " + totalSpent);
        System.out.println("85% of Available Limit: " + availableLimit.multiply(BigDecimal.valueOf(0.85)));


        return result;
    }

    private static BigDecimal getTotalSpentInCurrentMonth(String creditCardNumber) {
        BigDecimal totalSpent = BigDecimal.ZERO;

        try {
            // Buscar todos os cartões que compartilham o mesmo limite
            List<String> sharedCards = InvoicesService.getAllCardsSharingLimit(creditCardNumber);

            // Obter faturas de todos os cartões
            List<Invoices> invoices = InvoicesService.getCurrentMonthInvoices(sharedCards);

            // Somar os gastos das faturas pagas (status_invoice = true)
            for (Invoices invoice : invoices) {
                System.out.println("Verificando fatura: " + invoice.getId_invoices() +
                        ", Status: " + invoice.isStatus_invoice() +
                        ", Valor: " + invoice.getTotal_value());

                // Somar apenas as faturas que foram pagas (status_invoice == true)
                if (invoice.isStatus_invoice()) {
                    totalSpent = totalSpent.add(invoice.getTotal_value());
                }
            }

        } catch (Exception e) {
            System.err.println("Error calculating total spent in the month: " + e.getMessage());
        }

        System.out.println("Total gasto no mês (faturas pagas): " + totalSpent);
        return totalSpent;
    }


    private static BigDecimal getTotalSpentInAllPeriods(String creditCardNumber) {
        BigDecimal totalSpent = BigDecimal.ZERO;

        try {
            // Buscar todos os cartões que compartilham o mesmo limite
            List<String> sharedCards = InvoicesService.getAllCardsSharingLimit(creditCardNumber);

            // Obter todas as faturas (não limitar ao mês atual)
            List<Invoices> invoices = IncreaseLimit.getAllInvoices(sharedCards);

            // Somar os gastos das faturas pagas (status_invoice = true)
            for (Invoices invoice : invoices) {
                System.out.println("Verificando fatura: " + invoice.getId_invoices() +
                        ", Status: " + invoice.isStatus_invoice() +
                        ", Valor: " + invoice.getTotal_value());

                // Somar apenas as faturas que foram pagas (status_invoice == true)
                if (invoice.isStatus_invoice()) {
                    totalSpent = totalSpent.add(invoice.getTotal_value());
                }
            }

        } catch (Exception e) {
            System.err.println("Error calculating total spent in the period: " + e.getMessage());
        }

        System.out.println("Total gasto (faturas pagas): " + totalSpent);
        return totalSpent;
    }


    public static List<Invoices> getAllInvoices(List<String> creditCardNumbers) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Invoices> invoicesList = new ArrayList<>();

        try {
            String jpql = "SELECT i FROM Invoices i WHERE i.number_card IN :creditCardNumbers";
            invoicesList = em.createQuery(jpql, Invoices.class)
                    .setParameter("creditCardNumbers", creditCardNumbers)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error fetching all invoices: " + e.getMessage());
        } finally {
            em.close();
        }

        return invoicesList;
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
        boolean result = false;
        boolean hasOutstandingInvoices = InvoicesService.checkOutstandingInvoices("7829419613867387");


        System.out.println("AAAAAAAA" + LimitCreditCardService.getOriginalLimit("8414539165402504"));
/*
        BigDecimal availableLimit = LimitCreditCardService.getLimitCreditCardInfo("7829419613867387");
        System.out.println("Available Limit: " + availableLimit);

        BigDecimal totalSpent = getTotalSpentInAllPeriods("8414539165402504");

        System.out.println(totalSpent);
*/
     //   System.out.println(increaseLimitIfNeeded("8414539165402504"));
    }

}
