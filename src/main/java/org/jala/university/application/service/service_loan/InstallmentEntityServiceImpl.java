package org.jala.university.application.service.service_loan;

import java.util.List;

import org.jala.university.application.dto.dto_loan.InstallmentEntityDto;
import org.jala.university.application.mapper.mapper_loan.InstallmentEntityMapper;
import org.jala.university.domain.entity.entity_loan.InstallmentEntity;
import org.jala.university.domain.repository.repository_loan.InstallmentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class implements the {@link InstallmentEntityService} interface,
 * providing a concrete implementation for retrieving installments
 * associated with a loan.
 */
public class InstallmentEntityServiceImpl {

    /**
     * The repository for accessing installment entities.
     */
    @Autowired
    private InstallmentEntityRepository installmentEntityRepository;

    /**
     * The mapper for converting between installment entities
     * and DTOs.
     */
    private final InstallmentEntityMapper mapper;


    /**
     * Constructor for InstallmentEntityServiceImpl.
     *
     * @param mapper The mapper instance to be used.
     */
    public InstallmentEntityServiceImpl(InstallmentEntityMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    public List<InstallmentEntityDto> findByLoanId(Integer loanId) {
        List<InstallmentEntity> installments = installmentEntityRepository.findByLoanId(loanId);
        return installments.stream()
                .map(mapper::mapTo)
                .toList();
    }
}
