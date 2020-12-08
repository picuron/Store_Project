package store;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CustomerView {
    private static ArrayList<Item> Items;
    private static HashMap<Item, Integer> cart;

    public static void Run(){

        //Initalize objects to use and add to ArrayList- THIS IS TEMPORARY - will add File I/O and adding objects through merchant view
        Item i1 = new Item("Shirt", 3, 40, 15, "Soft, cotton");
        Item i2 = new Item("Hat", 10, 10, 5, "Baby blue, bucket-style");
        Item i3 = new Item("Toy", 15, 30, 20, "Baby Toy");
        Item i4 = new Item("Chair", 20, 100, 25, "Nice Chair");
        Items = new ArrayList<Item>();
        Items.add(i1);
        Items.add(i2);
        Items.add(i3);
        Items.add(i4);

        cart = new HashMap<Item, Integer>();

        System.out.println(Items);
        //User Menu
        Scanner myObj = new Scanner(System.in);
        boolean inProgress = true;
        while(inProgress) {
            System.out.println("[1] View items [2] Add Item to Cart [3] View cart [4] Checkout [5] Exit");
            String userInput = myObj.nextLine();
            int userInputFinal = Integer.valueOf(userInput);

            switch (userInputFinal) {
                case 1:
                    printItemNames();
                    break; // exit out of switch statement
                case 2:
                    //purchaseItem();
                    break;
                case 4:
                    inProgress = false;
                    break;
            }
        }
    }

    public static void printItemNames(){
        for(Item i: Items){
            System.out.println("Item: " + i.getItemName() + " | Quantity: " + i.getQuantity() + " | Price: " + i.getListPrice() + " | Description: " + i.getDescription());
        }
    }
}
