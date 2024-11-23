// package org.jala.university.presentation.controller;

// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import org.jala.university.infrastructure.config.SpringConfig;
// import org.jala.university.presentation.controller.Account.RegisterUserController;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.annotation.AnnotationConfigApplicationContext;
// import org.testfx.framework.junit5.ApplicationTest;
// import org.testfx.util.WaitForAsyncUtils;
// import org.jala.university.Config.Account.SpringFXMLLoader;

// import static org.junit.jupiter.api.Assertions.*;

// public class RegisterUserControllerTest extends ApplicationTest {

//     private RegisterUserController controller;

//     @Override
//     public void start(Stage stage) throws Exception {
//         // Inicialize o contexto manualmente
//         ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
//         SpringFXMLLoader.setApplicationContext(context); // Injetando o contexto no SpringFXMLLoader

//         FXMLLoader loader = SpringFXMLLoader.create("/Account/register-user.fxml");
//         Parent root = loader.load();

//         controller = loader.getController();
//         Scene scene = new Scene(root);
//         stage.setScene(scene);
//         stage.show();
//         stage.toFront();
//         stage.requestFocus();
//     }

//     @BeforeEach
//     public void setup() {

//         controller.initialize();

//     }

//     @Test
//     public void validateAll_withValidInput_shouldReturnTrue() {
//         clickOn("#nameField").write("João Silva");
//         clickOn("#cpfField").write("123.456.789-00");
//         clickOn("#birthDateField").write("1/11/2024");
//         clickOn("#genderField").clickOn("Male");
//         clickOn("#emailField").write("joao.silva@domain.com");
//         clickOn("#phoneField").write("(11) 99999-9999");
//         clickOn("#cepField").write("12345-678");
//         clickOn("#streetField").write("Rua das Flores");
//         clickOn("#districtField").write("Centro");
//         clickOn("#stateField").clickOn("AL");
//         clickOn("#countryField").clickOn("Brazil");

//         boolean isValid = controller.validateAll();
//         boolean isCPFValid = controller.validateCPF();

//         assertTrue(isValid, String.valueOf(isCPFValid));
//         assertTrue(controller.nameError.getText().isEmpty());
//         assertTrue(controller.cpfError.isVisible());
//         assertTrue(controller.birthDateError.getText().isEmpty());
//         assertTrue(controller.genderError.getText().isEmpty());
//         assertTrue(controller.emailError.getText().isEmpty());
//         assertTrue(controller.phoneError.getText().isEmpty());
//         assertTrue(controller.cepError.getText().isEmpty());
//         assertTrue(controller.streetError.getText().isEmpty());
//         assertTrue(controller.districtError.getText().isEmpty());
//         assertTrue(controller.stateError.getText().isEmpty());
//         assertTrue(controller.countryError.getText().isEmpty());
//     }

//     @Test
//     public void validateEmail_withInvalidEmail_shouldReturnFalse() {
//         clickOn("#emailField").write("email_invalido");

//         boolean isValid = controller.validateEmail();
//         WaitForAsyncUtils.waitForFxEvents();

//         assertFalse(isValid);
//         assertEquals("Invalid Email address", controller.emailError.getText());
//         assertTrue(controller.emailError.isVisible());
//     }

//     @Test
//     public void validateCPF_withEmptyCPF_shouldReturnError() {
//         clickOn("#cpfField").write("2378946234238");

//         boolean cpfValid = controller.validateCPF();
//         WaitForAsyncUtils.waitForFxEvents();

//         assertFalse(cpfValid);
//         assertEquals("Invalid CPF", controller.cpfError.getText());
//         assertTrue(controller.cpfError.isVisible());
//     }

//     @Test
//     public void validateCPF_withValidFormattedCPF_shouldReturnTrue() {
//         clickOn("#cpfField").write("237.894.623-42");

//         boolean cpfValid = controller.validateCPF();
//         WaitForAsyncUtils.waitForFxEvents();

//         assertTrue(cpfValid);
//         assertEquals("Invalid CPF", controller.cpfError.getText());
//         assertFalse(controller.cpfError.isVisible());
//     }

//     @Test
//     public void registerTest() {
//         clickOn("#nameField").write("João Silva");
//         clickOn("#cpfField").write("123.456.789-00");
//         clickOn("#birthDateField").write("1/11/2024");
//         clickOn("#genderField").clickOn("Male");
//         clickOn("#emailField").write("joao.silva@domain.com");
//         clickOn("#phoneField").write("(11) 99999-9999");
//         clickOn("#cepField").write("12345-678");
//         clickOn("#streetField").write("Rua das Flores");
//         clickOn("#districtField").write("Centro");
//         clickOn("#stateField").clickOn("AL");
//         clickOn("#countryField").clickOn("Brazil");

//         controller.validateAll();

//         clickOn("#btn_register");
//     }


// }
