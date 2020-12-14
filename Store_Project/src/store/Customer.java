package store;

import java.io.Serializable;

public class Customer implements Serializable {
    private String name;
    private String email;
    private String phoneNumber;
    private String streetName;
    private String city;
    private String province;
    private String postalCode;
    private String creditCardNumber;
    private String creditCardExpiry;
    private String password;

    //Customer constructor
    public Customer(String name, String email, String phoneNumber, String streetName, String city, String province, String postalCode, String creditCardNumber, String creditCardExpiry, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
        this.password = password;
    }

    //Getter and Setters
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public String getCity() { return city; }
    public String getProvince() {
        return province;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getCreditCardNumber() {
        return creditCardNumber;
    }
    public String getCreditCardExpiry() { return creditCardExpiry; }
    public String getPassword(){ return password; }
}



