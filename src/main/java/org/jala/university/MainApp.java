package org.jala.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.jala.university.config.config_account.SpringFXMLLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.URL;

@EnableScheduling
@SpringBootApplication(exclude = {
        SpringApplicationAdminJmxAutoConfiguration.class
})
@EntityScan("org.jala.university.domain.entity")
public class MainApp extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Apenas lança o JavaFX, que irá iniciar o Spring no método init()
        launch(args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(MainApp.class);
        SpringFXMLLoader.setApplicationContext(springContext);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            URL fxmlUrl = getClass().getResource("/Account/home-view.fxml");
            if (fxmlUrl == null) {
                throw new IllegalStateException("Não foi possível encontrar /home-view.fxml");
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            fxmlLoader.setControllerFactory(springContext::getBean);


            Parent root = fxmlLoader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Jala Bank");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Erro ao carregar FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void stop() throws Exception {
        springContext.close();
        super.stop();
    }

}