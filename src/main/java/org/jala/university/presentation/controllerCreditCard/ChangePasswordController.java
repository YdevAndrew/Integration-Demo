package org.jala.university.presentation.controllerCreditCard;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;
import org.jala.university.application.dao.CustomerInformationDAO;
import org.jala.university.requestCard.AddPasswordCard;
import org.jala.university.requestCard.ConfigurationsCard;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

/**
 * This class controls the view for changing a user's password.
 * It handles user input, validation, and display of error messages.
 */
public class ChangePasswordController {

    @FXML
    private Button resendCodeButton, sendButton;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField repeatPasswordTextField;

    @FXML
    private TextField codeTextField;

    @FXML
    private Label codeErrorLabel;

    @FXML
    private Label repeatPasswordErrorLabel;

    @FXML
    private CheckBox showNewPasswordCheckBox;

    @FXML
    private CheckBox showRepeatPasswordCheckBox;
    @Getter @Setter
    private String generatedCode;


    @FXML
    private void initialize() {
        newPasswordTextField.managedProperty().bind(showNewPasswordCheckBox.selectedProperty());
        newPasswordTextField.visibleProperty().bind(showNewPasswordCheckBox.selectedProperty());
        newPasswordField.managedProperty().bind(showNewPasswordCheckBox.selectedProperty().not());
        newPasswordField.visibleProperty().bind(showNewPasswordCheckBox.selectedProperty().not());
        newPasswordTextField.textProperty().bindBidirectional(newPasswordField.textProperty());

        repeatPasswordTextField.managedProperty().bind(showRepeatPasswordCheckBox.selectedProperty());
        repeatPasswordTextField.visibleProperty().bind(showRepeatPasswordCheckBox.selectedProperty());
        repeatPasswordField.managedProperty().bind(showRepeatPasswordCheckBox.selectedProperty().not());
        repeatPasswordField.visibleProperty().bind(showRepeatPasswordCheckBox.selectedProperty().not());
        repeatPasswordTextField.textProperty().bindBidirectional(repeatPasswordField.textProperty());

    }


    public String generateCode() {
        Random rand = new Random();
        int code = 100000 + rand.nextInt(900000);
        generatedCode = String.valueOf(code);
        setGeneratedCode(generatedCode);
        return generatedCode;
    }


    public void sendCodeByEmail() throws SQLException {
        String code = generateCode();
        String subject = "Your Password Reset Code";
        String body = "Your password reset code is: " + code;
        setGeneratedCode(code);

        sendEmail(CustomerInformationDAO.selectCustomer().get(0).getEmail(), subject, body);
    }

    public static void sendEmail(String to, String subject, String htmlBody) {
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "technologyservicesworld@gmail.com";
        String password = "gvza cqes xohm wlls";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSendButtonAction() throws SQLException {

        String newPassword = newPasswordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        String userCode = codeTextField.getText();

        AddPasswordCard.makeLabel(newPassword, repeatPasswordErrorLabel);

        if (!newPassword.equals(repeatPassword)) {
            repeatPasswordErrorLabel.setText("Passwords do not match.");
            return;
        } else {
            repeatPasswordErrorLabel.setText("");
        }


        if (!userCode.equals(getGeneratedCode())) {
            System.out.println("User Code: " + userCode);
            System.out.println("Generated Code: " + getGeneratedCode());
            codeErrorLabel.setText("Invalid code.");
        } else {
            codeErrorLabel.setText("");
            changePassword(newPassword, CardsPageController.getCardClicked());


        }
    }



    private void changePassword(String newPassword, String numberCard) throws SQLException {


        ConfigurationsCard.addOrUpdatePassword(numberCard, newPassword);


        showSuccessAlert();
    }


    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Your password has been successfully changed.");
        alert.showAndWait();}


    @FXML
    private void handleResendCodeButtonAction() throws SQLException {
        String newPassword = newPasswordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        if(AddPasswordCard.validationWithLabel(newPassword, repeatPasswordErrorLabel)){
            AddPasswordCard.makeLabel(newPassword, repeatPasswordErrorLabel);
            if (!newPassword.equals(repeatPassword)) {
                repeatPasswordErrorLabel.setText("Passwords do not match.");
//                return;
            } else {
                sendCodeByEmail();
            }

        }

    }
}
