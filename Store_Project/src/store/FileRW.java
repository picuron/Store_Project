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

    public static void readFinances(){
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader("Data/Finance/Finances.txt");
            br = new BufferedReader(fr);

            String line;
            double lineDouble;
            int counter = 0;
            while((line = br.readLine()) != null){
                lineDouble = Double.parseDouble(line);

                if(counter == 0){
                    Finances.setRevenue(lineDouble);
                }
                else if(counter == 1){
                    Finances.setProfit(lineDouble);
                }
                else if(counter == 2){
                    Finances.setCOG(lineDouble);
                }
                else if(counter == 3){
                    Finances.setValue(lineDouble);
                }
                else if(counter == 4){
                    Finances.setTax(lineDouble);
                }
            counter++;
            }
        }catch (IOException e) {
            //
        } finally {
            try {
                if (fr != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                //
            }
        }
    }

    public static void writeFinances(double revenue, double profit, double COG, double value, double tax){
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("Data/Finance/Finances.txt");
            bw = new BufferedWriter(fw);

            bw.write(String.valueOf(revenue));
            bw.newLine();
            bw.write(String.valueOf(profit));
            bw.newLine();
            bw.write(String.valueOf(COG));
            bw.newLine();
            bw.write(String.valueOf(value));
            bw.newLine();
            bw.write(String.valueOf(tax));

        } catch (IOException e) {
            //
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                //
            }
        }
    }

}
