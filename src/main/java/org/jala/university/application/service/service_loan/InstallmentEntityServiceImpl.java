package org.jala.university.application.service.service_loan;

import java.util.List;

import org.jala.university.application.dto.dto_loan.InstallmentEntityDto;
import org.jala.university.application.mapper.mapper_loan.InstallmentEntityMapper;
import org.jala.university.domain.entity.entity_loan.InstallmentEntity;
import org.jala.university.domain.repository.InstallmentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InstallmentEntityServiceImpl {

    @Autowired
    private InstallmentEntityRepository installmentEntityRepository;
    private final InstallmentEntityMapper mapper;

    public InstallmentEntityServiceImpl(InstallmentEntityMapper mapper) {
        this.mapper = mapper;
    }

    public List<InstallmentEntityDto> findByLoanId(Integer loanId) {
        List<InstallmentEntity> installments = installmentEntityRepository.findByLoanId(loanId);
        return installments.stream()
                .map(mapper::mapTo)
                .toList();
    }
}
