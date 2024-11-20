package org.jala.university.presentation;

import lombok.Getter;
import org.jala.university.commons.presentation.View;

/***
 * Enum with both pages will be loaded first
 */
@Getter
public enum CreditCardView {
    HOMEPAGE("board/DashboardApp.fxml");


    private final View view;

    CreditCardView(String fileName) {
        this.view = new View(fileName);
    }

}
