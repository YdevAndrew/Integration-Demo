package org.jala.university.presentation.controllerCreditCard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.jala.university.application.dao.CustomerInformationDAO;
import org.jala.university.application.dto.accountModule.CustomerBankDTO;
import org.jala.university.application.model.BillingAddress;

import org.jala.university.application.service.creditcard.BillingAddressService;
import org.jala.university.application.validation.billingAddress.ValidationFields;
import org.jala.university.application.validation.formInformation.ValidationPersonalInfo;
import org.jala.university.database.PostgreConnection;
import org.jala.university.requestCard.CreateCreditCard;
import org.jala.university.services.CepApplication;
import org.jala.university.services.LoggedInUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonalInfoController {

    @FXML
    private TextField cepField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField countryField;
    @FXML
    private RadioButton marriedRadio;
    @FXML
    private RadioButton dowagerRadio;
    @FXML
    private RadioButton otherRadio;
    @FXML
    private Button nextButton;
    @FXML
    private Label cepErrorLabel;
    @FXML
    private Label streetErrorLabel;
    @FXML
    private Label cityErrorLabel;
    @FXML
    private Label stateErrorLabel;
    @FXML
    private Label countryErrorLabel;
    @FXML
    private Label maritalStatusErrorLabel;
    @FXML
    private Label lblName, lblCPF;
    @FXML
    private ToggleGroup toggleGroup;
    @Getter @Setter
    private static String marritalStatus;
    @Getter
    private static List<BillingAddress> formInformation = new ArrayList<>();



    void updateAddress(String cep) {
        try {
            List<String> endereco = CepApplication.getCep(cep);
            if (endereco.size() == 3) {
                streetField.setText(endereco.get(0));
                cityField.setText(endereco.get(1));
                stateField.setText(endereco.get(2));
            } else {
                streetField.setText("");
                cityField.setText("");
                stateField.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() throws SQLException {
        formInformation.clear();

        String maritalStatus = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
        setMarritalStatus(marritalStatus);
        BillingAddress saveInfo = new BillingAddress(
                cepField.getText(), streetField.getText(), cityField.getText(),
                stateField.getText(), countryField.getText(), Integer.parseInt(LoggedInUser.getLogInUser())
        );

        formInformation.add(saveInfo);
    }


    @FXML
    public void initialize() throws SQLException {
        //addCepMask(cepField);
        SelectData();
        toggleGroup = new ToggleGroup();
        marriedRadio.setToggleGroup(toggleGroup);
        dowagerRadio.setToggleGroup(toggleGroup);
        otherRadio.setToggleGroup(toggleGroup);

        cepField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                streetField.clear();
                cityField.clear();
                stateField.clear();
                countryField.clear();
                String cep = cepField.getText();
                if (cep.length() == 8 && ValidationFields.validCEP(cep)) {
                    updateAddress(cep);
                }
            }
        });

    }
    public void SelectData() throws SQLException {

        PostgreConnection.getInstance("jala_bank").getConnection();
        List<CustomerBankDTO> data = CustomerInformationDAO.selectCustomer();
        data.forEach(datas -> {
            lblName.setText(datas.getFull_name());
            lblCPF.setText(datas.getCpf());
        });
    }

    @FXML
    public boolean validateForm() throws SQLException {
        clearErrorMessages();

        boolean isValid = true;
        ValidationPersonalInfo.validationAll(cepField, cepErrorLabel, streetField, streetErrorLabel, cityField, cityErrorLabel, stateField, stateErrorLabel, countryField, countryErrorLabel, marriedRadio, dowagerRadio, otherRadio, toggleGroup, maritalStatusErrorLabel);
        if (!ValidationPersonalInfo.validationValues(cepField, streetField, cityField, stateField, countryField, toggleGroup)) {
            isValid = false;
        }
        String cep = cepField.getText().trim();
        if (cep.isEmpty()) {
            cepErrorLabel.setText("CEP is required.");
            cepField.setStyle("-fx-border-color: #d44f43;");
            isValid = false;
        }

        String street = streetField.getText().trim();
        if (street.isEmpty()) {
            streetErrorLabel.setText("Street is required.");
            streetField.setStyle("-fx-border-color: #d44f43;");
            isValid = false;
        }

        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            cityErrorLabel.setText("City is required.");
            cityField.setStyle("-fx-border-color: #d44f43;");
            isValid = false;
        }

        String state = stateField.getText().trim();
        if (state.isEmpty()) {
            stateErrorLabel.setText("State is required.");
            stateField.setStyle("-fx-border-color: #d44f43;");
            isValid = false;
        }

        String country = countryField.getText().trim();
        if (country.isEmpty()) {
            countryErrorLabel.setText("Country is required.");
            countryField.setStyle("-fx-border-color: #d44f43;");
            isValid = false;
        }

        // Validação dos RadioButtons para marital status
        if (!marriedRadio.isSelected() && !dowagerRadio.isSelected() && !otherRadio.isSelected()) {
            maritalStatusErrorLabel.setText("Please select a marital status.");
            isValid = false;
        }

        return isValid;
    }


    private void clearErrorMessages() {
        cepErrorLabel.setText("");
        streetErrorLabel.setText("");
        cityErrorLabel.setText("");
        stateErrorLabel.setText("");
        countryErrorLabel.setText("");
        maritalStatusErrorLabel.setText("");

        cepField.setStyle("");
        streetField.setStyle("");
        cityField.setStyle("");
        stateField.setStyle("");
        countryField.setStyle("");
    }




    private void addCepMask(TextField textField) {
        textField.setOnKeyTyped(event -> {
            String text = textField.getText();
            text = text.replaceAll("\\D", "");
            if (text.length() > 8) {
                event.consume();
                return;
            }
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                if (i == 5) {
                    formatted.append("-");
                }
                formatted.append(text.charAt(i));
            }
            textField.setText(formatted.toString());
            textField.positionCaret(formatted.length());
        });
    }




    @FXML
    public void loadFinancialQuestions(ActionEvent event) throws SQLException {
        if (validateForm()) {
            try {
                if(CardsPageController.validIfPhysicalExist() == null){
                    // Carrega o FXML do modal "Financial Questions"
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/financial_questions/Form_financial_questions.fxml"));
                    saveData();
                    select();
                    Parent modalFinancialQues = fxmlLoader.load();

                    Stage modalStage = new Stage();
                    modalStage.initModality(Modality.APPLICATION_MODAL);
                    modalStage.setTitle("Financial Questions");
                    modalStage.setScene(new Scene(modalFinancialQues));
                    modalStage.showAndWait();

                }else if(Boolean.TRUE.equals(CardsPageController.validIfPhysicalExist()) && CardsPageController.validIfVirtualExist() == null){
                    BillingAddressService billingAddressService = new BillingAddressService();
                    BillingAddress billingAddress = new BillingAddress();
                    billingAddress.setCep(cepField.getText());
                    billingAddress.setStreet(streetField.getText());
                    billingAddress.setCity(cityField.getText());
                    billingAddress.setState(stateField.getText());
                    billingAddress.setCountry(countryField.getText());

                    billingAddressService.saveBillingAddress(billingAddress);
                    System.out.println("Form submitted successfully!");
                    closeModal(event);
                    CreateCreditCard.createOnlyVirtualCard();
                    FinancialQueController.sendCodeByEmail(true);
                }


                //modalStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para fechar o modal atual
    @FXML
    public void closeModal(ActionEvent event) {
        Stage stageAtual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAtual.close();
    }


    public static void select() {

        for(BillingAddress e: PersonalInfoController.getFormInformation()){
            System.out.println(e);
        }

    }
}
