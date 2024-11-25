package org.jala.university.domain.entity.entity_card;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "billing_address")
public class BillingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_billing_address;
    private String cep, street, city, state, country;
    private int fk_account_id;

    public BillingAddress(String cep, String street, String city, String state, String country, int fk_account_id) {
        this.cep = cep;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.fk_account_id = fk_account_id;
    }
    public BillingAddress() {}

    @Override
    public String toString() {
        return "BillingAddress{" +
                "id_billing_address=" + id_billing_address +
                ", cep='" + cep + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", fk_account_id=" + fk_account_id +
                '}';
    }
}
