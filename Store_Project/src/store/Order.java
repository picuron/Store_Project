package store;

import java.util.HashMap;

public class Order {
    private Customer customer;
    private HashMap<Item, Integer> items;

    public Order(Customer customer, HashMap<Item, Integer> items) {
        this.customer = customer;
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Item, Integer> items) {
        this.items = items;
    }
}
