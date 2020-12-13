package store;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StoreApplication {

    private static ArrayList<Item> Items;
    private static ArrayList<Customer> Customers;
    private static ArrayList<Order> Orders;

    public static void main(String[] args){

        DirectoryInitilization.Setup();

        Items = new ArrayList<Item>();
        Customers = new ArrayList<Customer>();
        Orders = new ArrayList<Order>();

        Items = FileRW.readItems();
        Customers = FileRW.readCustomers();
        Orders = FileRW.readOrders();
        FileRW.readFinances();

        Finances.setTax(0.13);

        //Initalize objects to use and add to ArrayList- THIS IS TEMPORARY - will add File I/O and adding objects through merchant view
//        Item i1 = new Item("Shirt", 3, 40, 15, "Soft, cotton");
//        Item i2 = new Item("Hat", 10, 10, 5, "Baby blue, bucket-style");
//        Item i3 = new Item("Toy", 15, 30, 20, "Baby Toy");
//        Item i4 = new Item("Chair", 20, 100, 25, "Nice Chair");
//        Item i5 = new Item("0 Quantity Item", 0, 10, 15, "No stock");
//
//        Items.add(i1);
//        Items.add(i2);
//        Items.add(i3);
//        Items.add(i4);
//        Items.add(i5);
//
//        FileRW.writeFinances(Finances.getRevenue(), Finances.getProfit(), Finances.getCOG(), Finances.getValue(), Finances.getTax());
//        FileRW.writeItems(Items);


        //Prompts user to enter merchant view or customer view
        boolean isValidInput = false;
        Scanner userInput = new Scanner(System.in);

        while(!isValidInput){
            System.out.println("Which view would like to enter in?");
            System.out.println("[1] - Merchant View");
            System.out.println("[2] - Customer View");


            String inputString = userInput.nextLine();
            try{
                int inputInt = Integer.valueOf(inputString);

                if(inputInt == 1){
                    MerchantView.Run();
                    isValidInput = true;
                }
                else if(inputInt == 2){
                    CustomerView.Run();
                    isValidInput = true;
                }
                else{
                    System.out.println("Invalid input. Please try again:");
                }
            }

            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }

    }

    public static ArrayList<Item> getItems() {
        return Items;
    }

    public static void setItems(ArrayList<Item> items) {
        Items = items;
    }

    public static ArrayList<Customer> getCustomers() {
        return Customers;
    }

    public static void setCustomers(ArrayList<Customer> customers) {
        Customers = customers;
    }

    public static ArrayList<Order> getOrders() {
        return Orders;
    }

    public static void setOrders(ArrayList<Order> orders) {
        Orders = orders;
    }
}
