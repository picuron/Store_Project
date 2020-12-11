package store;

public class Order {
    private Customer customer;
    private Item[] items;

    public Order(int orderID, int customerID, Item[] items) {
        this.customerID = customerID;
        this.items = items;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
