package org.jala.university.application.service.service_card;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import org.jala.university.application.dto.dto_card.InvoiceDetailsDTO;
import org.jala.university.domain.entity.entity_card.*;
import org.jala.university.infrastructure.PostgreConnection;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * representation from invoicees
 */
@Service
public class InvoicesService {



    public static boolean checkOutstandingInvoices(String creditCardNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        boolean hasOutstandingInvoices = false;

        try {

            String query = "SELECT i FROM Invoices i WHERE i.credit_card.numberCard = :creditCardNumber";
            List<Invoices> allInvoices = em.createQuery(query, Invoices.class)
                    .setParameter("creditCardNumber", creditCardNumber)
                    .getResultList();

            LocalDate currentDate = LocalDate.now();
            System.out.println("Actual date: " + currentDate);

            for (Invoices invoice : allInvoices) {
                String statusInvoiceQuery = "SELECT si FROM StatusInvoice si WHERE si.fk_id_invoice = :invoice";
                List<StatusInvoice> statusInvoices = em.createQuery(statusInvoiceQuery, StatusInvoice.class)
                        .setParameter("invoice", invoice)
                        .getResultList();

                for (StatusInvoice statusInvoice : statusInvoices) {
                    System.out.println("Check StatusInvoice: " + statusInvoice);
                    System.out.println("Status invoice (status_invoice): " + statusInvoice.isStatus_invoice());

                    String jpql = "SELECT dp FROM DatePayment dp WHERE dp.fk_id_status_invoices = :statusInvoice AND dp.date_payment IS NOT NULL";
                    List<DatePayment> datePayments = em.createQuery(jpql, DatePayment.class)
                            .setParameter("statusInvoice", statusInvoice)
                            .getResultList();

                    for (DatePayment datePayment : datePayments) {
                        LocalDate paymentDate = LocalDate.parse(datePayment.getDate_payment());
                        System.out.println("Date payment: " + paymentDate);
                        if (paymentDate.isBefore(currentDate) && !statusInvoice.isStatus_invoice()) {
                            hasOutstandingInvoices = true;
                            System.out.println("Pending invoice! ID: " + invoice.getId_invoices());
                            break;
                        } else {
                            System.out.println("Invoicee not pending.");
                        }
                    }

                    if (hasOutstandingInvoices) break;
                }

                if (hasOutstandingInvoices) break;
            }
        } catch (Exception e) {
            System.err.println("Fail: " + e.getMessage());
        } finally {
            em.close();
        }

        return hasOutstandingInvoices;
    }





    public static boolean checkIfHaveInvoicesInOpen(String creditCardNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        boolean haveInvoices = false;

        try {
            String jpql = "SELECT COUNT(si) " +
                    "FROM StatusInvoice si " +
                    "JOIN si.fk_id_invoice i " +
                    "JOIN i.credit_card c " +
                    "WHERE c.numberCard = :creditCardNumber " +
                    "AND si.status_invoice = false";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("creditCardNumber", creditCardNumber)
                    .getSingleResult();

            haveInvoices = count > 0;
        } catch (Exception e) {
            System.err.println("Fail: " + e.getMessage());
        } finally {
            em.close();
        }

        return haveInvoices;
    }







    public InvoicesService saveInvoices(InvoicesService invoicesService) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(invoicesService);
        em.getTransaction().commit();
        em.close();
        return invoicesService;
    }

    public static void createInstallments(BigDecimal totalValue, int numberOfInstallments, String creditCardNumber, String creditCardSeller, Product product, PurchaseRequest purchaseRequest) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            BigDecimal roundedTotalValue = totalValue.setScale(2, RoundingMode.HALF_UP);

            CreditCard creditCard = CreditCardService.getCreditCardByNumberCard(creditCardNumber);
            CreditCard cardSeller = CreditCardService.getCreditCardByNumberCard(creditCardSeller);

            purchaseRequest.setDateBuy(String.valueOf(new Date()));
            purchaseRequest.setNumberOfInstallments(numberOfInstallments);
            purchaseRequest.setCreditCard(creditCard);
            purchaseRequest.setProduct(product);
            purchaseRequest.setSellerCreditCard(cardSeller);

            PaymentDate paymentDate = em.createQuery("SELECT pd FROM PaymentDate pd WHERE pd.fk_credit_card.numberCard = :numberCard", PaymentDate.class)
                    .setParameter("numberCard", creditCardNumber)
                    .getSingleResult();

            int dueDay = Integer.parseInt(paymentDate.getDayPayment());

            String jpql = "SELECT lcc FROM CreditCard cc INNER JOIN cc.fk_limit_card lcc WHERE cc.numberCard = :numberCard";
            LimitCreditCard limitCreditCard = em.createQuery(jpql, LimitCreditCard.class)
                    .setParameter("numberCard", creditCardNumber)
                    .getSingleResult();

            String customLimitQuery = "SELECT cl FROM Customlimit cl WHERE cl.fk_limit_card = :limitCreditCard";
            Customlimit customLimit = null;
            try {
                customLimit = em.createQuery(customLimitQuery, Customlimit.class)
                        .setParameter("limitCreditCard", limitCreditCard)
                        .getSingleResult();
            } catch (NoResultException e) {
                System.out.println("No custom limit found for this credit card.");
            } catch (Exception e) {
                System.err.println("Error while retrieving custom limit: " + e.getMessage());
            }

            BigDecimal availableLimit = (customLimit != null) ? customLimit.getCustom_limit() : limitCreditCard.getLimit_credit();

            BigDecimal totalInvoices = InvoicesService.getTotalInvoicesByLimit(limitCreditCard).setScale(2, RoundingMode.HALF_UP);

            if (roundedTotalValue.add(totalInvoices).compareTo(availableLimit) > 0) {
                System.out.println("Purchase value exceeds the available limit after considering pending invoices of all cards sharing the same limit.");
                return;
            }

            Invoices invoice = new Invoices(roundedTotalValue, false, creditCardNumber, numberOfInstallments, Integer.parseInt(LoggedInUser.getLogInUser()), creditCard, cardSeller, product, purchaseRequest);

            em.getTransaction().begin();
            invoice = em.merge(invoice);
            em.getTransaction().commit();

            BigDecimal installmentValue = roundedTotalValue.divide(BigDecimal.valueOf(numberOfInstallments), 2, RoundingMode.HALF_UP);

            LocalDate purchaseDate = LocalDate.now();
            LocalDate firstPaymentDate = (purchaseDate.getDayOfMonth() <= dueDay)
                    ? purchaseDate.withDayOfMonth(dueDay)
                    : purchaseDate.plusMonths(1).withDayOfMonth(dueDay);

            for (int i = 0; i < numberOfInstallments; i++) {
                em.getTransaction().begin();

                StatusInvoice statusInvoice = new StatusInvoice(false, invoice, installmentValue);
                em.persist(statusInvoice);

                LocalDate paymentDateCalculate = firstPaymentDate.plusMonths(i);
                DatePayment datePayment = new DatePayment(statusInvoice, invoice, paymentDateCalculate.toString());
                em.persist(datePayment);

                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Failed to create installments: " + e.getMessage());
        } finally {
            em.close();
        }
    }


    public static boolean isLimitAvailable(BigDecimal totalValue, String creditCardNumber) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            CreditCard creditCard = CreditCardService.getCreditCardByNumberCard(creditCardNumber);

            String jpql = "SELECT lcc FROM CreditCard cc INNER JOIN cc.fk_limit_card lcc WHERE cc.numberCard = :numberCard";
            LimitCreditCard limitCreditCard = em.createQuery(jpql, LimitCreditCard.class)
                    .setParameter("numberCard", creditCardNumber)
                    .getSingleResult();

            String customLimitQuery = "SELECT cl FROM Customlimit cl WHERE cl.fk_limit_card = :limitCreditCard";
            Customlimit customLimit = null;
            try {
                customLimit = em.createQuery(customLimitQuery, Customlimit.class)
                        .setParameter("limitCreditCard", limitCreditCard)
                        .getSingleResult();
            } catch (NoResultException e) {
                System.out.println("No custom limit found for this credit card.");
            }

            BigDecimal availableLimit = (customLimit != null) ? customLimit.getCustom_limit() : limitCreditCard.getLimit_credit();

            availableLimit = availableLimit.setScale(2, RoundingMode.HALF_UP);

            BigDecimal totalInvoices = InvoicesService.getTotalInvoicesByLimit(limitCreditCard);
            totalInvoices = totalInvoices.setScale(2, RoundingMode.HALF_UP);

            totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);

            BigDecimal totalAmount = totalValue.add(totalInvoices);
            if (totalAmount.compareTo(availableLimit) > 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error checking limit: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }



    public static List<String> getAllCardsSharingLimit(String creditCardNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        List<String> cardNumbers = new ArrayList<>();

        try {
            String jpql = "SELECT cc.numberCard FROM CreditCard cc " +
                    "WHERE cc.fk_limit_card = (SELECT c.fk_limit_card FROM CreditCard c " +
                    "WHERE c.numberCard = :creditCardNumber)";
            cardNumbers = em.createQuery(jpql, String.class)
                    .setParameter("creditCardNumber", creditCardNumber)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error fetching cards sharing the same limit: " + e.getMessage());
        } finally {
            em.close();
        }

        return cardNumbers;
    }




    public static List<Invoices> getCurrentMonthInvoices(List<String> creditCardNumbers) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Invoices> invoicesList = new ArrayList<>();

        try {
            // Converter LocalDate para String no formato "YYYY-MM-DD"
            String startOfMonth = YearMonth.now().atDay(1).toString();
            String endOfMonth = YearMonth.now().atEndOfMonth().toString();

            String jpql = "SELECT i FROM DatePayment dp " +
                    "JOIN dp.fk_id_invoice i " +
                    "WHERE dp.date_payment >= :startOfMonth " +
                    "AND dp.date_payment <= :endOfMonth " +
                    "AND i.number_card IN :creditCardNumbers";

            invoicesList = em.createQuery(jpql, Invoices.class)
                    .setParameter("startOfMonth", startOfMonth)
                    .setParameter("endOfMonth", endOfMonth)
                    .setParameter("creditCardNumbers", creditCardNumbers)
                    .getResultList();

        } catch (Exception e) {
            System.err.println("Error fetching invoices for current month: " + e.getMessage());
        } finally {
            em.close();
        }

        return invoicesList;
    }




    public static List<InvoiceDetailsDTO> getInvoicesDetails(String creditCardNumber) throws SQLException {
        String sql = "WITH invoices_data AS ("
                + "    SELECT "
                + "        i.id_invoices,"
                + "        i.fk_id_account,"
                + "        i.number_card,"
                + "        i.number_of_installments,"
                + "        i.status_invoice,"
                + "        i.total_value,"
                + "        i.credit_card_id_credit_card "
                + "    FROM public.invoices i "
                + "    WHERE i.number_card = ?"
                + "), "
                + "date_payment_data AS ("
                + "    SELECT "
                + "        dp.id_payment,"
                + "        dp.date_payment,"
                + "        dp.fk_id_invoice_id "
                + "    FROM public.date_payment dp "
                + "    WHERE dp.date_payment >= TO_CHAR(DATE_TRUNC('MONTH', CURRENT_DATE), 'YYYY-MM-DD') "
                + "      AND dp.date_payment <= TO_CHAR(DATE_TRUNC('MONTH', CURRENT_DATE) + INTERVAL '1 MONTH - 1 day', 'YYYY-MM-DD') "
                + "), "
                + "status_invoice_data AS ("
                + "    SELECT "
                + "        si.id_status_invoice,"
                + "        si.status_invoice,"
                + "        si.value_invoice,"
                + "        si.fk_id_invoice "
                + "    FROM public.status_invoice si "
                + ") "
                + "SELECT "
                + "    i.id_invoices, "
                + "    i.fk_id_account, "
                + "    i.number_card, "
                + "    i.number_of_installments, "
                + "    i.status_invoice AS invoice_status, "
                + "    i.total_value, "
                + "    dp.date_payment, "
                + "    si.status_invoice AS payment_status, "
                + "    si.value_invoice "
                + "FROM invoices_data i "
                + "LEFT JOIN date_payment_data dp ON dp.fk_id_invoice_id = i.id_invoices "
                + "LEFT JOIN status_invoice_data si ON si.fk_id_invoice = i.id_invoices "
                + "ORDER BY dp.date_payment DESC";

             PreparedStatement statement = PostgreConnection.getInstance("jala_bank").getConnection().prepareStatement(sql);{

               statement.setString(1, creditCardNumber);

               try (ResultSet resultSet = statement.executeQuery()) {
                   List<InvoiceDetailsDTO> invoiceDetails = new ArrayList<>();

                   while (resultSet.next()) {
                       Integer idInvoice = resultSet.getInt("id_invoices");
                       Integer fkAccount = resultSet.getInt("fk_id_account");
                       String numberCard = resultSet.getString("number_card");
                       Integer numberOfInstallments = resultSet.getInt("number_of_installments");
                       Boolean invoiceStatus = resultSet.getBoolean("invoice_status");
                       BigDecimal totalValue = resultSet.getBigDecimal("total_value");
                       String datePayment = resultSet.getString("date_payment");
                       Boolean paymentStatus = resultSet.getBoolean("payment_status");
                       BigDecimal valueInvoice = resultSet.getBigDecimal("value_invoice");

                       invoiceDetails.add(new InvoiceDetailsDTO(idInvoice, fkAccount, numberCard, numberOfInstallments,
                               invoiceStatus, totalValue, datePayment, paymentStatus, valueInvoice));
                   }

                   return invoiceDetails;
               }

        }
    }

    public static List<InvoiceDetailsDTO> getDateInvoice(String creditCardNumber) throws SQLException {
        String sql = "SELECT DISTINCT "
                + "    i.id_invoices, "
                + "    i.fk_id_account, "
                + "    i.number_card, "
                + "    i.number_of_installments, "
                + "    i.status_invoice AS invoice_status, "
                + "    i.total_value, "
                + "    dp.id_payment, "
                + "    dp.date_payment, "
                + "    si.status_invoice AS payment_status, "
                + "    si.value_invoice, "
                + "    p.name_product AS product_name, "
                + "    p.description AS product_description, "
                + "    p.barcode AS product_barcode, "
                + "    pr.date_buy AS purchase_date, "
                + "    pr.fk_seller_credit_card_id AS seller_card_id, "
                + "    sc.number_card AS seller_credit_card "
                + "FROM public.invoices i "
                + "LEFT JOIN public.date_payment dp ON dp.fk_id_invoice_id = i.id_invoices "
                + "LEFT JOIN public.status_invoice si ON si.fk_id_invoice = i.id_invoices "
                + "LEFT JOIN public.product p ON p.id_product = i.fk_product "
                + "LEFT JOIN public.purchaserequest pr ON pr.fk_product_id = i.fk_purchase_request "
                + "LEFT JOIN public.credit_card sc ON sc.id_credit_card = pr.fk_seller_credit_card_id "
                + "WHERE i.number_card = ? "
                + "ORDER BY dp.date_payment DESC";

        PreparedStatement statement = PostgreConnection.getInstance("jala_bank").getConnection().prepareStatement(sql);
        statement.setString(1, creditCardNumber);

        try (ResultSet resultSet = statement.executeQuery()) {
            List<InvoiceDetailsDTO> invoiceDetails = new ArrayList<>();

            while (resultSet.next()) {

                Integer idInvoice = resultSet.getInt("id_invoices");
                Integer fkAccount = resultSet.getInt("fk_id_account");
                String numberCard = resultSet.getString("number_card");
                Integer numberOfInstallments = resultSet.getInt("number_of_installments");
                Boolean invoiceStatus = resultSet.getBoolean("invoice_status");
                BigDecimal totalValue = resultSet.getBigDecimal("total_value");
                Integer idPayment = resultSet.getInt("id_payment");
                String datePayment = resultSet.getString("date_payment");
                Boolean paymentStatus = resultSet.getBoolean("payment_status");
                BigDecimal valueInvoice = resultSet.getBigDecimal("value_invoice");
                String productName = resultSet.getString("product_name");
                String productDescription = resultSet.getString("product_description");
                String productBarcode = resultSet.getString("product_barcode"); // Captura o barcode
                String purchaseDate = resultSet.getString("purchase_date");
                Integer sellerCardId = resultSet.getInt("seller_card_id");
                String sellerCreditCard = resultSet.getString("seller_credit_card");

                invoiceDetails.add(new InvoiceDetailsDTO(
                        idInvoice, fkAccount, numberCard, numberOfInstallments, invoiceStatus, totalValue,
                        datePayment, paymentStatus, valueInvoice, productName, productDescription, purchaseDate,
                        sellerCardId, sellerCreditCard, productBarcode));
            }

            // Retorna null se a lista estiver vazia
            return invoiceDetails.isEmpty() ? null : invoiceDetails;
        }
    }








    public static List<Invoices> findByCreditCardId(String creditCardId) {
        EntityManager em = JpaUtil.getEntityManager();
        List<Invoices> invoicesList = new ArrayList<>();

        try {
            String query = "SELECT i FROM Invoices i WHERE i.credit_card.id_credit_card = :creditCardId";
            invoicesList = em.createQuery(query, Invoices.class)
                    .setParameter("creditCardId", creditCardId)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error fetching invoices for credit card ID: " + e.getMessage());
        } finally {
            em.close();
        }

        return invoicesList;
    }



    public static BigDecimal getAvailableLimit(String creditCardNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        BigDecimal availableLimit = BigDecimal.ZERO;

        try {

            String jpql = "SELECT cc FROM CreditCard cc WHERE cc.numberCard = :creditCardNumber";
            CreditCard creditCard = em.createQuery(jpql, CreditCard.class)
                    .setParameter("creditCardNumber", creditCardNumber)
                    .getSingleResult();


            String limitQuery = "SELECT lcc FROM LimitCreditCard lcc WHERE lcc.id_limit_credit_card = :limitCreditCardId";
            LimitCreditCard limitCreditCard = em.createQuery(limitQuery, LimitCreditCard.class)
                    .setParameter("limitCreditCardId", creditCard.getFk_limit_card().getId_limit_credit_card())
                    .getSingleResult();


            availableLimit = limitCreditCard.getLimit_credit();


            String customLimitQuery = "SELECT cl FROM Customlimit cl WHERE cl.fk_limit_card = :limitCreditCard";
            try {
                Customlimit customLimit = em.createQuery(customLimitQuery, Customlimit.class)
                        .setParameter("limitCreditCard", limitCreditCard)
                        .getSingleResult();
                availableLimit = customLimit.getCustom_limit();
            } catch (Exception e) {
                System.out.println("Não há limite personalizado para este cartão.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao calcular o limite disponível: " + e.getMessage());
        } finally {
            em.close();
        }

        return availableLimit != null ? availableLimit : BigDecimal.ZERO;
    }





    public static boolean canPurchase(String creditCardNumber, BigDecimal purchaseAmount) {
        BigDecimal availableLimit = getAvailableLimit(creditCardNumber);
        BigDecimal totalPendingInvoices = getTotalPendingInvoices(creditCardNumber);

        BigDecimal remainingLimit = availableLimit.subtract(totalPendingInvoices);
        System.out.println(remainingLimit);
        return remainingLimit.compareTo(purchaseAmount) >= 0;
    }


    public static BigDecimal getTotalPendingInvoices(String creditCardNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        BigDecimal totalPending = BigDecimal.ZERO;

        try {
            String jpqlCard = "SELECT cc FROM CreditCard cc WHERE cc.numberCard = :creditCardNumber";
            CreditCard creditCard = em.createQuery(jpqlCard, CreditCard.class)
                    .setParameter("creditCardNumber", creditCardNumber)
                    .getSingleResult();

            LimitCreditCard limitCreditCard = creditCard.getFk_limit_card();

            String jpqlSharedCards = "SELECT cc FROM CreditCard cc WHERE cc.fk_limit_card = :limitCreditCard";
            List<CreditCard> sharedCards = em.createQuery(jpqlSharedCards, CreditCard.class)
                    .setParameter("limitCreditCard", limitCreditCard)
                    .getResultList();

            for (CreditCard sharedCard : sharedCards) {
                String jpqlInvoices = "SELECT SUM(si.value_invoice) " +
                        "FROM StatusInvoice si " +
                        "JOIN si.fk_id_invoice i " +
                        "JOIN i.credit_card c " +
                        "WHERE c.numberCard = :creditCardNumber " +
                        "AND si.status_invoice = false";
                BigDecimal pending = em.createQuery(jpqlInvoices, BigDecimal.class)
                        .setParameter("creditCardNumber", sharedCard.getNumberCard())
                        .getSingleResult();

                if (pending != null) {
                    totalPending = totalPending.add(pending);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao calcular pendências: " + e.getMessage());
        } finally {
            em.close();
        }

        return totalPending != null ? totalPending : BigDecimal.ZERO;
    }



    public static BigDecimal restLimit(String creditCardNumber) {
        BigDecimal availableLimit = getAvailableLimit(creditCardNumber);
        BigDecimal totalPendingInvoices = getTotalPendingInvoices(creditCardNumber);
        BigDecimal remainingLimit = availableLimit.subtract(totalPendingInvoices);

        if (remainingLimit.compareTo(BigDecimal.ZERO) < 0) {
            remainingLimit = BigDecimal.ZERO;
        }

        return remainingLimit;
    }

    public static BigDecimal getTotalInvoicesByLimit(LimitCreditCard limitCreditCard) {
        if (limitCreditCard == null) {
            System.err.println("LimitCreditCard is null, returning 0 for total invoices.");
            return BigDecimal.ZERO;
        }

        EntityManager em = JpaUtil.getEntityManager();
        BigDecimal totalInvoices = BigDecimal.ZERO;

        try {
            String jpql = "SELECT SUM(i.total_value) FROM Invoices i " +
                    "WHERE i.credit_card.fk_limit_card = :limitCreditCard AND i.status_invoice = false";
            totalInvoices = em.createQuery(jpql, BigDecimal.class)
                    .setParameter("limitCreditCard", limitCreditCard)
                    .getSingleResult();

            if (totalInvoices == null) {
                totalInvoices = BigDecimal.ZERO;
            }
        } catch (Exception e) {
            System.err.println("Error calculating total invoices: " + e.getMessage());
        } finally {
            em.close();
        }

        return totalInvoices;
    }


    /*
    public static BigDecimal getTotalInvoicesByLimit(String creditCardNumber) {
        EntityManager em = JpaUtil.getEntityManager();
        BigDecimal totalInvoices = BigDecimal.ZERO;

        try {
            // Buscando o cartão de crédito pelo número
            String jpql = "SELECT cc FROM CreditCard cc WHERE cc.numberCard = :creditCardNumber";
            CreditCard creditCard = em.createQuery(jpql, CreditCard.class)
                    .setParameter("creditCardNumber", creditCardNumber)
                    .getSingleResult();

            // Buscando todas as faturas associadas ao mesmo fk_limit_card
            String invoiceQuery = "SELECT SUM(si.value_invoice) " +
                    "FROM StatusInvoice si " +
                    "JOIN si.fk_id_invoice i " +
                    "JOIN i.credit_card cc " +
                    "WHERE cc.fk_limit_card.id_limit_credit_card = :limitCreditCardId " +
                    "AND si.status_invoice = false"; // Faturas não pagas

            totalInvoices = em.createQuery(invoiceQuery, BigDecimal.class)
                    .setParameter("limitCreditCardId", creditCard.getFk_limit_card().getId_limit_credit_card())
                    .getSingleResult();

        } catch (Exception e) {
            System.err.println("Erro ao calcular o total das faturas: " + e.getMessage());
        } finally {
            em.close();
        }

        return totalInvoices != null ? totalInvoices : BigDecimal.ZERO;
    }*/


    public static void main(String[] args) throws SQLException {
       // System.out.println(getDateInvoice("8414539165402504"));

        System.out.println("olaa" + checkOutstandingInvoices("8414539165402504"));

        System.out.println(canPurchase("8414539165402504", new BigDecimal(960.82)));
        /*
        System.out.println(getTotalPendingInvoices("3878687640558653"));
        System.out.println(getTotalInvoicesByLimit(LimitCreditCardService.findLimitCreditByCardNumber("2452756957495076")));
        //System.out.println(!checkOutstandingInvoices("8782749168849098"));

        //createInstallments(new BigDecimal(4538), 2, "8782749168849098", "3239945750253034", new Product(), new PurchaseRequest());
*/
    }

}
