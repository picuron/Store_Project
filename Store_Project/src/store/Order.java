package store;

public class Order {
    private int orderID;
    private int customerID;
    private Item[] items;

    public Order(int orderID, int customerID, Item[] items) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.items = items;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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
