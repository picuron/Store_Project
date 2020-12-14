package store;

public class Cart {
    private int customerID;
    private Item[] items;
    private int subTotal;
    private Order order;

    public Cart(int customerID, Item[] items, int subTotal, Order order) {
        this.customerID = customerID;
        this.items = items;
        this.subTotal = subTotal;
        this.order = order;
    }
}
