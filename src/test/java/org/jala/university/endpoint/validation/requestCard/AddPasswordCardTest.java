package org.jala.university.endpoint.validation.requestCard;

import org.jala.university.requestCard.AddPasswordCard;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

public class AddPasswordCardTest {

    @Test
    public void testValidPasswordShouldReturnTrue_WhenPasswordIsValid() {
        String validPassword = "123456";
        boolean isValid = AddPasswordCard.validPassword(validPassword);
        assertTrue(isValid, "Passowrd needs to be valid");
    }


    @Test
    public void testHashPasswordShouldGenerateValidHash() {
        String password = "123456";
        String hashedPassword = AddPasswordCard.hashPassword(password);

        assertNotNull(hashedPassword, "Hash password can't be null.");
        assertTrue(BCrypt.checkpw(password, hashedPassword), "Password isn't equals to hashed password.");
    }

    @Test
    public void testCheckPasswordShouldReturnTrueWhenPasswordMatchesHash() {
        String password = "54656334";
        String hashedPassword = AddPasswordCard.hashPassword(password);

        boolean isMatch = AddPasswordCard.checkPassword(password, hashedPassword);
        assertTrue(isMatch, "Password need to be equals to hashed password.");
    }

    @Test
    public void testCheckPasswordShouldReturnFalseWhenPasswordDoesNotMatchHash() {
        String password = "123456";
        String incorrectPassword = "1111111";
        String hashedPassword = AddPasswordCard.hashPassword(password);

        boolean isMatch = AddPasswordCard.checkPassword(incorrectPassword, hashedPassword);
        assertFalse(isMatch, "The password have to be different from hashed password");
    }


    @Test
    public void testValidationShouldPrintInvalidPasswordMessageWhenPasswordIsInvalid() {
        String password = "123";


        var outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));


        AddPasswordCard.validation(password);


        assertTrue(outputStream.toString().contains("Invalid password!"), "The input have to be a invalid message");
    }
}
