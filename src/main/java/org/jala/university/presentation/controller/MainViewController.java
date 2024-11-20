package org.jala.university.presentation.controller;

import org.jala.university.commons.presentation.BaseController;
import org.jala.university.commons.presentation.ViewSwitcher;
import org.jala.university.presentation.AccountView;
import org.springframework.stereotype.Controller;

@Controller
public class MainViewController extends BaseController {

    public void onCreateAccount() {
        ViewSwitcher.switchTo(AccountView.CREATE.getView());
    }

    public void onViewAccounts() {
        ViewSwitcher.switchTo(AccountView.LIST.getView());
    }
}
