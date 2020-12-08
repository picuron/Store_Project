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
        Item i5 = new Item("0 Quantity Item", 0, 10, 15, "No stock");
        Items = new ArrayList<Item>();
        Items.add(i1);
        Items.add(i2);
        Items.add(i3);
        Items.add(i4);
        Items.add(i5);

        //Hash map will host <Item, Quantity>
        cart = new HashMap<Item, Integer>();

        //User Menu
        Scanner inputScanner = new Scanner(System.in);
        boolean inProgress = true;
        while(inProgress) {
            System.out.println("[1] View items [2] Add Item to Cart [3] Remove item from cart [4] View cart [5] Checkout [6] Exit");
            String userInput = inputScanner.nextLine();
            int userInputFinal = Integer.valueOf(userInput);

            switch (userInputFinal) {
                case 1:
                    printItemNames();
                    break; // exit out of switch statement
                case 2:
                    addItemToCart();
                    break;
                case 3:
                    break;
                case 4:
                    listCart();
                    break;
                case 5:
                    inProgress = false;
                    break;
            }
        }
    }

    //This will print all items in the inventory, if the quantity is greater than 0
    public static void printItemNames(){
        for(Item i: Items){
            //Don't want to print out of stock items, so quantity must be greater than 0
            if(i.getQuantity() > 0){
                System.out.println("Item: " + i.getItemName() + " | Quantity: " + i.getQuantity() + " | Price: " + i.getListPrice() + " | Description: " + i.getDescription());
            }
        }
    }

    public static void addItemToCart(){
        Scanner inputScanner = new Scanner(System.in);
        printItemNames();
        boolean validName = false;
        boolean validQuantity = false;
        String itemName = null;
        int quantityInt = -1;
        Item selectedItem = null;

        listCart();
        //Doesn't continue until a valid item name is given
        while(!validName){
            System.out.println("Enter the name of the item to add to your cart (case sensitive)");
            itemName = inputScanner.nextLine();

            //Make shirt name is valid
            for(Item i: Items){
                if (i.getItemName().equals(itemName)){
                    selectedItem = i;
                    validName = true;
                }
            }
            if(validName == false){
                System.out.println("The provided name is invalid, please try again. Here are the items you can select:");
                printItemNames();
            }
        }

        while(!validQuantity){
            System.out.println("How many of these would you like?");
            String quantityString = inputScanner.nextLine();
            quantityInt = Integer.valueOf(quantityString);

            //Error trap quantity
            if(selectedItem.getQuantity() < quantityInt){
                System.out.println("We do not have enough of these items in stock to provide you with that many! We have " + selectedItem.getQuantity() + " in stock of that item.");
                System.out.println("Please enter a lower quantity.");
            }
            else if (quantityInt < 1){
                System.out.println("Please enter a valid quantity, greater than 1");
            }
            else{
                validQuantity = true;
            }
        }

        int cartQuantity = 0;
        //Add item and quantity to hashmap
        //If this returns null, that means the item is not in the cart, so we need to create a new record of it
        if(cart.get(selectedItem) == null){
            cart.put(selectedItem, quantityInt);
            System.out.println("Added "+ cart.get(selectedItem) + " of " + selectedItem.getItemName() + "to cart.");
            selectedItem.reduceQuantity(quantityInt);
            listCart();
        }
        //If the item is already in the cart, we will update the value
        else{
            cartQuantity = cart.get(selectedItem);
            //Remove item from cart, so we can add with updated value
            cart.remove(selectedItem);
            cart.put(selectedItem, quantityInt + cartQuantity);
            System.out.println("Updated cart to have "+ cart.get(selectedItem) + " of " + selectedItem.getItemName() + ".");
            selectedItem.reduceQuantity(quantityInt + cartQuantity);
            listCart();
        }

    }

    public static void listCart(){
        System.out.println("Listed below is your current cart");
        for(Item i: cart.keySet()){
            System.out.println("Item: " + i.getItemName() + " | Quantity: " + cart.get(i));
        }
    }
}
