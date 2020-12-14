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
        boolean validInput = false;
        String customerInput = null;
        Scanner inputScanner = new Scanner(System.in);
        if(Items.isEmpty() && Customers.isEmpty() && Orders.isEmpty()){

            while(!validInput) {
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("It appears that your store is empty. Would you like to populate the store with data? If not you will have to start from scratch and add items in yourself. (Y/N)");
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                customerInput = inputScanner.nextLine();

                if (customerInput.equals("Y")) {
                    System.out.println("Okay, creating Items, Orders, Customer and Finance Data.");
                    seedStoreData();
                    validInput = true;
                } else if (customerInput.equals("N")) {
                    System.out.println("Okay, proceeding with empty store. You will have to go into merchant view and add items.");
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter Y or N (case-sensitive)");
                    validInput = false;
                }
            }
        }

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
                }else if(inputInt == 2){
                    CustomerView.Run();
                    isValidInput = true;
                }else{
                    System.out.println("Invalid input. Please try again:");
                }
            }

            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static void seedStoreData(){
        Finances.setTax(0.13);
        Item i1 = new Item("Shirt", 30, 40, 15, "Soft, cotton");
        Item i2 = new Item("Hat", 10, 10, 5, "Baby blue, bucket-style");
        Item i3 = new Item("Baby Toy", 15, 30, 20, "Fun to play with, fun to eat!");
        Item i4 = new Item("Chair", 20, 100, 25, "This is a REALLY NICE chair");
        Item i5 = new Item("Toilet Paper", 5, 10, 2, "Get it while it lasts!");
        Item i6 = new Item("Face Mask", 50, 5, 1, "All the COVID protection you need!");
        Item i7 = new Item("Python for Everybody Book", 23, 40, 10, "Learn Python!");
        Item i8 = new Item("Playstation 5", 3, 700, 400, "Latest Generation console!");
        System.out.println("Added 8 items to store.");

        Items.add(i1);
        Items.add(i2);
        Items.add(i3);
        Items.add(i4);
        Items.add(i5);
        Items.add(i6);
        Items.add(i7);
        Items.add(i8);
        FileRW.writeItems(Items);
        FileRW.writeFinances(Finances.getRevenue(), Finances.getProfit(), Finances.getCOG(), Finances.getValue(), 0.13);

        Customer c1 = new Customer("Eric Herscovich", "eric.herscovich@gmail.com", "613-883-8090", "520 Katnick Way" , "Ottawa", "Ontario", "K2T0E5", "1234567891234567", "03/2021", "eric");
        System.out.println("Created Customer 'Eric Herscovich' with password 'eric'");
        Customer c2 = new Customer("Tom Hinden", "tom.hinden@gmail.com", "613-599-4284", "90 Langford Crescent" , "Ottawa", "Ontario", "K2K2N6", "7654321987654321", "10/2021", "tom");
        System.out.println("Created Customer 'Tom Hinden' with password 'tom'");
        Customer c3 = new Customer("Jordan Egli", "jordan.egli@gmail.com", "613-867-8090", "13 Cecil Walden Ridge" , "Ottawa", "Ontario", "K2K3C6", "9876543211234567", "05/2023", "jordan");
        System.out.println("Created Customer 'Jordan Egli' with password 'Jordan'");
        Customers.add(c1);
        Customers.add(c2);
        Customers.add(c3);
        FileRW.writeCustomer(Customers);

        HashMap<Item, Integer> o1cart = new HashMap<Item, Integer>();
        o1cart.put(i1, 2);
        i1.reduceQuantity(2);
        i1.increaseNumSold(2);
        o1cart.put(i3, 3);
        i3.reduceQuantity(3);
        i3.increaseNumSold(2);
        Order o1 = new Order(c1, o1cart);
        Orders.add(o1);

        HashMap<Item, Integer> o2cart = new HashMap<Item, Integer>();
        o2cart.put(i2, 1);
        i2.reduceQuantity(1);
        i2.increaseNumSold(1);
        Order o2 = new Order(c2, o2cart);
        Orders.add(o2);

        HashMap<Item, Integer> o3cart = new HashMap<Item, Integer>();
        o3cart.put(i1, 3);
        i1.reduceQuantity(3);
        i1.increaseNumSold(3);
        o3cart.put(i6, 10);
        i6.reduceQuantity(10);
        i6.increaseNumSold(10);
        Order o3 = new Order(c1, o3cart);
        Orders.add(o3);

        HashMap<Item, Integer> o4cart = new HashMap<Item, Integer>();
        o4cart.put(i7, 1);
        i1.reduceQuantity(1);
        i1.increaseNumSold(1);
        o4cart.put(i4, 6);
        i4.reduceQuantity(6);
        i4.increaseNumSold(6);
        o4cart.put(i8, 1);
        i8.reduceQuantity(1);
        i8.increaseNumSold(1);
        Order o4 = new Order(c3, o4cart);
        Orders.add(o4);

        System.out.println("Added 4 orders.");
        FileRW.writeOrder(Orders);
        FileRW.writeItems(Items);
        FileRW.writeFinances(Finances.getRevenue(), Finances.getProfit(), Finances.getCOG(), Finances.getValue(), Finances.getTax());
    }

    public static ArrayList<Item> getItems() {
        return Items;
    }
    public static ArrayList<Customer> getCustomers() {
        return Customers;
    }
    public static ArrayList<Order> getOrders() {
        return Orders;
    }
}
