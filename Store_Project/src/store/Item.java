package store;

//Currently assuming that the only aspects of an item that can change is quantity and listPrice, assuming COG
//won't change
public class Item {

    //Implement item Category
    private String itemName;
    private int quantity;
    private double listPrice;
    private double COG;
    private String description;

    public Item(String name, int quantity, double listPrice, double COG, String description){
        this.itemName = name;
        this.quantity = quantity;
        this.listPrice = listPrice;
        this.COG = COG;
        this.description = description;

        Finances.addCOG(COG*quantity);
        Finances.addValue(listPrice*quantity);
    }

    public void reduceQuantity(int reduceBy){
       if(this.quantity > reduceBy){
           this.quantity = this.quantity - reduceBy;
           Finances.reduceCOG(COG*reduceBy);
           Finances.reduceValue(listPrice*reduceBy);
       }
       else{
           System.out.println("Cannot reduce into negative number");
       }
    }

    public void increaseQuantity(int increaseBy){
        this.quantity = this.quantity + increaseBy;
        Finances.addCOG(COG*increaseBy);
        Finances.addValue(listPrice*increaseBy);
    }

    public void changeListPrice(double newPrice){
        Finances.reduceValue(listPrice*quantity);
        Finances.addValue((newPrice*quantity));
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
