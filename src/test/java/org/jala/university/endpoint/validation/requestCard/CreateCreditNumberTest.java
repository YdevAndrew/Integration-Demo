package org.jala.university.endpoint.validation.requestCard;

import org.jala.university.requestCard.CreateCreditNumber;
import org.jala.university.services.endpoint.services.CreditCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCreditNumberTest {

    @Mock
    private CreditCardService creditCardService;

    @InjectMocks
    private CreateCreditNumber createCreditNumber;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNumerCreditIsFalseLuhnMeth() {
        String numerCard = "1234567890123456";
        boolean isValid = CreateCreditNumber.isValidLuhn(numerCard);
        assertFalse(isValid);
    }

    @Test
    public void testNumerCreditIsTrueLuhnMeth() {
        String numerCard = CreateCreditNumber.generateValidCardNumber();
        boolean isValid = CreateCreditNumber.isValidLuhn(numerCard);
        assertTrue(isValid);
    }

    /*
    @Test
    public void testIfNumberExistWhenCardExistsShouldReturnTrue() {
        String cardNumber = "12345678";
        CreditCard mockCard = new CreditCard();
        mockCard.setNumber_card(cardNumber);
        when(creditCardService.getCreditCardByNumberCard(cardNumber)).thenReturn(mockCard);
        boolean result = CreateCreditNumber.ifNumberExist(cardNumber);
        assertTrue(result);
    }
*/


}
