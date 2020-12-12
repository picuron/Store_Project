package store;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
    private Customer customer;
    private HashMap<Item, Integer> items;

    public Order(Customer customer, HashMap<Item, Integer> items) {
        this.customer = customer;
        this.items = items;

        changeFinancials(items);
    }

    private void changeFinancials( HashMap<Item, Integer> items){
        double itemCOG = 0;
        double itemValue = 0;
        double profit = 0;

        for(Item i: items.keySet()){
            itemCOG = i.getCOG() * items.get(i);
            itemValue = i.getListPrice() * items.get(i);
            profit =  itemValue - itemCOG;

            Finances.addRevenue(itemValue);
            Finances.addProfit(profit);
            Finances.reduceCOG(itemCOG);
            Finances.reduceValue(itemValue);
        }
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
