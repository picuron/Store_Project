package store;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StoreApplication {

    private static ArrayList<Item> Items;
    static File itemFile = new File("Data/Inventory/Items.txt");
//    static FileOutputStream FileOutput;
//    static ObjectOutputStream ObjectOutput;
//
//    {
//        try {
//            FileOutputStream FileOutput = new FileOutputStream(itemFile);
//            ObjectOutputStream ObjectOutput = new ObjectOutputStream(FileOutput);
//            System.out.println(FileOutput);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//    }

    

    
    public static void main(String[] args){

        DirectoryInitilization.Setup();


        //Initalize objects to use and add to ArrayList- THIS IS TEMPORARY - will add File I/O and adding objects through merchant view
        Item i1 = new Item("Shirt", 3, 40, 15, "Soft, cotton");
        Item i2 = new Item("Hat", 10, 10, 5, "Baby blue, bucket-style");
        Item i3 = new Item("Toy", 15, 30, 20, "Baby Toy");
        Item i4 = new Item("Chair", 20, 100, 25, "Nice Chair");
        Item i5 = new Item("0 Quantity Item", 0, 10, 15, "No stock");

        Items = new ArrayList<Item>();
        //loadItems();
        Items.add(i1);
        Items.add(i2);
        Items.add(i3);
        Items.add(i4);
        Items.add(i5);

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

    public static ArrayList<Item> getItems(){
        return Items;
    }

    public static File getItemFile(){
        return itemFile;
    }
    public static void loadItems(){
        try {
            FileInputStream f = new FileInputStream(itemFile);
            ObjectInputStream o = new ObjectInputStream(f);

            Item item = null;

            Item pr1 = (Item) o.readObject();
            //Item pr2 = (Item) o.readObject();

            System.out.println(pr1.getItemName());
            //System.out.println(pr2.getItemName());
            // Read objects
//            while ((item = (Item) o.readObject()) != null) {
//                if (item instanceof Item) {
//                    System.out.println(item.getItemName());
//                }
//            }

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public static void writeToItemFile(Item item){
//        try{
//            System.out.println(item);
//            //FileOutputStream FileOutput = new FileOutputStream(StoreApplication.getItemFile());
//            //ObjectOutputStream ObjectOutput = new ObjectOutputStream(f);
//            System.out.println(ObjectOutput);
//            ObjectOutput.writeObject(item);
//            ObjectOutput.close();
//            FileOutput.close();
//        }
//        catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        }
//
//    }
}
