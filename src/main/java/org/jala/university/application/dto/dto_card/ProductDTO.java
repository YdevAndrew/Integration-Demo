package org.jala.university.application.dto.dto_card;

import lombok.Getter;
import lombok.Setter;
import org.jala.university.domain.entity.entity_card.CreditCard;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private String nameProduct;
    private int quantity;
    private String description;
    private CreditCard sellerCreditCard;
    private BigDecimal productPrice;
    private String barcode;

    public ProductDTO(String nameProduct, int quantity, String description, CreditCard sellerCreditCard, BigDecimal productPrice, String barcode) {
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.description = description;
        this.sellerCreditCard = sellerCreditCard;
        this.productPrice = productPrice;
        this.barcode = barcode;
    }

    public ProductDTO(ProductDTO productDTO) {
        this.nameProduct = productDTO.nameProduct;
        this.quantity = productDTO.quantity;
        this.description = productDTO.description;
        this.sellerCreditCard = productDTO.sellerCreditCard;
        this.productPrice = productDTO.productPrice;
        this.barcode = productDTO.barcode;
    }
    public ProductDTO() {

    }
}
