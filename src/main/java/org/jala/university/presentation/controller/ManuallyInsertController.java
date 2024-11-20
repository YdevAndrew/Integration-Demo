package org.jala.university.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;

public class ManuallyInsertController {

    @FXML
    private TextField accountField;

    @FXML
    private TextField agencyField;

    @FXML
    private TextField recipientField;

    @FXML
    private TextField amountField;

    @FXML
    private DatePicker endDateField;

    @FXML
    private DatePicker expiredDateField;

    @FXML
    private Button showExpiredDateButton;

    private Pane mainContent;

    // Setter para o painel principal
    public void setMainContent(Pane mainContent) {
        this.mainContent = mainContent;
    }

    // Método para exibir o campo "Expired Date" e desabilitar o botão
    @FXML
    private void showExpiredDateField() {
        expiredDateField.setVisible(true);
        showExpiredDateButton.setDisable(true);
    }

    // Método acionado ao enviar os dados
    @FXML
    private void onSubmit() {
        if (expiredDateField.isVisible() && expiredDateField.getValue() != null) {
            System.out.println("Expired Date: " + expiredDateField.getValue());
        } else {
            System.out.println("Expired Date not provided.");
        }

        if (endDateField.getValue() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Submission Successful");
            alert.setHeaderText(null);
            alert.setContentText("Date selected: " + endDateField.getValue());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a date.");
            alert.showAndWait();
        }
    }

    // Método acionado ao clicar no botão "Insert Manually"
    @FXML
    public void onManualEntryClick(ActionEvent event) {
        System.out.println("Botão 'Insert Manually' foi clicado!");
        System.out.println("Abrir tela de inserção manual...");

        try {
            // Carregando o FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ManualPaymentScreens/QRCodePayment/ManuallyInsert.fxml"));
            Pane manualInsertPane = loader.load();

            // Obtendo o controlador da tela carregada
            ManuallyInsertController manuallyInsertController = loader.getController();

            // Passando o painel principal para o controlador
            manuallyInsertController.setMainContent(mainContent);

            // Substituindo o conteúdo atual pelo novo
            mainContent.getChildren().setAll(manualInsertPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inicializador para configurar formatações e limites de campos
    @FXML
    public void initialize() {
        recipientField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, c ->
                (c.getControlNewText().length() <= 14) ? c : null)); // Limita a 14 caracteres

        accountField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, c ->
                (c.getControlNewText().length() <= 9) ? c : null));

        agencyField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, c ->
                (c.getControlNewText().length() <= 4) ? c : null));
    }
}