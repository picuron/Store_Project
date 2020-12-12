package store;

import java.io.*;
import java.util.ArrayList;

public class FileRW {

    public static ArrayList<Item> readItems(){
        ArrayList<Item> Items = new ArrayList<Item>();
        try {

            FileInputStream fi = new FileInputStream(new File("Data/Inventory/Items.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            try{
                for(;;){
                    Item i = (Item) oi.readObject();
                    Items.add(i);
                }
            }catch (EOFException e){
                //EOF
            }

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            //EOF or Empty File
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Items;
    }

    public static void writeItems(ArrayList<Item> Items){

        try {
            FileOutputStream f = new FileOutputStream(new File("Data/Inventory/Items.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            for(Item i: Items){
                o.writeObject(i);
            }
            // Write objects to file

            o.close();
            f.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public static ArrayList<Customer> readCustomers(){
        ArrayList<Customer> Customers = new ArrayList<Customer>();
        try {

            FileInputStream fi = new FileInputStream(new File("Data/Customer/Customers.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            try{
                for(;;){
                    Customer c = (Customer) oi.readObject();
                    Customers.add(c);
                }
            }catch (EOFException e){
                //EOF
            }

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
           //EOF or Empty File
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Customers;
    }

    public static void writeCustomer(ArrayList<Customer> Customers){

        try {
            FileOutputStream f = new FileOutputStream(new File("Data/Customer/Customers.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            for(Customer c: Customers){
                o.writeObject(c);
            }
            // Write objects to file

            o.close();
            f.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public static ArrayList<Order> readOrders(){
        ArrayList<Order> Orders = new ArrayList<Order>();
        try {

            FileInputStream fi = new FileInputStream(new File("Data/Order/Orders.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            try{
                for(;;){
                    Order o = (Order) oi.readObject();
                    Orders.add(o);
                }
            }catch (EOFException e){
                //EOF
            }

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            //EOF or Empty File
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Orders;
    }

    public static void writeOrder(ArrayList<Order> Orders){

        try {
            FileOutputStream f = new FileOutputStream(new File("Data/Order/Orders.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            for(Order c: Orders){
                o.writeObject(c);
            }
            // Write objects to file

            o.close();
            f.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }
}
