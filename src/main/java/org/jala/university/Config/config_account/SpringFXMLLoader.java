package org.jala.university.config.config_account;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

public class SpringFXMLLoader {
    public static ApplicationContext applicationContext;



    // Método para injetar o contexto existente do Spring
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static FXMLLoader create(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(SpringFXMLLoader.class.getResource(fxmlPath));
        loader.setControllerFactory(applicationContext::getBean);
        return loader;
    }
}
