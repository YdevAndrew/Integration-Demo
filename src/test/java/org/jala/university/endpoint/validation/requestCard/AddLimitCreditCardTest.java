package org.jala.university.endpoint.validation.requestCard;

import org.jala.university.application.object.InforrmationForm.LimitCreditCardTool;
import org.jala.university.requestCard.AddLimitCreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class AddLimitCreditCardTest {

    private AddLimitCreditCard addLimitCreditCard;

    @BeforeEach
    public void setUp() {
        addLimitCreditCard = new AddLimitCreditCard();
    }

    @Test
    public void testValidationValue_ShouldReturnTrue_WhenIncomeIsValid() {
        try (MockedStatic<LimitCreditCardTool> mockedStatic = mockStatic(LimitCreditCardTool.class)) {
            mockedStatic.when(() -> LimitCreditCardTool.calculate("3000", 25, 50)).thenReturn(true);
            mockedStatic.when(() -> LimitCreditCardTool.calculateLimit("3000", 25)).thenReturn(750.0);
            boolean isValid = addLimitCreditCard.validationValue("3000");
            assertTrue(isValid);
            assertEquals(750.0, AddLimitCreditCard.getLimit());
        }
    }




    @Test
    public void testAddLimit_ShouldSetLimitMax_WhenIncomeExceedsLimit() {
        try (MockedStatic<LimitCreditCardTool> mockedStatic = mockStatic(LimitCreditCardTool.class)) {
            mockedStatic.when(() -> LimitCreditCardTool.calculateLimit("10000", 25)).thenReturn(10000.0);
            addLimitCreditCard.addlimit("10000");
            assertEquals(5000.0, AddLimitCreditCard.getLimit(), "Limit have to be 5000");
        }
    }

    @Test
    public void testValidationValue_ShouldReturnFalse_WhenIncomeIsInvalid() {
        try (MockedStatic<LimitCreditCardTool> mockedStatic = mockStatic(LimitCreditCardTool.class)) {
            mockedStatic.when(() -> LimitCreditCardTool.calculate("10000", 25, 50)).thenReturn(false);

            boolean isValid = addLimitCreditCard.validationValue("10000");

            assertFalse(isValid, "The value have to be invalid.");
        }
    }
}
