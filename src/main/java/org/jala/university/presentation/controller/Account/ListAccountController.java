package org.jala.university.presentation.controller.Account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.EqualsAndHashCode;
import org.jala.university.ServiceFactory;
import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.application.service.service_account.AccountService;
import org.jala.university.commons.presentation.BaseController;
import org.jala.university.commons.presentation.ViewContext;
import org.jala.university.commons.presentation.ViewSwitcher;
import org.jala.university.presentation.AccountView;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
@EqualsAndHashCode(callSuper = true)
public class ListAccountController extends BaseController implements Initializable {

    private final AccountService accountService;
    ObservableList<AccountDto> accounts = FXCollections.observableArrayList();

    @FXML
    private TableColumn<AccountDto, String> accountNumber;
    @FXML
    private TableColumn<AccountDto, Double> balance;
    @FXML
    private TableColumn<AccountDto, String> currency;
    @FXML
    private TableColumn<AccountDto, String> name;
    @FXML
    private TableColumn<AccountDto, String> status;
    @FXML
    private TableView<AccountDto> accountsTable;

    public ListAccountController() {
        this.accountService = ServiceFactory.accountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountNumber.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadAccounts();
    }

    @FXML
    void onReturn() {
        ViewSwitcher.switchTo(AccountView.MAIN.getView());
    }

    private void loadAccounts() {
        accounts.clear();
        List<AccountDto> allAccounts = accountService.getAllAccounts();
        accounts.addAll(allAccounts);
        accountsTable.setItems(accounts);
    }

    @FXML
    public void onUpdate() {
        AccountDto account = accountsTable.getSelectionModel().getSelectedItem();
        if (account == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Actualizar Cuenta");
            alert.setHeaderText("Seleccionar una cuenta para actualizar");
            alert.show();
            return;
        }
        UpdateViewContext updateViewContext = UpdateViewContext.builder()
                .accountToUpdate(account)
                .build();
        ViewSwitcher.switchTo(AccountView.UPDATE.getView(), (ViewContext) updateViewContext);
    }

    @FXML
    public void onDelete() {
        AccountDto account = accountsTable.getSelectionModel().getSelectedItem();
        if (account == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eliminar Cuenta");
            alert.setHeaderText("Seleccionar una cuenta para eliminar");
            alert.show();
            return;
        }
        accountService.removeAccount(account);
        loadAccounts();
    }
}
