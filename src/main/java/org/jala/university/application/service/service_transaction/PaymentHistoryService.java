package org.jala.university.application.service;
import java.util.List;

import org.jala.university.application.dto.AccountDto;
import org.jala.university.application.dto.PaymentHistoryDTO;
import org.jala.university.domain.entity.Account;

public interface PaymentHistoryService {
    PaymentHistoryDTO createPaymentHistory(Integer userId, PaymentHistoryDTO paymentHistoryDto, String transactionType);

    List<AccountDto> getContactList(Integer userId);

    List<PaymentHistoryDTO> getPaymentHistory(Integer userId);

    List<PaymentHistoryDTO> getPaymentHistoryFiltesSenderOrReceiver(Integer userId, Boolean isSender);

    List<PaymentHistoryDTO> getPaymentHistoryFiltesCompletedOrScheduled(Integer userId, Boolean isCompleted);
}
