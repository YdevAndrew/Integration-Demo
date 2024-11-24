package org.jala.university.presentation.controller.Card;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import org.jala.university.application.service.service_card.CreditCardClientService;
import org.jala.university.infrastructure.persistence_card.tools.ConfigurationsCard;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * This class controls the view for changing the due date of an invoice.
 * It allows the user to select a new due date from a set of toggle buttons
 * and displays a success modal upon confirmation.
 */
public class ChangeInvoiceDueDateController {

    @FXML
    private Button buttonChange;
    @Getter @Setter
    private  String cardClicked;

    /**
     * Opens a modal window to confirm the successful change of the invoice due date.
     * This method is triggered by an ActionEvent, likely a button click.
     * It retrieves the selected due date, closes the current window,
     * and opens a new modal window displaying the updated due date.
     *
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    private void openSuccessModal(ActionEvent event) {
        try {

            ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
            String selectedDay = selectedButton != null ? selectedButton.getText() : "N/A";


            LocalDate nextMonthDate = LocalDate.now().plusMonths(12);
            String nextMonth = nextMonthDate.format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH));
            String nextYear = nextMonthDate.format(DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH));
            String completeDate = nextMonth + " " + selectedDay + "th, " + nextYear;



            Stage currentStage = (Stage) buttonChange.getScene().getWindow();
            currentStage.close();

            if (toggleGroup.getSelectedToggle() != null) {
                assert selectedButton != null;

                // Verifica se a atualização é permitida
                if (CreditCardClientService.updatePaymentIsAvaliable(CardsPageController.getCardClicked())) {
                    ConfigurationsCard.addDatePayment(CardsPageController.getCardClicked(), selectedButton.getText());

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/changeInvoiceDueDate/sucess_modal.fxml"));
                    Parent root = loader.load();

                    SuccessModalController controller = loader.getController();
                    controller.setDueDate(selectedDay, completeDate);

                    Stage successStage = new Stage();
                    successStage.setScene(new Scene(root));
                    successStage.setTitle("Success!");
                    successStage.initModality(Modality.APPLICATION_MODAL);
                    successStage.setResizable(false);
                    successStage.initStyle(StageStyle.UTILITY);
                    successStage.show();

                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/changeInvoiceDueDate/fail_modal.fxml"));
                    Parent root = loader.load();

                    Stage failStage = new Stage();
                    failStage.setScene(new Scene(root));
                    failStage.setTitle("Fail!");
                    failStage.initModality(Modality.APPLICATION_MODAL);
                    failStage.setResizable(false);
                    failStage.initStyle(StageStyle.UTILITY);
                    failStage.show();
                }



        }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private ToggleButton button1, button2, button3, button4, button5, button6, button7, button8;

    private ToggleGroup toggleGroup;

    /**
     * Initializes the controller. This method is automatically called after the FXML file has been loaded.
     * It sets up a toggle group for the due date selection buttons.
     */
    @FXML
    public void initialize() {
        System.out.println(CardsPageController.getCardClicked());
        toggleGroup = new ToggleGroup();
        button1.setToggleGroup(toggleGroup);
        button2.setToggleGroup(toggleGroup);
        button3.setToggleGroup(toggleGroup);
        button4.setToggleGroup(toggleGroup);
        button5.setToggleGroup(toggleGroup);
        button6.setToggleGroup(toggleGroup);
        button7.setToggleGroup(toggleGroup);
        button8.setToggleGroup(toggleGroup);
    }

}