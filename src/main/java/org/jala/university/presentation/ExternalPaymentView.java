package org.jala.university.presentation;

import lombok.Getter;
import org.jala.university.commons.presentation.View;

/***
 * Enum with both pages will be loaded first
 */
@Getter
public enum ExternalPaymentView {
    DASHBOARD("board/DashboardApp.fxml");


    private final View view;

    ExternalPaymentView(String fileName) {
        this.view = new View(fileName);
    }

}
