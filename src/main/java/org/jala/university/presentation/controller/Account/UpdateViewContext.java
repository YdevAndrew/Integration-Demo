package org.jala.university.presentation.controller.Account;

import lombok.Getter;
import org.jala.university.application.dto.dto_account.AccountDto;

@Getter
public class UpdateViewContext {
    private final AccountDto accountToUpdate;

    private UpdateViewContext(AccountDto accountToUpdate) {
        this.accountToUpdate = accountToUpdate;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AccountDto accountToUpdate;

        public Builder accountToUpdate(AccountDto accountToUpdate) {
            this.accountToUpdate = accountToUpdate;
            return this;
        }

        public UpdateViewContext build() {
            return new UpdateViewContext(accountToUpdate);
        }
    }
}
