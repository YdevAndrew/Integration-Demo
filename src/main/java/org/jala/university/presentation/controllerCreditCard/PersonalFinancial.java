package org.jala.university.presentation.controllerCreditCard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jala.university.application.validation.billingAddress.ValidationFields;

import java.io.IOException;

public class PersonalFinancial {

    @FXML
    Button btnBackPage;
    @FXML
    private Label lblResponse;
    @FXML
    private TextField monthIncome;

    /***
     * function responsible for returning to the previous screen
     */
    @FXML
    private void backPage() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/gui/billing_address/Billing_address.fxml"));
                ScrollPane mainView = loader.load();

                BillingAddress billingAddress = loader.getController();
                billingAddress.fillData();

                Stage stage = (Stage) btnBackPage.getScene().getWindow();
                Scene scene = new Scene(mainView, 600, 400);
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /***
     * function responsible for validating the monthly income field
     */
    public void onBtnClick() {
        try {
            String dataMonthIncome = monthIncome.getText();


            if (!ValidationFields.isNonNegative(dataMonthIncome)) {
                lblResponse.setText("Negative or null");

            }  else {
                lblResponse.setText("Ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


