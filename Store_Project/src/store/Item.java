package store;
import java.io.*;

public class Item implements Serializable {

    private String itemName;
    private int quantity;
    private double listPrice;
    private double COG;
    private String description;
    private int numSold;

    public Item(String name, int quantity, double listPrice, double COG, String description){
        this.itemName = name;
        this.quantity = quantity;
        this.listPrice = listPrice;
        this.COG = COG;
        this.description = description;
        numSold = 0;

        Finances.addCOG(COG*quantity);
        Finances.addValue(listPrice*quantity);
    }

    public int getNumSold(){
        return numSold;
    }
    public void increaseNumSold(int increaseBy){
        this.numSold = this.numSold + increaseBy;
    }

    public void reduceQuantity(int reduceBy){
       if(this.quantity >= reduceBy){
           this.quantity = this.quantity - reduceBy;
           Finances.reduceCOG(COG*reduceBy);
           Finances.reduceValue(listPrice*reduceBy);
       }else{
           System.out.println("Cannot reduce into negative number");
       }
    }

    public void increaseQuantity(int increaseBy){
        this.quantity = this.quantity + increaseBy;
        Finances.addCOG(COG*increaseBy);
        Finances.addValue(listPrice*increaseBy);
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
        if (quantity < 0) {
            System.out.println("Cannot set quantity as a negative number");
        } else{
            this.quantity = quantity;
        }
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
