package org.jala.university.domain.entity.externalEntity;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

//    @Column(name = "gender", nullable = false)
//    private String gender;
//
//    @Column(name = "birthdayr", nullable = false)
//    private LocalDate birthday;
//
//    private String profilePicture;


}
