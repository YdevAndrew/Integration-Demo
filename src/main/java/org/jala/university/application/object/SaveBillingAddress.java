package org.jala.university.application.object;


/***
 * class responsible for saving the data previously filled in the form
 */

public class SaveBillingAddress {
    private static String postal_code;
    private static String address;
    private static String neighborhood;
    private static String street;
    private static String country;

    public SaveBillingAddress(String postal_code, String address, String neighborhood, String street, String country) {
        this.postal_code = postal_code;
        this.address = address;
        this.neighborhood = neighborhood;
        this.street = street;
        this.country = country;
    }

    public SaveBillingAddress() {}

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
