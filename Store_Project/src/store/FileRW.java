package store;
import java.io.*;
import java.util.ArrayList;

//This class is responsible for reading and writing objects and attributes of the different classes
public class FileRW {

    //Pulls all of the Item objects from the Items file
    public static ArrayList<Item> readItems(){
        ArrayList<Item> Items = new ArrayList<Item>();

        //Attempt to read from file
        try {
            FileInputStream fi = new FileInputStream(new File("Data/Inventory/Items.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read all objects until end of file is found
            try{
                for(;;){
                    Item i = (Item) oi.readObject();
                    Items.add(i);
                }
            }catch (EOFException e){
            }

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Items;
    }

    //Write items to file, given the ArrayList of Items
    public static void writeItems(ArrayList<Item> Items){

        //Try to open file and write
        try {
            FileOutputStream f = new FileOutputStream(new File("Data/Inventory/Items.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            //Write each object in the ArrayList
            for(Item i: Items){
                o.writeObject(i);
            }

            o.close();
            f.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    //Pulls all the Customer objects from the Customer files
    public static ArrayList<Customer> readCustomers(){
        ArrayList<Customer> Customers = new ArrayList<Customer>();

        //Try reading from Customers file
        try {
            FileInputStream fi = new FileInputStream(new File("Data/Customer/Customers.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            //Iterate through each object in file until end of file is reached
            try{
                for(;;){
                    Customer c = (Customer) oi.readObject();
                    Customers.add(c);
                }
            }catch (EOFException e){
            }

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }catch (IOException e) {
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Customers;
    }

    //Write customer objects to file given ArrayList of Customers
    public static void writeCustomer(ArrayList<Customer> Customers){

        //Try openuing file
        try {
            FileOutputStream f = new FileOutputStream(new File("Data/Customer/Customers.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            //Iterate through ArrayList of Customers and write the data
            for(Customer c: Customers){
                o.writeObject(c);
            }

            o.close();
            f.close();

        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    //Read all Order objects from Order file
    public static ArrayList<Order> readOrders(){
        ArrayList<Order> Orders = new ArrayList<Order>();
       //Attempt to open file
        try {

            FileInputStream fi = new FileInputStream(new File("Data/Order/Orders.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            //Iterate through each object until end of file found
            try{
                for(;;){
                    Order o = (Order) oi.readObject();
                    Orders.add(o);
                }
            }catch (EOFException e){
            }

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Orders;
    }

    //Iterate through ArrayList of orders and write each of them to file
    public static void writeOrder(ArrayList<Order> Orders){

        //Try to open file
        try {
            FileOutputStream f = new FileOutputStream(new File("Data/Order/Orders.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            //Iterate through ArrayList and add objects
            for(Order c: Orders){
                o.writeObject(c);
            }

            o.close();
            f.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    //Read finance data from Finance file
    public static void readFinances(){
        BufferedReader br = null;
        FileReader fr = null;
        //Try opening file
        try {
            fr = new FileReader("Data/Finance/Finances.txt");
            br = new BufferedReader(fr);

            String line;
            double lineDouble;
            int counter = 0;
            //Iterate through file until no new line
            while((line = br.readLine()) != null){
                lineDouble = Double.parseDouble(line);

                //Assign each line to it's corresponding variable
                if(counter == 0){
                    Finances.setRevenue(lineDouble);
                }else if(counter == 1){
                    Finances.setProfit(lineDouble);
                }else if(counter == 2){
                    Finances.setCOG(lineDouble);
                }else if(counter == 3){
                    Finances.setValue(lineDouble);
                }else if(counter == 4){
                    Finances.setTax(lineDouble);
                }
            counter++;
            }
        }catch (IOException e) {
        } finally {
            try {
                if (fr != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
            }
        }
    }

    //Write to finance file
    public static void writeFinances(double revenue, double profit, double COG, double value, double tax){
        BufferedWriter bw = null;
        FileWriter fw = null;

        //Attempt to open file and write
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
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
            }
        }
    }
}
