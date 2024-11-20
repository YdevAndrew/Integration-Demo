package org.jala.university.application.service;

import org.jala.university.application.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto account, Integer customerId);
    AccountDto getAccount(Integer id);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Integer id);

    void removeAccount(AccountDto account);

    void updateAccount(AccountDto accountDto);

}
