package org.jala.university.presentation;

import lombok.Getter;
import org.jala.university.commons.presentation.View;

@Getter
public enum AccountView {
    MAIN("main-view.fxml"),
    CREATE("Account/register-user.fxml"),
    UPDATE("Account/update-account.fxml"),
    CLOSE("close-account.fxml"),
    LIST("Account/list-account.fxml");

    private final View view;

    AccountView(String fileName) {
        this.view = new View(fileName);
    }

}
