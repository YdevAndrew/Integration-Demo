package org.jala.university.application.dto.dto_account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuthenticationDto {
    @Id
    private Integer id;
    private String email;
    private String cpf;
    private String password;

    public void setCpf(Object cpf) {

    }
}
