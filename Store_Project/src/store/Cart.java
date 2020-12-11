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


//        order.setItems();
    }




//    public int getOrderID() {
//        return orderID;
//    }
//
//    public void setOrderID(int orderID) {
//        this.orderID = orderID;
//    }
//
//    public int getCustomerID() {
//        return customerID;
//    }
//
//    public void setCustomerID(int customerID) {
//        this.customerID = customerID;
//    }
//
//    public Item[] getItems() {
//        return items;
//    }
//
//    public void setItems(Item[] items) {
//        this.items = items;
//    }
//}
}
