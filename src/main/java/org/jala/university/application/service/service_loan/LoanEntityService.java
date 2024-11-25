package org.jala.university.application.service.service_loan;

import java.time.LocalDate;
import java.util.List;

import org.jala.university.application.dto.dto_loan.InstallmentEntityDto;
import org.jala.university.application.dto.dto_loan.LoanEntityDto;
import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.domain.entity.entity_loan.LoanEntity;

public interface LoanEntityService {

    LoanEntityDto findById(Integer id);
    List<LoanEntityDto> findAll();
    LoanEntity findEntityById(Integer id);
    LoanEntityDto save(LoanEntityDto entityDto);
    void deleteById(Integer id);
    void delete(LoanEntityDto entityDto);
    LoanEntityDto update(LoanEntityDto entityDto);
    List<LoanEntityDto> findLoansByAccountId();
    Account payInstallmentManually(LoanEntityDto dto);
    long getPaidInstallments(LoanEntityDto dto);
    LocalDate getFirstUnpaidInstallmentDate(LoanEntityDto dto);
    Double getOutstandingBalance(Integer loanId);
    InstallmentEntityDto getFirstUnpaidInstallment(LoanEntityDto loan);
}
