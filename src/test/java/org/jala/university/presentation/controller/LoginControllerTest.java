package org.jala.university.presentation.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.jala.university.application.service.service_account.CustomerService;
import org.jala.university.presentation.controller.Account.LoginController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class LoginControllerTest extends ApplicationTest {

    @Mock
    private CustomerService customerService;

    private LoginController loginController;

    @Start
    public void start(Stage stage) throws Exception {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Account/login.fxml"));

        // Inject the mock CustomerService
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == LoginController.class) {
                return new LoginController(customerService);
            }
            return null;
        });

        Parent root = loader.load();
        loginController = loader.getController();

        // Setup scene and stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInvalidLogin() {
        // Arrange
        when(customerService.authenticate(anyString(), anyString())).thenReturn(false);

        // Act
        clickOn("#cpfField").write("12345678900");
        clickOn("#passwordField").write("wrongpassword");
        clickOn("#loginButton");

        // Assert
        Label errorLabel = lookup("#errorLabel").query();
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Credenciais inválidas", errorLabel.getText());
        assertTrue(errorLabel.isVisible());
    }



    @Test
    public void testSuccessfulLogin() {
        // Arrange
        when(customerService.authenticate("987.654.321-12", "12345")).thenReturn(true);

        // Act
        clickOn("#cpfField").write("987.654.321-12");
        clickOn("#passwordField").write("12345");
        clickOn("#loginButton");

        // Assert - this is tricky as it changes scenes
        // You might want to verify the stage has changed or a new scene is loaded
        // This is a placeholder assertion
        verify(customerService).authenticate("98765432112", "12345");
    }}

