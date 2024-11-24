package org.jala.university.application.service.service_account;

import org.jala.university.application.dto.dto_account.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto account, Integer customerId);
    AccountDto getAccount(Integer id);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Integer id);

    void removeAccount(AccountDto account);

    void updateAccount(AccountDto accountDto);

    BigDecimal getBalanceByCustomerId(Integer customerId);

}
