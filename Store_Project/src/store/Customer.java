package store;

public class Customer {
    private int CustomerID;
    private String name;
    private String email;
    private String phoneNumber;
    private String streetName;
    private String city;
    private String province;
    private String postalCode;
    private int creditCardNumber;
    private String creditCardExpiry;

    // For now, customer attributes can't be changed. We should try to add support for this.
    public Customer(int customerID, String name, String email, String phoneNumber, String streetName, String city, String province,
                    String postalCode, int creditCardNumber, String creditCardExpiry) {
        CustomerID = customerID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public void setCreditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }
}



