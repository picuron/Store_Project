package store;

import java.io.*;

//Currently assuming that the only aspects of an item that can change is quantity and listPrice, assuming COG
//won't change
public class Item implements Serializable {

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

        //StoreApplication.writeToItemFile(this);
    }

//    public void writeToItemFile(Item item){
//        try{
//            System.out.println(item);
//            FileOutputStream f = new FileOutputStream(StoreApplication.getItemFile());
//            ObjectOutputStream o = new ObjectOutputStream(f);
//            o.writeObject(item);
//            o.close();
//            f.close();
//        }
//        catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        }
//
//    }
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

        if (newPrice < 0) { System.out.println("Cannot reduce price into negative number"); }
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

    public void updateCustomerItems(Item selectedItem) {

    }
}
