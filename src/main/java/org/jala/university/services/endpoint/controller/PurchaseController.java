package org.jala.university.services.endpoint.controller;

import org.jala.university.requestCard.ActiveCreditCard;
import org.jala.university.services.endpoint.dto.ProductDTO;
import org.jala.university.services.endpoint.modal.CreditCard;
import org.jala.university.services.endpoint.modal.PurchaseRequest;
import org.jala.university.services.endpoint.services.CreditCardService;
import org.jala.university.services.endpoint.services.InvoicesService;
import org.jala.university.services.endpoint.validation.PurchaseService;
import org.jala.university.services.endpoint.validation.ValidateCreditCard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;



/***
 * endpoint to the store use, with this is possible to make a purchase and send the billing for the customer
 */

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    /***
     * to make the registrer for the customer credit card in the store
     * @param creditCard is the data from credit card customer
     * @return the request
     */
    @PostMapping("/credit-card-customer")
    public ResponseEntity<String> registerCredit(@RequestBody CreditCard creditCard) throws SQLException {
        if (ValidateCreditCard.validateCreditCardInfo(creditCard.getNumberCard(), creditCard.getCvv(), creditCard.getExpiration_date(), creditCard.getName_card())) {
            return ResponseEntity.ok("Credit card added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credit card data");
        }
    }


    /***
     * to make the register for the product in the store
     * @param productDTO is the product to put int the store
     * @return the request
     */
    @PostMapping("/product")
    public ResponseEntity<String> registerProduct(@RequestBody List<ProductDTO> productDTO) {
        if (!productDTO.isEmpty()) {
            return ResponseEntity.ok("Product add with success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product don't exist");
        }
    }

    /***
     * be called when the customer buys a product in the store, missing the functions to buy
     * @param purchaseRequest is the data for make a purchase, update the data in the credit card for the saller and customers
     * @return the request
     */
    @PostMapping("/purchase")
    public ResponseEntity<String> registerPurchase(@RequestBody PurchaseRequest purchaseRequest ) throws Exception {


        boolean isValid = ValidateCreditCard.validateCreditCardInfo(purchaseRequest.getCreditCard().getNumberCard(), purchaseRequest.getCreditCard().getCvv(), purchaseRequest.getCreditCard().getExpiration_date(), purchaseRequest.getCreditCard().getName_card());
        System.out.println("Validation result: " + isValid);
        if (!isValid) {
            System.out.println("Invalid credit card data");
            throw new Exception("Invalid credit data");
        }

        if (ValidateCreditCard.validateCreditCardInfo(purchaseRequest.getCreditCard().getNumberCard(), purchaseRequest.getCreditCard().getCvv(), purchaseRequest.getCreditCard().getExpiration_date(), purchaseRequest.getCreditCard().getName_card()) &&
                ValidateCreditCard.validateCreditCardInfo(purchaseRequest.getSellerCreditCard().getNumberCard(), purchaseRequest.getSellerCreditCard().getCvv(), purchaseRequest.getSellerCreditCard().getExpiration_date(), purchaseRequest.getSellerCreditCard().getName_card())) {
            if(ActiveCreditCard.checkStatus(CreditCardService.getCreditCardByNumberCard(purchaseRequest.getCreditCard().getNumberCard())).isActive())
            {
            if (!InvoicesService.checkOutstandingInvoices(purchaseRequest.getCreditCard().getNumberCard())) {
                if (PurchaseService.makePurchase(purchaseRequest, purchaseRequest.getSellerCreditCard().getNumberCard())) {
                    if (InvoicesService.canPurchase(purchaseRequest.getCreditCard().getNumberCard(), purchaseRequest.getProduct().getProductPrice())) {
                        if(InvoicesService.isLimitAvailable(purchaseRequest.getProduct().getProductPrice(), purchaseRequest.getCreditCard().getNumberCard())){
                            InvoicesService.createInstallments(purchaseRequest.getProduct().getProductPrice(), purchaseRequest.getNumberOfInstallments(), purchaseRequest.getCreditCard().getNumberCard(), purchaseRequest.getSellerCreditCard().getNumberCard(), purchaseRequest.getProduct(), purchaseRequest);
                            System.out.println( purchaseRequest.getNumberOfInstallments());
                            return ResponseEntity.ok("Purchase added successfully");
                        }
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Purchase denied: Insufficient limit");
                    }
                    }else{
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Purchase denied: Insufficient limit or unresolved issues.");
                    }
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid limit");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credit card is not active");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credit card data");
        }
    }



    /***
     * to test with software insomnia
     */
    @GetMapping("/")
    public String home() {
        return "Hello, Spring Boot!";
    }
}
