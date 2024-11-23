package org.jala.university.presentation.controller.Account;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class ProfileViewController {

    @FXML
    private Button profileButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button updateProfilePictureButton;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView duoIconsImageView;

    @FXML
    private ImageView logoutImageView;

    @FXML
    private ImageView helpImageView;

    @FXML
    private ImageView solarPenImageView;

    @FXML
    private ImageView logoImageView;

    // Método de inicialização
    @FXML
    private void initialize() {
        // Código de inicialização, se necessário
    }

    // Manipulador para o botão Profile
    @FXML
    private void handleProfileButtonAction(ActionEvent event) {
        // Implementar a lógica para navegar para a página de perfil
        showAlert(AlertType.INFORMATION, "Profile", "Navegar para a página de perfil.");
    }

    // Manipulador para o botão Dashboard
    @FXML
    private void handleDashboardButtonAction(ActionEvent event) {
        // Implementar a lógica para navegar para o dashboard
        showAlert(AlertType.INFORMATION, "Dashboard", "Navegar para o dashboard.");
    }

    // Manipulador para o botão Help
    @FXML
    private void handleHelpButtonAction(ActionEvent event) {
        // Implementar a lógica para mostrar ajuda
        showAlert(AlertType.INFORMATION, "Help", "Abrir a seção de ajuda.");
    }

    // Manipulador para o botão Logout
    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        // Implementar a lógica para logout
        boolean confirm = confirmAction("Logout", "Tem certeza que deseja sair?");
        if (confirm) {
            // Implementar a lógica de logout, como fechar a aplicação ou voltar para a tela de login
            System.exit(0);
        }
    }

    // Manipulador para o botão Update Profile Picture
    @FXML
    private void handleUpdateProfilePictureButtonAction(ActionEvent event) {
        // Implementar a lógica para atualizar a foto de perfil
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolher nova foto de perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(updateProfilePictureButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image newImage = new Image(new FileInputStream(selectedFile));
                profileImageView.setImage(newImage);
                showAlert(AlertType.INFORMATION, "Sucesso", "Foto de perfil atualizada.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Erro", "Não foi possível carregar a imagem.");
            }
        }
    }

    // Método utilitário para mostrar alertas
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método utilitário para confirmação de ações
    private boolean confirmAction(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(response -> response == javafx.scene.control.ButtonType.OK).isPresent();
    }
}
