package org.jala.university.presentation.controller.Account;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.jala.university.ServiceFactory;
import org.jala.university.application.dto.dto_account.CustomerDto;
import org.jala.university.application.service.service_account.CustomerService;
import org.jala.university.application.service.service_account.CustomerServiceImpl;
import org.jala.university.domain.entity.entity_account.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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

    // ImageViews
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

    // Labels para dados do cliente
    @FXML
    private Label fullNameLabel;

    @FXML
    private Label cpfLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label streetLabel;

    @FXML
    private Label districtLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneNumberLabel;

    private final CustomerService customerService;

    @Autowired
    public ProfileViewController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @FXML
    private void initialize() {
        //loadCustomerData();
    }


    @FXML
    private void handleProfileButtonAction(ActionEvent event) {
        showAlert(AlertType.INFORMATION, "Profile", "Navegar para a página de perfil.");
    }

    @FXML
    private void handleDashboardButtonAction(ActionEvent event) {
        showAlert(AlertType.INFORMATION, "Dashboard", "Navegar para o dashboard.");
    }

    @FXML
    private void handleHelpButtonAction(ActionEvent event) {
        showAlert(AlertType.INFORMATION, "Help", "Abrir a seção de ajuda.");
    }

    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        boolean confirm = confirmAction("Logout", "Tem certeza que deseja sair?");
        if (confirm) {
            System.exit(0); // Lógica para logout
        }
    }

    @FXML
    private void handleUpdateProfilePictureButtonAction(ActionEvent event) {
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
                // Lógica adicional para salvar a imagem no backend pode ser implementada aqui
            } catch (FileNotFoundException e) {
                showAlert(AlertType.ERROR, "Erro", "Não foi possível carregar a imagem.");
                e.printStackTrace();
            }
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean confirmAction(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(response -> response == javafx.scene.control.ButtonType.OK).isPresent();
    }}
//    private void loadCustomerData() {
//        try {
//            // Recuperando o usuário logado a partir do SecurityContextHolder
//          //  CustomerService customerservice = SecurityContextHolder.getContext().getAuthentication();
//            //String username = customerservice.getCpf(); // O username é o email ou CPF
//
//            // Aqui, você pode usar o username para buscar o cliente
//           // CustomerDto user = customerService.getCustomerByEmail(username);  // Use o email ou CPF para buscar o cliente
//
//            // Agora você preenche as informações na interface
//            fullNameLabel.setText(user.getFullName());
//            cpfLabel.setText(user.getCpf());
//            genderLabel.setText(user.getGender());
//            birthdayLabel.setText(user.getBirthday().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//            streetLabel.setText(user.getStreet());
//            districtLabel.setText(user.getDistrict());
//            stateLabel.setText(user.getState());
//            postalCodeLabel.setText(user.getPostalCode());
//            countryLabel.setText(user.getCountry());
//            emailLabel.setText(user.getEmail());
//            phoneNumberLabel.setText(user.getPhoneNumber());
//        } catch (Exception e) {
//            showAlert(AlertType.ERROR, "Erro", "Não foi possível carregar os dados do cliente.");
//            e.printStackTrace();
//        }
//    }
//}





