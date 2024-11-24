package org.jala.university.presentation.controller.Card;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import lombok.Getter;
import org.jala.university.application.dao_card.CustomerInformationDAO;
import org.jala.university.application.service.service_card.BillingAddressService;
import org.jala.university.application.service.service_card.FinancialInformationService;
import org.jala.university.domain.entity.entity_card.FinancialInformation;
import org.jala.university.domain.entity.entity_card.LimitCreditCardTool;
import org.jala.university.infrastructure.persistence_card.CreateCreditCard;
import org.jala.university.utils.utils_card.formInformation.ValidationFinancialInformation;
import org.jala.university.domain.entity.entity_card.BillingAddress;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class controls the view for a financial question modal.
 * It provides functionality to close the modal window.
 */
public class FinancialQueController {

    @FXML
    private RadioButton yesRadio;
    @FXML
    private RadioButton noRadio;
    @FXML
    private ToggleGroup toggleGroup2;
    @FXML
    private TextField incomeField;
    @FXML
    private RadioButton cltRadio;
    @FXML
    private RadioButton independentRadio;
    @FXML
    private RadioButton businesspersonRadio;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private TextField positionField;
    @FXML
    private Label creditCardErrorLabel;
    @FXML
    private Label incomeErrorLabel;
    @FXML
    private Label occupationErrorLabel;
    @FXML
    private Label positionErrorLabel;
    @FXML
    private ToggleGroup creditCardGroup;
    @FXML
    private ToggleGroup occupationGroup;
    @Getter
    private static List<FinancialInformation> formInformation = new ArrayList<>();

    @FXML
    public void initialize() {
        creditCardGroup = new ToggleGroup();
        yesRadio.setToggleGroup(creditCardGroup);
        noRadio.setToggleGroup(creditCardGroup);

        occupationGroup = new ToggleGroup();
        cltRadio.setToggleGroup(occupationGroup);
        independentRadio.setToggleGroup(occupationGroup);
        businesspersonRadio.setToggleGroup(occupationGroup);
    }

    @FXML
    public boolean validateForm(ActionEvent event) {
        clearErrorMessages();

        boolean isValid = true;
        ValidationFinancialInformation.validAll(creditCardGroup, creditCardErrorLabel, incomeField, incomeErrorLabel, occupationGroup, occupationErrorLabel,
                positionField, positionErrorLabel);

        if(!ValidationFinancialInformation.validValues(creditCardGroup, incomeField, occupationGroup, positionField)) {
            isValid = false;
        }
        if (!yesRadio.isSelected() && !noRadio.isSelected()) {
            creditCardErrorLabel.setText("Please select an option.");
            isValid = false;
        }

        String income = incomeField.getText().trim();
        if (income.isEmpty()) {
            incomeErrorLabel.setText("Income is required.");
            incomeField.setStyle("-fx-border-color: #d44f43;");
            isValid = false;
        }

        if (!cltRadio.isSelected() && !independentRadio.isSelected() && !businesspersonRadio.isSelected()) {
            occupationErrorLabel.setText("Please select an occupation type.");
            isValid = false;
        }

        String position = positionField.getText().trim();
        if (position.isEmpty()) {
            positionErrorLabel.setText("Position is required.");
            positionField.setStyle("-fx-border-color: #d44f43;");
            isValid = false;
        }

        return isValid;
    }

    private void clearErrorMessages() {
        creditCardErrorLabel.setText("");
        incomeErrorLabel.setText("");
        occupationErrorLabel.setText("");
        positionErrorLabel.setText("");

        incomeField.setStyle("");
        positionField.setStyle("");
    }

    @FXML
    public void handleSendButton(ActionEvent event) throws SQLException {




        if (validateForm(event)) {
            save();
            if(LimitCreditCardTool.calculate(incomeField.getText(), 25, 50)){

                FinancialInformationService financialInformationService = new FinancialInformationService();
                BillingAddressService billingAddressService = new BillingAddressService();

                BillingAddress billingAddress = PersonalInfoController.getFormInformation().get(0);
                FinancialInformation financialInformation = FinancialQueController.getFormInformation().get(0);

                financialInformationService.savefinancialInformation(financialInformation);
                billingAddressService.saveBillingAddress(billingAddress);
                System.out.println("Form submitted successfully!");
                closeModal(event);

                createCredit();
                sendCodeByEmail(true);



            }else {
                occupationErrorLabel.setText("You month income is low");
                sendCodeByEmail(false);
            }
        } else {
            System.out.println("Form validation failed.");
        }
    }

    /**
     * Closes the current modal window.
     * This method is typically triggered by a button click within the modal.
     *
     * @param event The ActionEvent that triggered this method, usually a button click.
     */
    @FXML
    public void closeModal(ActionEvent event) {
        Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAtual.close();
    }

    public static void sendCodeByEmail(boolean isValid) throws SQLException {

        if(isValid) {
            String body = "Your card has been successfully requested and accepted.";
            String subjectSucess = "JalaU credit card request";

            ChangePasswordController.sendEmail(CustomerInformationDAO.selectCustomer().get(0).getEmail(), subjectSucess, body);
        }else {
            String body = "Your card was requested and unfortunately it was not accepted.";
            String subjectFall = "JalaU credit card request";

            ChangePasswordController.sendEmail(CustomerInformationDAO.selectCustomer().get(0).getEmail(), subjectFall, body);
        }

    }



    public void save() {
        formInformation.clear();
        String marritalStatus = PersonalInfoController.getMarritalStatus();
        String creditStatus = ((RadioButton) creditCardGroup.getSelectedToggle()).getText();
        String ocupationStatus = ((RadioButton) occupationGroup.getSelectedToggle()).getText();
        FinancialInformation saveInfo = new FinancialInformation(
                convertSelectInBoolean(creditStatus), new BigDecimal(incomeField.getText()), ocupationStatus, positionField.getText(),
                PersonalInfoController.getMarritalStatus()
        );

        formInformation.add(saveInfo);
    }




    public void createCredit() throws SQLException {

        CreateCreditCard.createCreditCardClientWithVirtual(incomeField.getText());


    }




    public static boolean convertSelectInBoolean(String value){
        return value.equals("Yes");

    }








}
