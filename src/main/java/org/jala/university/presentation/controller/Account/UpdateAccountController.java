package org.jala.university.presentation.controller.Account;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.EqualsAndHashCode;
import org.jala.university.ServiceFactory;
import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.commons.presentation.ViewSwitcher;
import org.jala.university.presentation.AccountView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@EqualsAndHashCode(callSuper = true)
public class UpdateAccountController extends BaseController implements Initializable {

    @FXML
    public ChoiceBox<String> currencySelector;
    @FXML
    public TextField accountNumber;
    @FXML
    public TextField name;
    @FXML
    public TextField balance;

    AccountDto accountDto;

    AccountService accountService;

    public UpdateAccountController() {
        this.accountService = ServiceFactory.accountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            UpdateViewContext context = (UpdateViewContext) this.context;
            if (context == null ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error al mostrar la pantalla de actualizacion");
                alert.show();
                return;
            }
            accountDto  =  context.getAccountToUpdate();
            accountNumber.setText(accountDto.getAccountNumber());
            balance.setText(accountDto.getBalance().toString());
            currencySelector.setValue(String.valueOf(accountDto.getCurrency().getClass()));
            //do stuff

        });

    }

    public void onUpdate() {
        accountDto.setAccountNumber(accountNumber.getText());
        accountService.updateAccount(accountDto);
        ViewSwitcher.switchTo(AccountView.LIST.getView());
    }

    public void onCancel() {
        ViewSwitcher.switchTo(AccountView.LIST.getView());
    }
}
