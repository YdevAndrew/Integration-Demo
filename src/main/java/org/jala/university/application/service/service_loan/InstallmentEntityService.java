package org.jala.university.application.service.service_loan;

import java.util.List;

import org.jala.university.application.dto.dto_loan.InstallmentEntityDto;

public interface InstallmentEntityService {
    List<InstallmentEntityDto> findByLoanId(Integer id);
}
