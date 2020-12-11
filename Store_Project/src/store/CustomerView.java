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
            boolean validMenuInput = false;
            while(!validMenuInput){
                System.out.println("[1] View items [2] Add Item to Cart [3] Remove item from cart [4] View cart [5] Checkout [6] Exit");
                String userInput = inputScanner.nextLine();

                int userInputFinal= 0;

                try{
                    userInputFinal = Integer.valueOf(userInput);
                }
                catch(NumberFormatException e){
                    validMenuInput = false;
                }

                if(userInputFinal>0 && userInputFinal<7){
                    System.out.println("Posted");
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
                        case 6:
                            break;
                    }
                }
                else{
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    //This will print all items in the inventory, if the quantity is greater than 0
    public static void printItemNames(){
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Items in Stock:");
        for(Item i: Items){
            //Don't want to print out of stock items, so quantity must be greater than 0
            if(i.getQuantity() > 0){
                System.out.println("Item: " + i.getItemName() + " | Quantity: " + i.getQuantity() + " | Price: " + i.getListPrice() + " | Description: " + i.getDescription());
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void addItemToCart(){
        Scanner inputScanner = new Scanner(System.in);
        printItemNames();
        boolean validName = false;
        boolean validQuantity = false;
        String itemName = null;
        int quantityInt = -1;
        Item selectedItem = null;

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
        selectedItem.increaseQuantity(quantityInt);
        listCart();
    }

    public static void listCart(){
        double cartTotal = 0;
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        if(cart.isEmpty()){
            System.out.println("There's nothing in your cart yet");
        }
        else{
            System.out.println("Listed below is your current cart");
            for(Item i: cart.keySet()){
                System.out.println("Item: " + i.getItemName() + " | Quantity: " + cart.get(i));
                cartTotal = cartTotal + (i.getListPrice() * cart.get(i));
//            }
            }
            System.out.println("Cart total: $" + cartTotal);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void checkoutCart(){

        Scanner inputScanner = new Scanner(System.in);
        String customerName = null;
        String email = null;
        String phoneNumber = null;
        String streetName = null;
        String city = null;
        String province = null;
        String postalCode = null;
        String creditCardNumber = null;
        String creditCardExpirationDate = null;
        boolean validCustomerName = false;
        boolean validEmail = false;
        boolean validPhoneNumber = false;
        boolean validStreetName = false;
        boolean validCity = false;
        boolean validProvince = false;
        boolean validPostalCode = false;
        boolean validCreditCardNumber = false;
        boolean validCreditCardExpirationDate = false;

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
            System.out.println("Enter your city (example: Ottawa)");
            city = inputScanner.nextLine();

            if (!city.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")){
                System.out.println("Your city is invalid; please try again!");
            } else {
                validCity = true;
            }
        }

        // Validate province
        while (!validProvince){
            System.out.println("Enter your province (example: Ontario)");
            province = inputScanner.nextLine();

            if (!province.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")){
                System.out.println("Your province is invalid; please try again!");
            } else {
                validProvince = true;
            }
        }

        // Validate postal code
        while (!validPostalCode){
            System.out.println("Enter your postal code (example: 577533)");
            postalCode = inputScanner.nextLine();

            if (!postalCode.matches("\\d{6}")){
                System.out.println("Your postal code is invalid; please try again!");
            } else {
                validPostalCode = true;
            }
        }

        // Validate credit card number
        while (!validCreditCardNumber){
            System.out.println("Enter your credit card number");
            creditCardNumber = inputScanner.nextLine();

            if (!creditCardNumber.matches("\\d{16}")){
                System.out.println("Your credit card number is invalid; please try again!");
            } else {
                validCreditCardNumber = true;
            }
        }

        // Validate credit card number
        while (!validCreditCardExpirationDate){
            System.out.println("Enter your credit card expiration date");
            creditCardExpirationDate = inputScanner.nextLine();

            if (!creditCardExpirationDate.matches("(0[1-9]|10|11|12)/20[0-9]{2}$")){
                System.out.println("Your credit card expiration date is invalid; please try again!");
            } else {
                validCreditCardExpirationDate = true;
            }
        }

        listCart();
        System.out.println("We have your information saved! Email: " + email + " | Phone number: " + phoneNumber);
        System.out.println("Address: " + streetName + ", " + city + ", " + province + ", " + postalCode);
        System.out.println("Thanks " + customerName + " for your purchase!");
    }

    public static ArrayList<Item> getItems(){
        return Items;
    }
}
