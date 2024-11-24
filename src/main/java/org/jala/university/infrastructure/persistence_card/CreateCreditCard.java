package org.jala.university.infrastructure.persistence_card;


import org.jala.university.application.service.service_card.CreditCardClientService;
import org.jala.university.application.service.service_card.LimitCreditCardService;
import org.jala.university.application.service.service_card.LoggedInUser;
import org.jala.university.domain.entity.entity_card.CreditCard;
import org.jala.university.domain.entity.entity_card.CreditCardClient;
import org.jala.university.domain.entity.entity_card.LimitCreditCard;
import org.jala.university.presentation.controller.Card.CardsPageController;

import java.math.BigDecimal;
import java.sql.SQLException;

public class CreateCreditCard {

    public static LimitCreditCard createLimit(String txtIncome) throws SQLException {
        LimitCreditCardService limitService = new LimitCreditCardService();

        BigDecimal limitCard;
        AddLimitCreditCard.validationValue(txtIncome);
        limitCard = BigDecimal.valueOf(AddLimitCreditCard.getLimit());

        LimitCreditCard limitCreditCard = new LimitCreditCard(limitCard, Integer.parseInt(LoggedInUser.getLogInUser()));

        limitService.saveLimitCreditCard(limitCreditCard);

        return limitCreditCard;
    }

    public synchronized static CreditCard createCreditCard(LimitCreditCard limitCreditCard, String type) throws SQLException {
        String numberCreditCard, cvv, expirationDate, nameCreditCard;

        CreateCreditNumber.generateCreditNumber();
        CreateCVV.generateCvv();
        CreateExpirateDate.createExpirateDate();
        AddNameCreditCard.addName();

        numberCreditCard = CreateCreditNumber.getNumerCreditCard();
        cvv = CreateCVV.getCvv();
        expirationDate = CreateExpirateDate.calculateExpirationDate();
        nameCreditCard = AddNameCreditCard.getFirstTwoNames();

        return new CreditCard(numberCreditCard, cvv, expirationDate, false, nameCreditCard, limitCreditCard, type);
    }

    public static void createCreditCardClientWithVirtual(String txtIncome) throws SQLException {
        LimitCreditCard sharedLimit = createLimit(txtIncome);
        CreditCard physicalCard = createCreditCard(sharedLimit, "Physical");
        CreditCard virtualCard = createCreditCard(sharedLimit, "Virtual");
        virtualCard.setName_card(physicalCard.getName_card() + " - Virtual");
        CreditCardClientService creditCardClientService = new CreditCardClientService();

        CreditCardClient creditCardClientPhysical = new CreditCardClient();
        creditCardClientPhysical.setCredit_card(physicalCard);
        creditCardClientPhysical.setFk_account_id(Integer.parseInt(LoggedInUser.getLogInUser()));
        creditCardClientService.saveCreditCardClient(creditCardClientPhysical);

        CreditCardClient creditCardClientVirtual = new CreditCardClient();
        creditCardClientVirtual.setCredit_card(virtualCard);
        creditCardClientVirtual.setFk_account_id(Integer.parseInt(LoggedInUser.getLogInUser()));
        creditCardClientService.saveCreditCardClient(creditCardClientVirtual);
        ConfigurationsCard.addDatePaymentDefault(creditCardClientPhysical.getCredit_card().getNumberCard(), "27");
        ConfigurationsCard.addDatePaymentDefault(creditCardClientVirtual.getCredit_card().getNumberCard(), "27");


    }

    //Mudar depois
    public static void createOnlyVirtualCard() throws SQLException {
        LimitCreditCard sharedLimit = LimitCreditCardService.findLimitCreditByCardNumber(CardsPageController.returnPhysicalCard());
        CreditCard virtualCard = createCreditCard(sharedLimit, "Virtual");

        virtualCard.setName_card(AddNameCreditCard.getFirstTwoNames() + " - Virtual");
        CreditCardClientService creditCardClientService = new CreditCardClientService();
        CreditCardClient creditCardClientVirtual = new CreditCardClient();
        creditCardClientVirtual.setCredit_card(virtualCard);
        creditCardClientVirtual.setFk_account_id(Integer.parseInt(LoggedInUser.getLogInUser()));

        creditCardClientService.saveCreditCardClient(creditCardClientVirtual);
        ConfigurationsCard.addDatePaymentDefault(creditCardClientVirtual.getCredit_card().getNumberCard(), "27");
    }

    public static void main(String[] args) throws SQLException {
        createCreditCardClientWithVirtual("1000");
    }


}
