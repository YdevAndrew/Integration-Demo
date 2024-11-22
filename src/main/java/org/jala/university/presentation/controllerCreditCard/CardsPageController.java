package org.jala.university.presentation.controllerCreditCard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import org.jala.university.requestCard.ActiveCreditCard;
import org.jala.university.services.endpoint.modal.CreditCard;
import org.jala.university.services.endpoint.services.CreditCardClientService;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Cards page.
 * This class handles user interactions and manages the display of the Cards page.
 */
public class CardsPageController {

    @FXML
    private Label physicalCardNumber, physicalDateCard, physicalNameSurname;
    @FXML
    private Label virtualCardNumber, virtualDateCard, virtualNameSurname;
    @FXML
    private Button blockButtonPhysical, blockButtonVirtual, deletCardButton, buttonAdjustLimit, buttonAdjustLimit2;
    @FXML
    private Label statusCard;

    @Getter @Setter
    private static String cardClicked;

    @FXML
    private ImageView physicalCardsImage1, VirtualCardsImage2, configVirtual, configPhysical;
    @FXML
    private MenuButton menuButtonVirtual, menuButtonPhysical;
    @FXML
    private Text textPhysical, textVirtual;

    /**
     * Initializes the controller.
     * This method is called when the FXML file is loaded.
     * It currently prints a message to the console indicating that the controller is being initialized.
     */
    @FXML
    private void initialize() throws IOException {

            if(validIfPhysicalExist() == null && validIfVirtualExist() == null){
                textPhysical.setText("");
                textVirtual.setText("");
                physicalCardsImage1.setVisible(false);
                VirtualCardsImage2.setVisible(false);
                physicalCardNumber.setVisible(false);
                physicalDateCard.setVisible(false);
                physicalNameSurname.setVisible(false);
                virtualCardNumber.setVisible(false);
                virtualDateCard.setVisible(false);
                virtualNameSurname.setVisible(false);
                blockButtonPhysical.setVisible(false);
                blockButtonVirtual.setVisible(false);
                statusCard.setVisible(false);
                configVirtual.setVisible(false);
                configPhysical.setVisible(false);
                menuButtonVirtual.setVisible(false);
                menuButtonPhysical.setVisible(false);
            }else if(Boolean.TRUE.equals(CardsPageController.validIfPhysicalExist()) && CardsPageController.validIfVirtualExist() == null) {
                addInfo();
                checkStatusCard();
                textVirtual.setText("");
                VirtualCardsImage2.setVisible(false);
                virtualCardNumber.setVisible(false);
                virtualDateCard.setVisible(false);
                virtualNameSurname.setVisible(false);
                blockButtonVirtual.setVisible(false);
                statusCard.setVisible(false);
                configVirtual.setVisible(false);
                menuButtonVirtual.setVisible(false);
                System.out.println("Inicializando CardsPageController");
            }else{
                addInfo();
                checkStatusCard();
            }


    }


    /**
     * Opens the "Adjust Limit" modal window.
     * This method is called when the "Adjust Limit" button is clicked.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    void adjustLimit(ActionEvent event) {
        try {
            Button sourceButton = (Button) event.getSource();
            String buttonId = sourceButton.getId();

            if ("buttonAdjustLimit".equals(buttonId)) {
                setCardClicked(physicalCardNumber.getText());
                System.out.println("Botão de Alterar Senha 1 clicado.");
            } else if ("buttonAdjustLimit2".equals(buttonId)) {
                setCardClicked(virtualCardNumber.getText());
                System.out.println("Botão de Alterar Senha 2 clicado.");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/creditCardFXMLs/adjustLimit/modal_adjust_limit.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Adjust Limit");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deletCard(ActionEvent event) throws IOException {
    try {
        Button sourceButton = (Button) event.getSource();
        String buttonId = sourceButton.getId();

        if ("deletCardButton".equals(buttonId)) {
            setCardClicked(virtualCardNumber.getText());
            System.out.println("Botão de Alterar Senha 2 clicado.");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/deletVirtualCard/delet_virtual_card.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Delete Virtual Card");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        stage.show();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    }
    /**
     * Opens the "Change Password" modal window.
     * This method is called when the "Change Password" button is clicked.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void changePassword(ActionEvent event) {
        try {
            Button sourceButton = (Button) event.getSource();
            String buttonId = sourceButton.getId();

            if ("changePasswordButton".equals(buttonId)) {
                setCardClicked(physicalCardNumber.getText());
                System.out.println("Botão de Alterar Senha 1 clicado.");
            } else if ("changePasswordButton2".equals(buttonId)) {
                setCardClicked(virtualCardNumber.getText());
                System.out.println("Botão de Alterar Senha 2 clicado.");
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/changePassword/change_password.fxml"));
            Parent root = loader.load();



            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Change Password");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Opens the "Change Invoice Due Date" modal window.
     * This method is called when the "Change Invoice Due Date" button is clicked.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void changeInvoiceDue(ActionEvent event) {
        try {
            Button sourceButton = (Button) event.getSource();
            String buttonId = sourceButton.getId();

            if ("buttonChangeInvoice".equals(buttonId)) {
                setCardClicked(physicalCardNumber.getText());
                System.out.println("Botão de Alterar Senha 1 clicado.");
            } else if ("buttonChangeInvoice2".equals(buttonId)) {
                setCardClicked(virtualCardNumber.getText());
                System.out.println("Botão de Alterar Senha 2 clicado.");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/changeInvoiceDueDate/change_invoice_due_date.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Change Invoice Due Date");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the UI with credit card information.
     * This method loads the credit card details (physical and virtual cards).
     */
    public void addInfo() throws IOException {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();
        if (creditCards == null) {
            System.err.println("Nenhum cartão foi retornado pelo serviço.");
            return; // Evitar exceções no processamento posterior.
        }
        if (creditCards.isEmpty()) {

            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/personal_information/Form_personal_info.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Persona Info");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

             */
        } else {
            creditCards.forEach(card -> {
                if (card.getTypeCard().equals("Physical")) {
                    physicalDateCard.setText(card.getExpiration_date());
                    physicalCardNumber.setText(card.getNumberCard());
                    physicalNameSurname.setText(card.getName_card());
                    System.out.println(card.getNumberCard());
                }
                if (card.getTypeCard().equals("Virtual")) {
                    virtualDateCard.setText(card.getExpiration_date());
                    virtualCardNumber.setText(card.getNumberCard());
                    virtualNameSurname.setText(card.getName_card());
                }
            });
        }
    }

    public static void loadCards() throws IOException {
        CardsPageController cardsPageController = new CardsPageController();
        cardsPageController.addInfo();
    }

    /**
     * Updates the status of the credit card buttons based on their current state.
     */
    public void checkStatusCard() {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();
        creditCards.forEach(card -> {
            if (card.getTypeCard().equals("Physical")) {
                if (card.isActive()) {
                    blockButtonPhysical.setText("Block");
                } else {
                    blockButtonPhysical.setText("Unblock");
                }
            }
            if (card.getTypeCard().equals("Virtual")) {
                if (card.isActive()) {
                    blockButtonVirtual.setText("Block");
                    statusCard.setText("Active");
                } else {
                    blockButtonVirtual.setText("Unblock");
                    statusCard.setText("Blocked");
                }
            }
        });
    }

    /**
     * Handles blocking/unblocking of the physical credit card.
     */
    @FXML
    public void blockButtonPhysical(ActionEvent event) {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();
        creditCards.forEach(card -> {
            if (card.getTypeCard().equals("Physical")) {
                if (card.isActive()) {
                    ActiveCreditCard.desactiveCreditCard(card.getNumberCard());
                    blockButtonPhysical.setText("Unblock");
                } else {
                    ActiveCreditCard.activateCreditCard(card.getNumberCard());
                    blockButtonPhysical.setText("Block");
                }
            }
        });
    }

    /**
     * Handles blocking/unblocking of the virtual credit card.
     */
    @FXML
    public void blockButtonVirtual(ActionEvent event) {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();
        creditCards.forEach(card -> {
            if (card.getTypeCard().equals("Virtual")) {
                if (card.isActive()) {
                    ActiveCreditCard.desactiveCreditCard(card.getNumberCard());
                    blockButtonVirtual.setText("Unblock");
                    statusCard.setText("Blocked");
                } else {
                    ActiveCreditCard.activateCreditCard(card.getNumberCard());
                    blockButtonVirtual.setText("Block");
                    statusCard.setText("Active");
                }
            }
        });
    }

    public static String returnPhysicalCard() {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();

        for (CreditCard card : creditCards) {
            if (card.getTypeCard().equals("Physical")) {
                return card.getNumberCard();
            }
        }

        return null;
    }

    public static Boolean validIfVirtualExist() {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();

        for (CreditCard card : creditCards) {
            if (card.getTypeCard().equals("Virtual")) {
                return true;
            }
        }

        return null;
    }

    public static Boolean validIfPhysicalExist() {
        List<CreditCard> creditCards = CreditCardClientService.searchCreditCardsByAccountId();

        for (CreditCard card : creditCards) {
            if (card.getTypeCard().equals("Physical") ) {
                return true;
            }
        }

        return null;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(CardsPageController.validIfVirtualExist());
    }
}
