package store;

public class Customer {
    private int CustomerID;
    private String name;
    private String email;
    private String address;
    private int creditCardNumber;

    public Customer(String name, String email, String address, int creditCardNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

}
