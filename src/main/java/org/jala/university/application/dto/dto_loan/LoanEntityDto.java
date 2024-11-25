package org.jala.university.application.dto.dto_loan;

import java.time.LocalDate;
import java.util.List;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_loan.FormEntity;
import org.jala.university.domain.entity.entity_loan.InstallmentEntity;
import org.jala.university.domain.entity.entity_loan.enums.PaymentMethod;
import org.jala.university.domain.entity.entity_loan.enums.Status;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class LoanEntityDto {

    Integer id;
    Double amountBorrowed;
    Double totalInterest;
    Integer numberOfInstallments;
    Double valueOfInstallments;
    Double totalPayable;
    PaymentMethod paymentMethod;
    Status status;
    LocalDate issueDate;
    LocalDate loanDueDate;
    FormEntity form;
    List<InstallmentEntity> installments;
    Account account;
}