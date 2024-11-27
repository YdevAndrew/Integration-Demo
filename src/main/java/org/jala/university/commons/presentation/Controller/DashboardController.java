package org.jala.university.commons.presentation.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jala.university.presentation.controller.Card.CardsPageController;
import org.jala.university.application.dto.dto_account.CustomerDto;
import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.application.service.service_account.CustomerService;
import org.jala.university.presentation.controller.Account.ProfileViewController;
import org.jala.university.presentation.controller.Loan.MyLoans;
import org.jala.university.presentation.controller.Loan.SpringFXMLLoader;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.presentation.controller.Transaction.TransactionHistoryController;
import org.jala.university.presentation.controller.Transaction.TransactionPaymentScreenController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class DashboardController extends BaseController {

    private final SpringFXMLLoader springFXMLLoader;

    private final CustomerService customerService;

    private final AccountService accountService;

    @FXML
    public Label nameLabel;

    public Button pixButton;

    public Button transactionHistButton;

    @FXML
    public Label nameLabel2;

    @FXML
    private VBox myCardsVBox;

    @FXML
    private AnchorPane mainViewContainer;

    @FXML
    private VBox otherButtonsVBox;

    @FXML
    private Label balanceLabel;

    @FXML
    private Button transactionButton;

    @FXML
    private Pane contentPane;

    @FXML
    private Button toggleButton;

    @FXML
    private ImageView eyeIcon;

    @FXML
    private Label dateLabel;

    @FXML
    private Pane mainContent;

    private boolean isBalanceVisible = false; // Controle de visibilidade do saldo
    private double balance = 1234.56; // Exemplo de valor do saldo, substitua pelo valor real

    @Autowired
    public DashboardController(SpringFXMLLoader springFXMLLoader, CustomerService customerService, AccountService accountService) {
        this.springFXMLLoader = springFXMLLoader;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @FXML
    public void initialize() {
        loadCustomerInfo();
        // Configura o botão de alternância de exibição do saldo
        toggleButton.setOnAction(event -> toggleBalanceVisibility());

        // Define a data atual no formato desejado e força o local para inglês
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd", Locale.ENGLISH);
        dateLabel.setText(LocalDate.now().format(formatter));
        dateLabel.getStyleClass().add("balance-date");

        // Configurar ações dos botões
        transactionButton.setOnAction(event -> loadTransactionView());
        transactionHistButton.setOnAction(event -> loadTransactionHistoryScreen());
    }

    @FXML
    private void loadInvoiceHistory() throws IOException {
        clearAllPanels();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/invoiceHistory/invoice_history.fxml"));
        AnchorPane invoiceHistory = loader.load();
        mainContent.getChildren().setAll(invoiceHistory);
    }


    @FXML
    public void toggleBalanceVisibility() {
        if (balanceLabel.getText().equals("R$ ********")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Usuário não está autenticado.");
                return;
            }

            String cpf = authentication.getName();
            if (cpf == null || cpf.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro", "CPF do usuário não encontrado.");
                return;
            }

            CustomerDto user = customerService.getCustomerByCpf(cpf);
            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Cliente não encontrado para o CPF: " + cpf);
                return;
            }

            // Mostra o saldo e ajusta o ícone para "olho aberto"
            balanceLabel.setText(String.format("R$ %.2f", accountService.getBalanceByCustomerId(user.getId())));
            eyeIcon.setImage(new Image(getClass().getResource("/img/eye_open.png").toExternalForm()));
        } else {
            // Esconde o saldo e ajusta o ícone para "olho fechado"
            balanceLabel.setText("R$ ********");
            eyeIcon.setImage(new Image(getClass().getResource("/img/eye.png").toExternalForm()));
        }
    }

    @FXML
    private void loadMyCards() throws IOException {
        clearAllPanels();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/cards/cards_page.fxml"));
            Pane cardsPage = loader.load();
            mainContent.getChildren().setAll(cardsPage);
        } catch (IOException e) {
            System.err.println("Erro ao carregar cards_page.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @FXML
    private void loadRequestCard() throws IOException {


        clearAllPanels();
        if(CardsPageController.validIfPhysicalExist() != null){
            if(CardsPageController.validIfVirtualExist() != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/requestACard/request_a_card.fxml"));
                AnchorPane requestCard = loader.load();
                mainContent.getChildren().setAll(requestCard);
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/requestAVirtualCard/request_a_card.fxml"));
                AnchorPane requestCard = loader.load();
                mainContent.getChildren().setAll(requestCard);


            }
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Cards/requestACard/request_a_card.fxml"));
            AnchorPane requestCard = loader.load();
            mainContent.getChildren().setAll(requestCard);
        }

    }

    /**
     * Remove todos os painéis e limpa os contêineres.
     */
    private void clearAllPanels() {
        mainContent.getChildren().clear();
        contentPane.getChildren().clear();
        mainViewContainer.getChildren().clear();
        myCardsVBox.setVisible(false);
        myCardsVBox.setManaged(false);

    }

    @FXML
    private void loadTransactionView() {
        clearAllPanels();
        try {
            clearAllPanels();
            FXMLLoader loader = springFXMLLoader.load("/Transaction/Transection_paymentScreen.fxml");
            Node transactionPane = loader.load();
            TransactionPaymentScreenController controller = loader.getController();
            mainContent.getChildren().add(transactionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadTransactionHistoryScreen(){
        try {
            clearAllPanels();
            FXMLLoader loader = springFXMLLoader.load("/Transaction/Transection_Historical.fxml");
            Node transactionPane = loader.load();
            TransactionHistoryController controller = loader.getController();
            mainContent.getChildren().add(transactionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void scheduleAPayment() throws IOException {
        clearAllPanels(); // Oculta os outros painéis antes de carregar um novo
        FXMLLoader loader = springFXMLLoader.load("/External/SchedulePaymentScreens/SchedulePayment/SchedulePayment.fxml");
        Pane schedulePayment = loader.load();
        mainContent.getChildren().add(schedulePayment);
    }

    @FXML
    private void QRCodePayment() throws IOException {
        clearAllPanels(); // Oculta os outros painéis antes de carregar um novo
        FXMLLoader loader = springFXMLLoader.load("/External/ManualPaymentScreens/QRCodePayment/QRCodePayment.fxml");
        Pane QRCodePayments = loader.load();
        mainContent.getChildren().add(QRCodePayments);
    }

    @FXML
    private void ScheduleService() throws IOException {
        clearAllPanels(); // Oculta os outros painéis antes de carregar um novo
        FXMLLoader loader = springFXMLLoader.load("/External/ScheduleServices/ButtonService.fxml");
        Pane scheduleService = loader.load();
        mainContent.getChildren().add(scheduleService);
    }

    @FXML
    public void handleMyCardsClick() {
        clearAllPanels(); // Garante que outros painéis estejam escondidos
        boolean isVisible = myCardsVBox.isVisible();
        myCardsVBox.setVisible(!isVisible);
        myCardsVBox.setManaged(!isVisible);
        otherButtonsVBox.setVisible(!isVisible);
        otherButtonsVBox.setManaged(!isVisible);
    }

    @FXML
    public void loadMainViewLoan() {
        clearAllPanels();
        try {
            clearAllPanels(); // Oculta os outros painéis antes de carregar o módulo Loan
            System.out.println("Trying to load main-viewLoan.fxml...");

            FXMLLoader loader = springFXMLLoader.load("/Loans/main-viewLoan.fxml");
            Node mainViewContent = loader.load();
            mainContent.getChildren().add(mainViewContent);

            System.out.println("main-viewLoan.fxml loaded successfully!");
        } catch (IOException e) {
            System.err.println("Error loading main-viewLoan.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void loadLoansView() {
        clearAllPanels();
        try {
            FXMLLoader loader = springFXMLLoader.load("/Loans/Myloans/myloans.fxml");
            Node loansView = loader.load();

            MyLoans controller = loader.getController();
            controller.loadLoanDetails();

            mainContent.getChildren().setAll(loansView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logoutButton(ActionEvent event) {
        try {
            FXMLLoader loader = org.jala.university.config.config_account.SpringFXMLLoader.create("/Account/login.fxml");
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void loadProfileView(ActionEvent event) {
        try {
            // Carrega o FXML da página de perfil
            FXMLLoader loader = org.jala.university.config.config_account.SpringFXMLLoader.create("/Account/profile-page.fxml");
            Parent root = loader.load();

            // Recupera a Stage atual
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

            // Atualiza a cena com a página de perfil
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCustomerInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Usuário não está autenticado.");
                return;
            }

            String cpf = authentication.getName();
            if (cpf == null || cpf.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erro", "CPF do usuário não encontrado.");
                return;
            }

            CustomerDto user = customerService.getCustomerByCpf(cpf);
            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Cliente não encontrado para o CPF: " + cpf);
                return;
            }

            nameLabel.setText(user.getFullName() != null ? user.getFullName() : "");
            nameLabel2.setText(user.getFullName() != null ? user.getFullName() : "");
            balanceLabel.setText(String.format("R$ %.2f", accountService.getBalanceByCustomerId(user.getId())));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar dados do cliente: " + e.getMessage());
        }

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}