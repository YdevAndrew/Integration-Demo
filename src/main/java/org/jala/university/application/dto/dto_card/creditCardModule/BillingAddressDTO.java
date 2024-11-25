package org.jala.university.application.dto.dto_card.creditCardModule;


import lombok.Getter;
import lombok.Setter;

/***
 * Class representing the bank customer address
 */
@Getter
@Setter
public class BillingAddressDTO {
    private int id_billing_addess;
    private String cep, street, city, state, country;
    private int fk_account_id;

    public BillingAddressDTO(String cep, String street, String city, String state, String country) {
        this.cep = cep;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public BillingAddressDTO() {}
}
