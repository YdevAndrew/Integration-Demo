package org.jala.university.presentation.controller.Card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import lombok.Getter;
import org.jala.university.application.service.service_card.BillingAddressService;
import org.jala.university.domain.entity.entity_card.BillingAddress;
import org.jala.university.application.service.service_card.FinancialInformationService;
import org.jala.university.domain.entity.entity_card.FinancialInformation;
import org.jala.university.domain.entity.entity_card.LimitCreditCardTool;
import org.jala.university.infrastructure.persistence_card.CreateCreditCard;
import org.jala.university.utils.utils_card.formInformation.ValidationFinancialInformation;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * controller from financial page
 */
public class Financial {

    @FXML
    private RadioButton rbYes, rbNo;
    @FXML
    private RadioButton rbdCLT, rdbIndependent, rbdBusinessperson;
    @FXML
    private TextField txtIncome, txtCurrentPosition;
    @FXML
    private Label lblHaveCreditCard, lblIncome, lblOcupation, lblCurrentPosition;

    @FXML
    private ToggleGroup creditCardGroup;
    @FXML
    private ToggleGroup occupationGroup;

    @Getter
    private static List<FinancialInformation> formInformation = new ArrayList<>();


    @FXML
    public void initialize() {
        creditCardGroup = new ToggleGroup();
        rbYes.setToggleGroup(creditCardGroup);
        rbNo.setToggleGroup(creditCardGroup);

        occupationGroup = new ToggleGroup();
        rbdCLT.setToggleGroup(occupationGroup);
        rdbIndependent.setToggleGroup(occupationGroup);
        rbdBusinessperson.setToggleGroup(occupationGroup);
    }

    /***
     * function responsible to validate all fields
     */
    @FXML
    public void onSendClick() throws SQLException {

        ValidationFinancialInformation.validAll(creditCardGroup, lblHaveCreditCard, txtIncome, lblIncome, occupationGroup, lblOcupation,
                txtCurrentPosition, lblCurrentPosition);

        if(ValidationFinancialInformation.validValues(creditCardGroup, txtIncome, occupationGroup, txtCurrentPosition)){
            save();
            Personal.select();
            select();

            if(LimitCreditCardTool.calculate(txtIncome.getText(), 25, 50)){

                FinancialInformationService financialInformationService = new FinancialInformationService();
                BillingAddressService billingAddressService = new BillingAddressService();

                BillingAddress billingAddress = Personal.getFormInformation().get(0);
                FinancialInformation financialInformation = Financial.getFormInformation().get(0);

                financialInformationService.savefinancialInformation(financialInformation);
                billingAddressService.saveBillingAddress(billingAddress);

                createCredit();
            }else {
                lblOcupation.setText("You month income is low");
            }
        }


    }

    /***
     * responsible for saving the data entered in the form
     */
    public void save() {
        formInformation.clear();
        String marritalStatus = Personal.getMarritalStatus();
        String creditStatus = ((RadioButton) creditCardGroup.getSelectedToggle()).getText();
        String ocupationStatus = ((RadioButton) occupationGroup.getSelectedToggle()).getText();
        FinancialInformation saveInfo = new FinancialInformation(
                convertSelectInBoolean(creditStatus), new BigDecimal(txtIncome.getText()), ocupationStatus, txtCurrentPosition.getText(),
                Personal.getMarritalStatus()
        );

        formInformation.add(saveInfo);
    }




    public void createCredit() throws SQLException {

        CreateCreditCard.createCreditCardClientWithVirtual(txtIncome.getText());


    }




    public static boolean convertSelectInBoolean(String value){
        return value.equals("Yes");

    }

    /***
     * a function to test the select
     */
    public static void select() {

        for(FinancialInformation e: Financial.getFormInformation()){
            System.out.println(e);
        }

    }

    /***
     * function responsible to save data in the database
     */
    public static void saveOnDatabase(){


    }



    @FXML
    public void onCancelClick() {

    }

    public static void main(String[] args) throws SQLException {
        Financial financial = new Financial();
        financial.createCredit();
    }
}
