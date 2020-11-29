package store;

public class Item {

    //Implement item Category
    String itemName;
    int quantity;
    double listPrice;
    double COG;
    String description;

    public Item(String name, int quantity, double listPrice, double COG, String description){
        this.itemName = name;
        this.quantity = quantity;
        this.listPrice = listPrice;
        this.COG = COG;
        this.description = description;
    }

    public void setItemName(String name){
        this.itemName = name;
    }

    public String getItemName(){
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getCOG() {
        return COG;
    }

    public void setCOG(double COG) {
        this.COG = COG;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
