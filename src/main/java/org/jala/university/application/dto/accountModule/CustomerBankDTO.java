package org.jala.university.application.dto.accountModule;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/***
 * Representative class of the bank's customer
 */

@Getter
@Setter
public class CustomerBankDTO {
    private int id_customer;
    private String full_name;
    private String cpf;
    private String gender;
    private Date birthday;
    private String email;


    public CustomerBankDTO() {}

    public CustomerBankDTO(int id_customer, String full_name, String cpf, String gender, Date birthday) {
        this.id_customer = id_customer;
        this.full_name = full_name;
        this.cpf = cpf;
        this.gender = gender;
        this.birthday = birthday;
    }

    public CustomerBankDTO(int id_customer, String full_name, String cpf, String gender, Date birthday, String email) {
        this.id_customer = id_customer;
        this.full_name = full_name;
        this.cpf = cpf;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerBankDTO{" +
                "id_customer=" + id_customer +
                ", full_name='" + full_name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }
}
