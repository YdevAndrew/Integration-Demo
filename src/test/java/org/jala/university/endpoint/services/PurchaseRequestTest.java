package org.jala.university.endpoint.services;

public class PurchaseRequestTest {
/*
    @Test
    public void testPurchaseRequestInitialization() throws ParseException {
        Date date = new Date("2024/11/06");
        CreditCard customer = new CreditCard(1, "12345678", "123", date, true, "Rodrigo S", "12345678", new BigDecimal(450.43), "Rodrigo Santos");
        CreditCard seller = new CreditCard(2, "987654321", "321", date, true, "Ana S","12345678", new BigDecimal(500.34), "Ana Santos");
        String barcode = "123456789";

        PurchaseRequest purchaseRequest = new PurchaseRequest(customer, seller,null, barcode);

        assertEquals(customer, purchaseRequest.getCreditCard());
        assertEquals(seller, purchaseRequest.getSellerCreditCard());
        assertEquals(barcode, purchaseRequest.getProduct().getBarcode());
    }
/*
    @Test
    public void testDateBuyIsGeneratedCorrectly() throws ParseException {
        Date date = new Date("2024/11/06");
        String expectedDatePattern = "\\d{4}-\\d{2}-\\d{2}";
        PurchaseRequest purchaseRequest = new PurchaseRequest(
                new CreditCard(1, "12345678", "123", date, true, "Rodrigo S", "12345678", new BigDecimal(450.43), "Rodrigo Santos"),
                new CreditCard(2, "987654321", "321", date, true, "Ana S","12345678", new BigDecimal(500.34), "Ana Santos"),
                null,
                "123456789"
        );

        assertTrue(purchaseRequest.getDateBuy().matches(expectedDatePattern));
    }

    @Test
    public void testInsertDateFormat() throws ParseException {
        String date = PurchaseRequest.insertDate();
        assertNotNull(date);
        assertTrue(date.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    public void testValidationCreditCard() throws ParseException, SQLException {
        Date date = new Date("2024/11/06");

        CreditCard customer = new CreditCard(1, "12345678", "123", date, true, "Rodrigo S", "12345678", new BigDecimal(450.43), "Rodrigo Santos");

        CreditCard seller = new CreditCard(2, "987654321", "321", date, true, "Ana S","12345678", new BigDecimal(500.34), "Ana Santos");

        //Valid customer credit card
        List<CreditCard> fieldsValidateCustomer =  ValidateCreditCard.validateFilds(customer);
        boolean isValidCustomer = ValidateCreditCard.validateCreditCardInfo(fieldsValidateCustomer);

        //Valid seller credit card
        List<CreditCard> fieldsValidateSeller =  ValidateCreditCard.validateFilds(seller);
        boolean isValidSeller = ValidateCreditCard.validateCreditCardInfo(fieldsValidateSeller);


        assertTrue(isValidCustomer);
        assertTrue(isValidSeller);

    }

    @Test
    public void testRequestBuy() throws SQLException {
        PurchaseRequest purchaseRequest = Mockito.mock(PurchaseRequest.class);
        Mockito.when(purchaseRequest.getProduct().getProductPrice()).thenReturn(new BigDecimal(1));
     //   PostgreConnection.openConnection();
        boolean result = PurchaseService.makePurchase(purchaseRequest, "1");
        assertTrue(result);


    }

*/
}
