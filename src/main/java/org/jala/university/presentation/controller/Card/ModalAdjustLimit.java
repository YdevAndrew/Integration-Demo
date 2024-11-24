package org.jala.university.presentation.controller.Card;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jala.university.application.service.service_card.AddCustomLimitService;
import org.jala.university.application.service.service_card.LimitCreditCardService;


import java.io.IOException;
import java.math.BigDecimal;

public class ModalAdjustLimit {

    @FXML
    private Slider limitSlider;

    @FXML
    private Region fillBar;

    @FXML
    private Label valueLabelDisplay, valueLabel;

    /**
     * Initializes the controller and sets up the listener for the slider to update the displayed value.
     */
    public void initialize() {
        valueLabel.setText(String.valueOf(searchLimit()));
        BigDecimal limit = searchLimit();
        limitSlider.setMax(limit.doubleValue());

        limitSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double percent = newVal.doubleValue() / limitSlider.getMax();
            double thumbWidth = 15.0;
            double fillWidth = percent * (limitSlider.getWidth() - thumbWidth) + (thumbWidth / 2);
            fillBar.setPrefWidth(fillWidth);

            double sliderValue = newVal.doubleValue();
            valueLabelDisplay.setText(String.format("$%,.2f", sliderValue));
        });
    }


    public static BigDecimal searchLimit(){
        return LimitCreditCardService.findLimitCreditByCardNumber(CardsPageController.getCardClicked()).getLimit_credit();
    }

    /**
     * Updates the limit in the backend by calling the backend logic.
     * @param newLimit The new limit to be set.
     */
    private void updateLimitOnBackend(double newLimit) {
        new Thread(() -> {
            try {
                System.out.println(CardsPageController.getCardClicked());

                if (newLimit != 0) {

                    AddCustomLimitService.validateAndSaveOrUpdateCustomLimit(CardsPageController.getCardClicked(), BigDecimal.valueOf(newLimit));
                    Platform.runLater(() -> {
                        showSuccessAlert();
                        valueLabelDisplay.setText(String.format("New Limit: $%,.2f", newLimit));
                    });
                } else {
                    Platform.runLater(() -> showErrorAlert("The limit value is invalid."));
                }
            } catch (Exception e) {
                Platform.runLater(() -> showErrorAlert("An error occurred: " + e.getMessage()));
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Displays a success message to the user.
     */
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText("Limit successfully adjusted!");
        alert.showAndWait();
    }

    /**
     * Displays an error message to the user.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the action of the "Set" button to adjust the limit.
     */
    @FXML
    private void handleSetButtonAction() {
        double newLimit = limitSlider.getValue();
        updateLimitOnBackend(newLimit);
    }

    /**
     * Opens the modal for increasing the credit card limit.
     * @param event The action event triggered by the user.
     */
    @FXML
    private void increaseLimit(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/increaseLimit/modal-increase-limit.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Increase Limit");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
