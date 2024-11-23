package org.jala.university.domain.entity.entity_account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Authentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

//    public String getEmail() {
//
//        return customer.getEmail();
//    }

    public void setEmail(String email) {

        customer.setEmail(email);
    }

    public String getPassword() {


        return "";
    }

    public void setPassword(String password) {

    }

    public void setCpf(String cpf) {

    }

    public Object getCpf() {

        return null;
    }
}

