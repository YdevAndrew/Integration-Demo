package org.jala.university.application.dto.dto_account;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Integer id;
    private String fullName;
    private String cpf;
    private String email;
    private String gender;
    private String phoneNumber;
    private LocalDate birthday;
    private String country;
    private String district;
    private String state;
    private String postalCode;
    private String street;
    private byte[] password;
    private String verificationCode;

}
