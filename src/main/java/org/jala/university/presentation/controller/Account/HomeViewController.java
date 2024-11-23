package org.jala.university.presentation.controller.Account;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.jala.university.Config.Account.SpringFXMLLoader;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class HomeViewController {

    @FXML
    public Hyperlink helpLink;

    @FXML
    private AnchorPane topBar;

    @FXML
    private ImageView backgroundImage;

    @FXML
    private ImageView headerImage;

    @FXML
    private Button registerButton;  // Certifique-se de que o nome e tipo estão corretos

    @FXML
    public Button getRegisterButton() {
        return registerButton;
    }

    @FXML
    public void setRegisterButton(Button registerButton) {
        this.registerButton = registerButton;
    }

    @FXML
    public void initialize() {
        if (registerButton != null) {
            AnchorPane.setTopAnchor(registerButton, 50.0); // Só executa se registerButton não for nulo
        } else {
            System.out.println("registerButton é nulo!");
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = SpringFXMLLoader.create("/Account/login.fxml");
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = SpringFXMLLoader.create("/Account/register-user.fxml");

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void showHelpModal(ActionEvent event) {
        try {
            // Carregar o FXML do modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Account/help.fxml"));
            Parent root = loader.load();

            // Criar um novo Stage para o modal
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            modalStage.setTitle("Help");

            // Configurar a cena do modal
            Scene scene = new Scene(root);
            scene.setFill(null); // Deixa o fundo transparente, se necessário
            modalStage.setScene(scene);

            // Exibir o modal
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

