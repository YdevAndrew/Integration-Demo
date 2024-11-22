package org.jala.university.application.object.InformarrmationForm;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
/***
 *  Class responsible for enabling the creation of an object with the field that the user places on the personal form page
 */
public class PersonalInformation {


    private String cep, street, city, state, country, maritalStatus;


    public PersonalInformation(String cep, String street, String city, String state, String country, String maritalStatus) {
        this.cep = cep;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.maritalStatus = maritalStatus;
    }



    @Override
    public String toString() {
        return "InformationCustomer{" +
                "cep='" + cep + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}