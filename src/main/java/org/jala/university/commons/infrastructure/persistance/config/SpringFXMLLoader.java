package org.jala.university.commons.infrastructure.persistance.config;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.net.URL;

public class SpringFXMLLoader {
    private static ApplicationContext applicationContext;



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

