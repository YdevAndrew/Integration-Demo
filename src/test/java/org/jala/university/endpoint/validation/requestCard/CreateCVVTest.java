package org.jala.university.endpoint.validation.requestCard;

import org.jala.university.requestCard.CreateCVV;
import org.jala.university.services.endpoint.services.CreditCardService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateCVVTest {
    @Mock
    private CreditCardService creditCardService;

    @InjectMocks
    private CreateCVV createCVV;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    @Test
    public void testIfCvvExistWhenCardExistsShouldReturnTrue() {
        String cvvNumber = "123";
        CreditCard mockCard = new CreditCard();
        mockCard.setCvv(cvvNumber);
        when(creditCardService.getCvvCreditCard(cvvNumber)).thenReturn(mockCard);
        boolean result = CreateCVV.ifCvvExists(cvvNumber);
        assertTrue(result);
    }
*/
/*
    @Test
    public void testIfCvvNoExistWhenCardExistsShouldReturnFalse() {
        String cvvNumber = "423";
        CreditCard mockCard = new CreditCard();
        mockCard.setCvv(cvvNumber);
        when(creditCardService.getCvvCreditCard(cvvNumber)).thenReturn(mockCard);
        boolean result = CreateCVV.ifCvvExists(cvvNumber);
        assertFalse(result);
    }*/
}
