package org.jala.university.application.service.service_loan;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_loan.LoanEntity;

/**
 * This interface defines the service contract for handling loan results
 * and related actions, such as sending loan amounts to accounts,
 * processing installment payments, and verifying scheduled tasks.
 */
public interface LoanResultsService {

    /**
     * Sends the loan amount to the associated account.
     *
     * @param loanEntity The loan entity for which to send the amount.
     * @return The updated account after the loan amount is sent.
     */
    Account sendAmountAccount(LoanEntity loanEntity);

    /**
     * Processes the payment of an installment for a loan.
     *
     * @param loanEntity The loan entity for which to pay the installment.
     * @return The updated account after the installment payment is processed.
     */
    Account payInstallment(LoanEntity loanEntity);
}
