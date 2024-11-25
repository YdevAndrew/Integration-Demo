package org.jala.university.application.service.service_loan;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_loan.LoanEntity;

public interface LoanResultsService {
    Account sendAmountAccount(LoanEntity loanEntity);
    Account payInstallment(LoanEntity loanEntity);
}
