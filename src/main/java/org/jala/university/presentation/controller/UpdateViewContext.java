package org.jala.university.presentation.controller;

import lombok.Getter;
import org.jala.university.application.dto.AccountDto;
import org.springframework.stereotype.Controller;

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
