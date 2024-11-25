package org.jala.university.domain.entity.entity_card;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_product;

    @Column(name = "name_product", nullable = false)
    private String nameProduct;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "productPrice", nullable = false)
    private BigDecimal productPrice;

    @Column(name = "barcode", nullable = false)
    private String barcode;


}
