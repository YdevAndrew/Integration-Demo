package org.jala.university.commons.presentation;

import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardApp extends Application {

    private BorderPane layout;

    public DashboardApp() {
        layout = new BorderPane();
        layout.setTop(createHeader());
        layout.setLeft(createSideMenu(layout));
        layout.setBottom(createFooter());
        layout.setCenter(new BorderPane()); // Conteúdo central
    }

    public BorderPane getView() {
        return layout;
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout Principal (BorderPane)
        BorderPane root = new BorderPane();

        // Cabeçalho
        HBox header = createHeader();
        root.setTop(header);

        // Conteúdo Principal
        BorderPane mainContent = new BorderPane();
        root.setCenter(mainContent);

        // Menu Lateral com botões funcionais
        VBox sideMenu = createSideMenu(mainContent);
        root.setLeft(sideMenu);

        // Rodapé
        HBox footer = createFooter();
        root.setBottom(footer);

        // Definindo o ícone da aplicação
        primaryStage.getIcons().add(new Image(getClass().getResource("/dashboard-res/Logo.png").toExternalForm()));

        // Configuração da Scene
        Scene scene = new Scene(root, 1200, 900);
        scene.getStylesheets().add(getClass().getResource("/dashboard-res/style-dashboard.css").toExternalForm());

        // Configuração da Janela e Exibição
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard - Bank App");
        primaryStage.show();
    }

    private VBox createBalanceSection(double balance) {
        VBox balanceBox = new VBox();
        balanceBox.getStyleClass().add("balance-container");
        balanceBox.setSpacing(10);
        balanceBox.setAlignment(Pos.CENTER);

        // Título "Balance"
        Label balanceTitle = new Label("Balance");
        balanceTitle.getStyleClass().add("balance-title");

        // Data atual
        Label dateLabel = new Label(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM, dd")));
        dateLabel.getStyleClass().add("balance-date");

        // Caixa de valor do saldo
        HBox balanceValueBox = new HBox();
        balanceValueBox.setAlignment(Pos.CENTER);
        balanceValueBox.setSpacing(10);
        balanceValueBox.getStyleClass().add("balance-button");

        // Texto do valor do saldo
        Label balanceLabel = new Label("R$ *****");
        balanceLabel.getStyleClass().add("balance-text");

        // Ícone de "mostrar/ocultar" saldo
        ImageView eyeIcon = new ImageView(new Image(getClass().getResource("/dashboard-res/eye.png").toExternalForm()));
        eyeIcon.setFitWidth(20);
        eyeIcon.setFitHeight(20);

        // Botão para alternar a visibilidade do saldo
        Button toggleButton = new Button();
        toggleButton.setGraphic(eyeIcon);
        toggleButton.getStyleClass().add("toggle-button");

        // Lógica para alternar a exibição do saldo
        toggleButton.setOnAction(event -> {
            if (balanceLabel.getText().equals("R$ *****")) {
                balanceLabel.setText(String.format("R$ %.2f", balance));
            } else {
                balanceLabel.setText("R$ *****");
            }
        });

        // Adiciona o valor e o botão ao HBox
        balanceValueBox.getChildren().addAll(balanceLabel, toggleButton);

        // Adiciona os elementos ao VBox
        balanceBox.getChildren().addAll(balanceTitle, dateLabel, balanceValueBox);

        return balanceBox;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setSpacing(15); // Espaçamento entre os elementos
        header.setStyle("-fx-background-color: linear-gradient(to right, #1e90ff, #87CEFA);");

        // Imagem do usuário (foto)
        ImageView userPhoto = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("/dashboard-res/john_doe.png")).toExternalForm()));
        userPhoto.setFitWidth(50);
        userPhoto.setFitHeight(50);

        // Nome do usuário
        Label userName = new Label("John Doe");
        userName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        userName.setStyle("-fx-text-fill: white;");

        // Ícone de seta para o dropdown
        ImageView arrowIcon = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("/dashboard-res/seta_baixo.png")).toExternalForm()));
        arrowIcon.setFitWidth(10);
        arrowIcon.setFitHeight(10);

        // Botão que contém a seta
        Button dropdownButton = new Button();
        dropdownButton.setGraphic(arrowIcon);
        dropdownButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        // Menu suspenso (ContextMenu) com CSS
        ContextMenu dropdownMenu = new ContextMenu();
        dropdownMenu.getStyleClass().add("context-menu");

        MenuItem profileItem = new MenuItem("Profile");
        profileItem.getStyleClass().add("menu-item");

        MenuItem settingsItem = new MenuItem("Settings");
        settingsItem.getStyleClass().add("menu-item");

        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.getStyleClass().add("menu-item");
        logoutItem.setOnAction(event -> System.out.println("Logout selected"));

        dropdownMenu.getItems().addAll(profileItem, settingsItem, logoutItem);

        // Alterna a exibição do menu ao clicar na seta
        dropdownButton.setOnAction(event -> {
            if (dropdownMenu.isShowing()) {
                dropdownMenu.hide();  // Esconde o menu se já estiver visível
            } else {
                dropdownMenu.show(dropdownButton, Side.BOTTOM, 0, 0);  // Exibe o menu
            }
        });

        // Agrupa a foto, nome e seta em um HBox
        HBox userInfo = new HBox(userPhoto, userName, dropdownButton);
        userInfo.setSpacing(8); // Espaço entre a foto e o nome
        userInfo.setAlignment(Pos.CENTER_LEFT);

        // Saudação centralizada
        Label welcomeMessage = new Label("Hello John Doe, welcome!");
        welcomeMessage.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeMessage.setStyle("-fx-text-fill: white;");

        // Espaçadores para organizar o layout
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // Logo do aplicativo
        ImageView appLogo = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("/dashboard-res/Logo.png")).toExternalForm()));
        appLogo.setFitWidth(80);
        appLogo.setFitHeight(50);

        // Adiciona todos os elementos ao cabeçalho
        header.getChildren().addAll(userInfo, spacer1, welcomeMessage, spacer2, appLogo);
        header.setAlignment(Pos.CENTER); // Alinha tudo no centro verticalmente

        return header;
    }

    private VBox createSideMenu(BorderPane mainContent) {
        VBox sideMenu = new VBox();
        sideMenu.getChildren().add(createBalanceSection(5234.50));
        sideMenu.setPadding(new Insets(20));
        sideMenu.setSpacing(15);
        sideMenu.setStyle("-fx-background-color: #f8f8f8;");
        sideMenu.setAlignment(Pos.TOP_LEFT);

        List<Button> buttons = new ArrayList<>();

        Button dashboardButton = createMenuButton("Dashboard", "/dashboard-res/dashboard.png", mainContent, buttons,
                () -> showContent(mainContent, "Dashboard Content"));

        Button transactionsButton = createMenuButton("Transactions", "/dashboard-res/Transactions.png", mainContent, buttons,
                () -> showContent(mainContent, "Transactions Content"));

        Button myCardsButton = createMenuButton("My Cards", "/dashboard-res/my_cards.png", mainContent, buttons,
                () -> mainContent.setCenter(createMyCardsContent()));

        Button loansButton = createMenuButton("Loans", "/dashboard-res/loans.png", mainContent, buttons,
                () -> showContent(mainContent, "Loans Content"));

        Button paymentsButton = createMenuButton("Make Payments", "/dashboard-res/make_payments.png", mainContent, buttons,
                () -> showContent(mainContent, "Make Payments Content"));

        Button servicesButton = createMenuButton("Scheduled Services", "/dashboard-res/scheduled_services.png", mainContent, buttons,
                () -> showContent(mainContent, "Scheduled Services Content"));

        Button settingsButton = createMenuButton("Settings", "/dashboard-res/settings.png", mainContent, buttons,
                () -> showContent(mainContent, "Settings Content"));

        buttons.addAll(Arrays.asList(dashboardButton, transactionsButton, myCardsButton,
                loansButton, paymentsButton, servicesButton, settingsButton));

        sideMenu.getChildren().addAll(buttons);

        return sideMenu;
    }


    private VBox createMyCardsContent() {
        // VBox principal para alinhar verticalmente
        VBox myCardsContent = new VBox(40);
        myCardsContent.setPadding(new Insets(30));
        myCardsContent.setAlignment(Pos.CENTER);

        // Label com a mensagem estilizada
        Label message = new Label("WITH JALA U CREDIT\nCARDS, YOU WILL\nGO FAR");
        message.setFont(Font.font("GraphikBold", FontWeight.BOLD, 32));
        message.setTextAlignment(TextAlignment.LEFT);
        message.setStyle("-fx-text-fill: #003366;");

        // Imagem dos cartões
        ImageView cardsImage = new ImageView(new Image(getClass().getResource("/dashboard-res/my_cards/example_cards.png").toExternalForm()));
        cardsImage.setFitWidth(300);
        cardsImage.setPreserveRatio(true);

        // HBox para posicionar a mensagem e a imagem dos cartões lado a lado
        HBox contentLayout = new HBox(30);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.getChildren().addAll(message, cardsImage);

        // Botão estilizado "GET A CARD"
        Button getCardButton = new Button("GET A CARD");
        getCardButton.setStyle("-fx-background-color: linear-gradient(to bottom, #1e90ff, #4169e1);" +
                "-fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10 40; -fx-background-radius: 30;");
        getCardButton.setOnAction(e -> System.out.println("Get a Card button clicked"));

        // Adicionando o layout e o botão ao VBox principal
        myCardsContent.getChildren().addAll(contentLayout, getCardButton);

        return myCardsContent;
    }

    private void setActiveButton(Button activeButton, List<Button> buttons) {
        // Remove o estilo de ativo de todos os botões
        for (Button button : buttons) {
            button.getStyleClass().remove("menu-button-active");
            button.getStyleClass().add("menu-button");
        }

        // Adiciona o estilo de ativo ao botão clicado
        activeButton.getStyleClass().add("menu-button-active");
    }

    private void showContent(BorderPane mainContent, String content) {
        Label contentLabel = new Label(content);
        contentLabel.setFont(new Font(18));
        mainContent.setCenter(contentLabel);
    }

    private Button createMenuButton(String text, String iconPath, BorderPane mainContent, List<Button> buttons, Runnable action) {
        Button button = new Button(text);
        button.setGraphic(new ImageView(new Image(getClass().getResource(iconPath).toExternalForm())));
        button.getStyleClass().add("menu-button");

        // Adiciona evento ao botão
        button.setOnAction(event -> {
            // Remove a classe "menu-button-active" de todos os botões
            buttons.forEach(btn -> {
                btn.getStyleClass().remove("menu-button-active");
                if (!btn.getStyleClass().contains("menu-button")) {
                    btn.getStyleClass().add("menu-button");
                }
            });

            // Define o botão clicado como ativo
            button.getStyleClass().remove("menu-button");
            button.getStyleClass().add("menu-button-active");

            // Executa a ação associada ao botão
            action.run();
        });

        return button;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(15));
        footer.setSpacing(10);
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-background-color: linear-gradient(to right, #87cefa, #1e90ff);");

        Button logoutButton = new Button();
        logoutButton.setGraphic(new ImageView(new Image(getClass().getResource("/dashboard-res/Exit.png").toExternalForm())));
        logoutButton.setStyle("-fx-background-color: transparent;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label contactUs = new Label("Contact Us");
        contactUs.setTextFill(Color.WHITE);

        footer.getChildren().addAll(logoutButton, spacer, contactUs);
        return footer;
    }
}
