package org.jala.university.infrastructure.persistance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_account.AccountStatus;
import org.jala.university.domain.entity.entity_account.Currency;

public class AccountGenerator {
    private static final Random RANDOM = new Random();
    private static final String[] ACCOUNT_NUMBERS = {
            "123456789", "987654321", "555555555", "111111111", "222222222"
    };

    public static Map<? extends Integer, ? extends Account> generateRandomAccounts(int count) {
        Map<Integer, Account> accounts = new HashMap<>();
        for (int i = 0; i < count; i++) {
            Integer id = i + 1; // Gera IDs de 1 a count
            Account account = new Account();
            account.setId(id);
            account.setAccountNumber(ACCOUNT_NUMBERS[RANDOM.nextInt(ACCOUNT_NUMBERS.length)]);
            account.setBalance(BigDecimal.valueOf(RANDOM.nextDouble() * 10000)); // Saldo aleatório
            account.setStatus(AccountStatus.values()[RANDOM.nextInt(AccountStatus.values().length)]); // Status aleatório
            account.setCurrency(Currency.values()[RANDOM.nextInt(Currency.values().length)]); // Moeda aleatória
            accounts.put(id, account);
        }
        return accounts;
    }
}
