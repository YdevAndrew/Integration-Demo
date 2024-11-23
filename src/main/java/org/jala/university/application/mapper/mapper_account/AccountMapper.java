package org.jala.university.application.mapper.mapper_account;

import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.domain.entity.entity_account.Account;

public class AccountMapper {
    public static AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setPaymentPassword(account.getPaymentPassword());
        return dto;
    }

    public static Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setCurrency(dto.getCurrency());
        account.setPaymentPassword(dto.getPaymentPassword());
        return account;
    }
}