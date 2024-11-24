package org.jala.university.presentation.controller.Card;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.jala.university.commons.presentation.BaseController;
import org.jala.university.domain.entity.entity_card.SaveBillingAddress;
import org.jala.university.utils.utils_card.billingAddress.ValidationFields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillingAddress extends BaseController {
    @FXML
    private ScrollPane root;
    @FXML
    private Label lblAddressResponse;
    @FXML
    private TextField txtPostalCode, txtAddress, txtNeighborhood, txtStreet, txtCountry;
    @FXML
    private Button btnFormFinanciaInformation;

    /***
     * List responsible for storing data previously
     */
    private static final List<SaveBillingAddress> saveInformations = new ArrayList<>();


    /***
     * Function responsible for initializing the screen with the width and height of the window size, and adding the css to the main screen
     */

    @FXML
    public void loadFinancialPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/personal_information/Form_personal_info.fxml"));
            ScrollPane mainView = loader.load();

            Stage stage = (Stage) btnFormFinanciaInformation.getScene().getWindow();
            Scene scene = new Scene(mainView, 600, 400);
            stage.setScene(scene);

            if (root != null) {
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Cards/gui/billing_address/style.cssstyle.css")).toExternalForm());

                stage.widthProperty().addListener((obs, oldVal, newVal) -> root.setPrefWidth(newVal.doubleValue()));
                stage.heightProperty().addListener((obs, oldVal, newVal) -> root.setPrefHeight(newVal.doubleValue()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Function responsible for storing data previously filled in the list so that it can be retrieved later
     */

    public void fillData() {
        if (!saveInformations.isEmpty()) {
            SaveBillingAddress savedData = saveInformations.get(0);
            txtPostalCode.setText(savedData.getPostal_code());
            txtAddress.setText(savedData.getAddress());
            txtNeighborhood.setText(savedData.getNeighborhood());
            txtStreet.setText(savedData.getStreet());
            txtCountry.setText(savedData.getCountry());
        }
    }

    /***
     * Function responsible for calling the validation classes and calling the function to load the next page
     */

    public void onBtnClick() {
        try {
            String postalCode = txtPostalCode.getText();
            String address = txtAddress.getText();
            String neighborhood = txtNeighborhood.getText();
            String street = txtStreet.getText();
            String country = txtCountry.getText();

            if (!ValidationFields.isNumeric(postalCode)) {
                lblAddressResponse.setText("Invalid ZIP Code");
                if (!ValidationFields.isNumeric(street) || !ValidationFields.isNumeric(address)) {
                    lblAddressResponse.setText("Invalid Street");
                }
            } else if (!ValidationFields.isAlphabetic(neighborhood) || !ValidationFields.isAlphabetic(country)) {
                lblAddressResponse.setText("Invalid information");
            } else {
                saveInformations.clear();
                SaveBillingAddress saveBilling = new SaveBillingAddress(postalCode, address, neighborhood, street, country);
                saveInformations.add(saveBilling);

                loadFinancialPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
