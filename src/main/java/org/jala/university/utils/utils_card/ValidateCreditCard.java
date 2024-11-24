package org.jala.university.utils.utils_card;


import org.jala.university.application.service.service_card.CreditCardService;
import org.jala.university.domain.entity.entity_card.CreditCard;
import org.jala.university.infrastructure.PostgreConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 * class responsible to validate if the credit card exist and if the informations are correct
 */
public class ValidateCreditCard {

    /***
     * only to make a test
     */
    public static void main(String[] args) throws SQLException {


        System.out.println(validateCreditCardInfo("3878687640558653","635", "11/2029", "Maria Oliveira - Virtual"));




       // System.out.println(validateCreditCardInfo("7203508788541771", "543", "15/11/2029", "Rodrigo Santos"));
      //  List<CreditCard> creditCustomer = validateFilds(purchaseRequest.getCreditCard().getNumber_card());
        //List<CreditCard> creditSeller = validateFilds(purchaseRequest.getSellerCreditCard().getNumber_card());


    }


    /***
     * this functions validate if the list have itens or is null
     *  validCards is the values how are validated by another function (validateFilds)
     * @return if it's correct or no
     */
    public static boolean validateCreditCardInfo(String numberCard, String cvv, String expirationDate, String nameCard) throws SQLException {
        CreditCard creditCard = CreditCardService.getCreditCardByNumberCard(numberCard);

        if (creditCard == null) {
            return false;
        }

        // Validação dos dados do cartão
        boolean isValid = Objects.equals(creditCard.getNumberCard(), numberCard) &&
                Objects.equals(creditCard.getCvv(), cvv) &&
                creditCard.getExpiration_date().equals(expirationDate) &&
                Objects.equals(creditCard.getName_card(), nameCard);

        return isValid;
    }

    /***
     * this function take the parameters, find in the database, if correct put in the list, if not correct is null
     *  is the credit card data from customer
     * @return return a list with the results
     */

    public static List<CreditCard> validateFilds(String numberCard) throws SQLException {
        List<CreditCard> validCards = new ArrayList<>();

        CreditCard creditCard = CreditCardService.getCreditCardByNumberCard(numberCard);

        if (creditCard == null) {
            throw new SQLException("Card not found for the provided card number.");
        }

        List<CreditCard> allCards = new ArrayList<>();
        allCards.add(creditCard);

        try {
            PostgreConnection.getInstance("jala_bank");

            for (CreditCard dbCard : allCards) {
                if (Objects.equals(creditCard.getNumberCard(), dbCard.getNumberCard()) &&
                        Objects.equals(creditCard.getCvv(), dbCard.getCvv()) &&
                        creditCard.getExpiration_date().equals(dbCard.getExpiration_date()) &&
                        Objects.equals(creditCard.getName_card(), dbCard.getName_card())) {
                    validCards.add(dbCard);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Database error while validating credit card", e);
        }

        return validCards;
    }


}
