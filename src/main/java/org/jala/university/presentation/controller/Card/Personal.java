package org.jala.university.presentation.controller.Card;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.jala.university.application.service.service_card.BillingAddressService;
import org.jala.university.domain.entity.entity_card.BillingAddress;
import org.jala.university.application.dao_card.CustomerInformationDAO;
import org.jala.university.application.dto.dto_card.accountModule.CustomerBankDTO;
import org.jala.university.application.service.service_card.CepApplication;
import org.jala.university.application.service.service_card.LoggedInUser;
import org.jala.university.infrastructure.PostgreConnection;
import org.jala.university.utils.utils_card.billingAddress.ValidationFields;
import org.jala.university.utils.utils_card.formInformation.ValidationPersonalInfo;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * controller from personal page
 */
@Getter
@Setter
public class Personal {

    @FXML
    private Button btnNext;
    @FXML
    Label lblFullName;
    @FXML
    Label lblCPF;
    @FXML
    TextField txtCEP;
    @FXML
    TextField txtStreet;
    @FXML
    TextField txtCity;
    @FXML
    TextField txtState;
    @FXML
    TextField txtCountry;
    @FXML
    private Label lblResultMarried;
    @FXML
    Label lblCEPResult;
    @FXML
    private Label lblStreetResult;
    @FXML
    private Label lblCityResult;
    @FXML
    private Label lblStateResult;
    @FXML
    private Label lblCountryResult;
    @FXML
    private RadioButton rbMarried, rbDowager, rbOther;
    @FXML
    private ToggleGroup toggleGroup;

    @Getter
    private static List<BillingAddress> formInformation = new ArrayList<>();
    @Getter @Setter
    private static String marritalStatus;

    @FXML
    public void initialize() throws SQLException {
        SelectData();

        toggleGroup = new ToggleGroup();
        rbMarried.setToggleGroup(toggleGroup);
        rbDowager.setToggleGroup(toggleGroup);
        rbOther.setToggleGroup(toggleGroup);

        txtCEP.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                txtStreet.clear();
                txtCity.clear();
                txtState.clear();
                txtCountry.clear();
                String cep = txtCEP.getText();
                if (cep.length() == 8 && ValidationFields.validCEP(cep)) {
                    updateAddress(cep);
                }
            }
        });
    }

    /***
     * call the class to search the cep in the api and update the form
     * @param cep is the field that the bank customer put in the form
     */
    void updateAddress(String cep) {
        try {
            List<String> endereco = CepApplication.getCep(cep);
            if (endereco.size() == 3) {
                txtStreet.setText(endereco.get(0));
                txtCity.setText(endereco.get(1));
                txtState.setText(endereco.get(2));
            } else {
                txtStreet.setText("");
                txtCity.setText("");
                txtState.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * function called when the button is clicked, validation every field and return failure message or show the next page
     */
    public void onBtnClick() {
        try {
            ValidationPersonalInfo.validationAll(txtCEP, lblCEPResult, txtStreet, lblStreetResult, txtCity, lblCityResult, txtState, lblStateResult, txtCountry, lblCountryResult, rbMarried, rbDowager, rbOther, toggleGroup, lblResultMarried);
            if (ValidationPersonalInfo.validationValues(txtCEP, txtStreet, txtCity, txtState, txtCountry, toggleGroup)) {
                saveData();
                select();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/gui/sketch/financial/financial.fxml"));
                AnchorPane mainView = loader.load();
                Stage currentStage = (Stage) txtCEP.getScene().getWindow();
                Scene newScene = new Scene(mainView);
                currentStage.setScene(newScene);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * responsible to hide the full name and cpf in the labels
     * @throws SQLException
     */
    public void SelectData() throws SQLException {

        PostgreConnection.getInstance("jala_bank").getConnection();
        List<CustomerBankDTO> data = CustomerInformationDAO.selectCustomer();
        data.forEach(datas -> {
            lblFullName.setText(datas.getFull_name());
            lblCPF.setText(datas.getCpf());
        });
    }


    /***
     * responsible for saving the data entered in the form
     */
    public void saveData() throws SQLException {
        formInformation.clear();

        String maritalStatus = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
        setMarritalStatus(marritalStatus);
        BillingAddress saveInfo = new BillingAddress(
                txtCEP.getText(), txtStreet.getText(), txtCity.getText(),
                txtState.getText(), txtCountry.getText(), Integer.parseInt(LoggedInUser.getLogInUser())
        );

        formInformation.add(saveInfo);
    }

    /***
     * a function to test the select
     */
    public static void select() {

        for(BillingAddress e: Personal.getFormInformation()){
            System.out.println(e);
        }

    }





}
