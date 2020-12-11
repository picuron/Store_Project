package store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;


public class CustomerView {
    private static ArrayList<Item> Items;
    private static HashMap<Item, Integer> cart;

    public static void Run(){

        Items = StoreApplication.getItems();

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
                    removeItemFromCart();
                    break;
                case 4:
                    listCart();
                    break;
                case 5:
                    inProgress = false;
                    checkoutCart();
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
            System.out.println("Added "+ cart.get(selectedItem) + " of " + selectedItem.getItemName() + " to cart.");
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

    public static void removeItemFromCart() {
        Scanner inputScanner = new Scanner(System.in);
        printItemNames();
        boolean validName = false;
        boolean validQuantity = false;
        String itemName = null;
        int quantityInt = -1;
        Item selectedItem = null;

        listCart();
        while(!validName){
            System.out.println("Enter the name of the item to remove from your cart (case sensitive)");
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
            System.out.println("How many of these do you want to remove?");
            String quantityString = inputScanner.nextLine();
            quantityInt = Integer.valueOf(quantityString);

            //Error trap quantity
            if(quantityInt > cart.get(selectedItem)){
                System.out.println("You're removing too many! You have " + cart.get(selectedItem) + " of that item in your cart.");
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
        cartQuantity = cart.get(selectedItem);
        //Remove item from cart, so we can add with updated value
        cart.remove(selectedItem);
        cart.put(selectedItem, cartQuantity - quantityInt);
        System.out.println("Updated cart to have "+ cart.get(selectedItem) + " of " + selectedItem.getItemName() + ".");
        selectedItem.reduceQuantity(cartQuantity + quantityInt);
        listCart();
    }

    public static void listCart(){
        System.out.println("Listed below is your current cart");
        for(Item i: cart.keySet()){
            System.out.println("Item: " + i.getItemName() + " | Quantity: " + cart.get(i));
        }
    }

    public static void checkoutCart(){

        Scanner inputScanner = new Scanner(System.in);
        String customerName = null;
        String email = null;
        String phoneNumber = null;
        String streetName = null;
        String city = null;
        String state = null;
        String zipCode = null;
        boolean validCustomerName = false;
        boolean validEmail = false;
        boolean validPhoneNumber = false;
        boolean validStreetName = false;
        boolean validCity = false;
        boolean validState = false;
        boolean validZipCode = false;

        // Validate name
        while (!validCustomerName){
            System.out.println("Enter your name");
            customerName = inputScanner.nextLine();

            Pattern p = Pattern.compile("([0-9])");
            Matcher m = p.matcher(customerName);

            if(m.find()){
                System.out.println("Your name is invalid; please try again!");
            } else {
                validCustomerName = true;
            }
        }

        // Validate email
        while (!validEmail){
            System.out.println("Enter your email");
            email = inputScanner.nextLine();

            if(!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                System.out.println("Your email is invalid; please try again!");
            } else {
                validEmail = true;
            }
        }

        // Validate phone number
        while (!validPhoneNumber){
            System.out.println("Enter your phone number (example: 224-504-4923)");
            phoneNumber = inputScanner.nextLine();

            if (!phoneNumber.matches("(?:\\d{3}-){2}\\d{4}")){
                System.out.println("Your phone number is invalid; please try again!");
            } else {
                validPhoneNumber = true;
            }
        }

        // Validate street name
        while (!validStreetName){
            System.out.println("Enter your street name (example: 123 Street)");
            streetName = inputScanner.nextLine();

            if (!streetName.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")){
                System.out.println("Your street name is invalid; please try again!");
            } else {
                validStreetName = true;
            }
        }

        // Validate city
        while (!validCity){
            System.out.println("Enter your city (example: Chicago)");
            city = inputScanner.nextLine();

            if (!city.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")){
                System.out.println("Your city is invalid; please try again!");
            } else {
                validCity = true;
            }
        }

        // Validate state
        while (!validState){
            System.out.println("Enter your state (example: Illinois)");
            state = inputScanner.nextLine();

            if (!state.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")){
                System.out.println("Your state is invalid; please try again!");
            } else {
                validState = true;
            }
        }

        // Validate zip code
        while (!validZipCode){
            System.out.println("Enter your zip code (example: 57753)");
            zipCode = inputScanner.nextLine();

            if (!zipCode.matches("\\d{5}")){
                System.out.println("Your zip code is invalid; please try again!");
            } else {
                validZipCode = true;
            }
        }

        listCart();
        System.out.println("We have your information saved! Email: " + email + " | Phone number: " + phoneNumber);
        System.out.println("Address: " + streetName + ", " + city + ", " + state + ", " + zipCode);
        System.out.println("Thanks " + customerName + " for your purchase!");


    }

    public static ArrayList<Item> getItems(){
        return Items;
    }
}
