package org.jala.university.commons.presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListAccountView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Criando os botões
        Button returnButton = new Button("Volver");
        returnButton.setLayoutX(293.0);
        returnButton.setLayoutY(356.0);
        returnButton.setPrefSize(80.0, 26.0);
        returnButton.setOnAction(event -> onReturn());

        Button updateButton = new Button("Actualizar");
        updateButton.setLayoutX(389.0);
        updateButton.setLayoutY(356.0);
        updateButton.setPrefSize(80.0, 26.0);
        updateButton.setOnAction(event -> onUpdate());

        Button deleteButton = new Button("Eliminar");
        deleteButton.setLayoutX(481.0);
        deleteButton.setLayoutY(356.0);
        deleteButton.setPrefSize(80.0, 26.0);
        deleteButton.setOnAction(event -> onDelete());

        // Criando a tabela
        TableView<?> accountsTable = new TableView<>();
        accountsTable.setLayoutX(30.0);
        accountsTable.setLayoutY(68.0);
        accountsTable.setPrefSize(523.0, 238.0);

        // Defina explicitamente os tipos das colunas
        TableColumn<Object, String> accountNumberColumn = new TableColumn<>("Numero de Cuenta");
        accountNumberColumn.setPrefWidth(129.0);

        TableColumn<Object, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setPrefWidth(120.0);

        TableColumn<Object, Double> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setPrefWidth(99.0);

        TableColumn<Object, String> statusColumn = new TableColumn<>("Estado");
        statusColumn.setPrefWidth(75.0);

        TableColumn<Object, String> currencyColumn = new TableColumn<>("Moneda");
        currencyColumn.setPrefWidth(99.0);


        // Criando o layout Pane e adicionando os componentes
        Pane pane = new Pane();
        pane.setPrefSize(475.0, 395.0);
        pane.getChildren().addAll(returnButton, updateButton, deleteButton, accountsTable);

        // Criando o layout VBox e adicionando o Pane
        VBox vbox = new VBox();
        vbox.setPrefSize(575.0, 400.0);
        vbox.getChildren().add(pane);

        // Configurando a cena e o palco
        Scene scene = new Scene(vbox);
        primaryStage.setTitle("ListAccountView");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Métodos de controle (simulados)
    private void onReturn() {
        System.out.println("Ação de Volver executada.");
    }

    private void onUpdate() {
        System.out.println("Ação de Actualizar executada.");
    }

    private void onDelete() {
        System.out.println("Ação de Eliminar executada.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
