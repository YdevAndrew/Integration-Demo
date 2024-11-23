package org.jala.university.application.service.service_transaction;


import org.jala.university.application.dto.dto_account.AccountDto;
import org.jala.university.application.dto.dto_transaction.PaymentHistoryDTO;

import java.util.List;

public interface PaymentHistoryService {
    PaymentHistoryDTO createPaymentHistory(Integer userId, PaymentHistoryDTO paymentHistoryDto, String transactionType);

    List<AccountDto> getContactList(Integer userId);

    List<PaymentHistoryDTO> getPaymentHistory(Integer userId);

    List<PaymentHistoryDTO> getPaymentHistoryFiltesSenderOrReceiver(Integer userId, Boolean isSender);

    List<PaymentHistoryDTO> getPaymentHistoryFiltesCompletedOrScheduled(Integer userId, Boolean isCompleted);
}