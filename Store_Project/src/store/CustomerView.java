package store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;


public class CustomerView {
    private static ArrayList<Item> Items;
    private static ArrayList<Customer> Customers;
    private static ArrayList<Order> Orders;
    private static HashMap<Item, Integer> cart;

    public static void Run(){

        cart = new HashMap<Item, Integer>();
        Items = StoreApplication.getItems();
        Customers = StoreApplication.getCustomers();
        Orders = StoreApplication.getOrders();

        //User Menu
        Scanner inputScanner = new Scanner(System.in);
        boolean inProgress = true;
        while(inProgress) {
            boolean validMenuInput = false;
            while(!validMenuInput){
                System.out.println("[1] View items [2] Add Item to Cart [3] Remove item from cart [4] View cart [5] Checkout [6] View Past Orders [7] Exit program");
                String userInput = inputScanner.nextLine();

                int userInputFinal= 0;

                try{
                    userInputFinal = Integer.valueOf(userInput);
                }
                catch(NumberFormatException e){
                    validMenuInput = false;
                }

                if(userInputFinal>0 && userInputFinal<8){
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
                            if(cart.isEmpty()){
                                System.out.println("Your cart is empty! You can't check out with an empty cart");
                                break;
                            }
                            else{
                                inProgress = false;
                                checkoutCart();
                                break;
                            }
                        case 6:
                            viewOrders();
                            break;
                        case 7:
                            System.out.println("Thank you for shopping with us!");
                            //Put items in cart back in stock
                            for(Item i: cart.keySet()){
                                i.increaseQuantity(cart.get(i));
                            }
                            //Ensure all changes are saved
                            FileRW.writeItems(Items);
                            FileRW.writeOrder(Orders);
                            FileRW.writeCustomer(Customers);
                            FileRW.writeFinances(Finances.getRevenue(), Finances.getProfit(), Finances.getCOG(), Finances.getValue(), Finances.getTax());
                            System.exit(1);
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
        listCart();
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
        if(cartQuantity - quantityInt != 0){
            cart.put(selectedItem, cartQuantity - quantityInt);
            System.out.println("Updated cart to have "+ cart.get(selectedItem) + " of " + selectedItem.getItemName() + ".");
        }
        else{
            System.out.println("Removed " + selectedItem.getItemName() + " from your cart.");
        }
        selectedItem.increaseQuantity(quantityInt);
        listCart();
    }

    public static void listCart(){
        double cartTotal = 0.00;
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        if(cart.isEmpty()){
            System.out.println("Your cart is empty.");
        }
        else{
            System.out.println("Listed below is your current cart");
            for(Item i: cart.keySet()){
                System.out.println("Item: " + i.getItemName() + " | Quantity: " + cart.get(i));
                cartTotal = cartTotal + (i.getListPrice() * cart.get(i));
//            }
            }
            String formattedSubtotal = String.format("%.2f", cartTotal);
            String formattedTotal = String.format("%.2f", (cartTotal * (1+Finances.getTax())));

            System.out.println("Cart subtotal: $" + formattedSubtotal);
            System.out.println("Cart total: $" + formattedTotal);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void checkoutCart(){
        Scanner inputScanner = new Scanner(System.in);
        String customerInput;
        Customer customer = null;
        boolean validCustomerInput = false;
        boolean validInputNameNotFound = false;
        boolean validCustomerName = false;

        outerloop:
        while(!validCustomerInput){
            System.out.println("Do you already have an account with us? (Y/N)");
            customerInput = inputScanner.nextLine();

            if(customerInput.equals("Y")){
                System.out.println("Great, please enter your full name");
                customerInput = inputScanner.nextLine();

                for(Customer c: Customers){
                    if(c.getName().equals(customerInput)){
                        customer = c;
                        validCustomerInput = true;
                        validCustomerName = true;
                    }
                }

                boolean validPassword = false;
                if(validCustomerName == false){
                    while(validInputNameNotFound == false){
                        System.out.println("We couldn't find your name as an existing customer. What would you like to do: ");
                        System.out.println("[1] Try again");
                        System.out.println("[2] Make new account");
                        customerInput = inputScanner.nextLine();

                        if(customerInput.equals("1")){
                            validCustomerInput = false;
                            break;
                        }
                        else if (customerInput.equals("2")){
                            validCustomerInput = true;
                            customer = createCustomer();
                            break;
                        }
                        else{
                            System.out.println("Invalid input, please try again.");
                        }
                    }
                }
                else{
                    System.out.println("Found you!");
                    while(!validPassword){
                        System.out.println("Please enter your password to confirm it's you: ");
                        customerInput = inputScanner.nextLine();

                        if(customer.getPassword().equals(customerInput)){
                            System.out.println("Perfect, found you!");
                            validPassword = true;
                            break;
                        }
                        else{
                            System.out.println("Incorrect password. What would you like to do: ");
                            System.out.println("[1] Try a different password");
                            System.out.println("[2] Create new account");
                            customerInput = inputScanner.nextLine();

                            if(customerInput.equals("1")){
                                validPassword = false;
                            }
                            else if (customerInput.equals("2")){
                                validCustomerInput = false;
                                customer = createCustomer();
                                break outerloop;
                            }
                            else{
                                System.out.println("Invalid input, please try again.");
                            }
                        }
                    }
                }
            }
            else if(customerInput.equals("N")){
                System.out.println("Okay, you will need to create an account.");
                validCustomerInput = true;
                customer = createCustomer();
            }
            else{
                System.out.println("Invalid input. Please try again (case-sensitive).");
            }

        }

        for(Item i: cart.keySet()){
            i.increaseNumSold(cart.get(i));
        }

        Order o = new Order(customer, cart);
        Orders.add(o);
        FileRW.writeOrder(Orders);
        FileRW.writeItems(Items);
        //FileRW.writeFinances(Finances.class);
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Order Confirmed. Thank you for your order " + customer.getName() + "!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        cart = new HashMap<Item, Integer>();
        //cart.clear();
    }

    public static Customer createCustomer(){
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
        String password = null;
        boolean validCustomerName = false;
        boolean validEmail = false;
        boolean validPhoneNumber = false;
        boolean validStreetName = false;
        boolean validCity = false;
        boolean validProvince = false;
        boolean validPostalCode = false;
        boolean validCreditCardNumber = false;
        boolean validCreditCardExpirationDate = false;
        boolean validPassword = false;

        // Validate name
        while (!validCustomerName){
            boolean repeatFound = false;

            System.out.println("Enter your name");
            customerName = inputScanner.nextLine();

            Pattern p = Pattern.compile("([0-9])");
            Matcher m = p.matcher(customerName);

            for(Customer c: Customers){
                if(c.getName().equals(customerName)){
                    repeatFound = true;
                }
            }
            if(m.find()){
                System.out.println("Your name is invalid; please try again!");
            }
            else if(repeatFound == true){
                System.out.println("It appears there is already an account in that name. Please use a different name.");
            }
            else {
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
            System.out.println("Enter your postal code (example: K2K2N6)");
            postalCode = inputScanner.nextLine();

            if (!postalCode.matches("^([A-Za-z]\\d[A-Za-z][-]?\\d[A-Za-z]\\d)")){
                System.out.println("Your postal code is invalid; please try again!");
            } else {
                validPostalCode = true;
            }
        }

        // Validate credit card number
        while (!validCreditCardNumber){
            System.out.println("Enter your credit card number (example: 1234567891234567)");
            creditCardNumber = inputScanner.nextLine();

            if (!creditCardNumber.matches("\\d{16}")){
                System.out.println("Your credit card number is invalid; please try again!");
            } else {
                validCreditCardNumber = true;
            }
        }

        // Validate credit card number
        while (!validCreditCardExpirationDate){
            System.out.println("Enter your credit card expiration date (example: 03/2021)");
            creditCardExpirationDate = inputScanner.nextLine();

            if (!creditCardExpirationDate.matches("(0[1-9]|10|11|12)/20[0-9]{2}$")){
                System.out.println("Your credit card expiration date is invalid; please try again!");
            } else {
                validCreditCardExpirationDate = true;
            }
        }

        // Validate password
        while (!validPassword){
            System.out.println("Enter a password");
            password = inputScanner.nextLine();

            if (password == ""){
                System.out.println("Your password is invalid; please try again!");
            } else {
                validPassword = true;
            }
        }

        Customer c = new Customer(customerName, email, phoneNumber, streetName, city, province, postalCode, creditCardNumber, creditCardExpirationDate, password);
        Customers.add(c);
        FileRW.writeCustomer(Customers);
        System.out.println("We have your information saved! Email: " + email + " | Phone number: " + phoneNumber);
        System.out.println("Address: " + streetName + ", " + city + ", " + province + ", " + postalCode);

        return c;
    }

    public static void viewOrders(){
        Scanner inputScanner = new Scanner(System.in);
        String customerInput;
        Customer customer = null;
        boolean validCustomerInput = false;
        boolean validInputNameNotFound = false;
        boolean validCustomerName = false;

        outerloop:
        while(!validCustomerInput) {
            System.out.println("You need an account to view orders. Do you already have an account with us? (Y/N)");
            customerInput = inputScanner.nextLine();

            if (customerInput.equals("Y")) {
                System.out.println("Great, please enter your full name");
                customerInput = inputScanner.nextLine();

                for (Customer c : Customers) {
                    if (c.getName().equals(customerInput)) {
                        customer = c;
                        validCustomerInput = true;
                        validCustomerName = true;
                    }
                }

                boolean validPassword = false;
                if (validCustomerName == false) {
                    while (validInputNameNotFound == false) {
                        System.out.println("We couldn't find your name as an existing customer. What would you like to do: ");
                        System.out.println("[1] Try again");
                        System.out.println("[2] Exit");
                        customerInput = inputScanner.nextLine();

                        if (customerInput.equals("1")) {
                            validCustomerInput = false;
                            break;
                        } else if (customerInput.equals("2")) {
                            validCustomerInput = true;
                            break;
                        } else {
                            System.out.println("Invalid input, please try again.");
                        }
                    }
                } else {
                    System.out.println("Found you!");
                    while (!validPassword) {
                        System.out.println("Please enter your password to confirm it's you: ");
                        customerInput = inputScanner.nextLine();

                        if (customer.getPassword().equals(customerInput)) {
                            System.out.println("Perfect, found you! Here are your orders: ");

                            int counter = 1;
                            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            for(Order o: Orders){
                                validPassword = true;
                                if(o.getCustomer().getName().equals(customer.getName()) && o.getCustomer().getPassword().equals(customer.getPassword())){
                                    HashMap<Item, Integer> items = new HashMap<Item, Integer>();
                                    items = o.getItems();

                                    System.out.println("Order " + counter + ":");
                                    for(Item i: items.keySet()){
                                        System.out.println("Item: " + i.getItemName() + " | Quantity: " + items.get(i));
                                    }

                                    counter++;
                                }
                            }
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        } else {
                            System.out.println("Incorrect password. What would you like to do: ");
                            System.out.println("[1] Try a different password");
                            System.out.println("[2] Exit");
                            customerInput = inputScanner.nextLine();

                            if (customerInput.equals("1")) {
                                validPassword = false;
                            } else if (customerInput.equals("2")) {
                                break outerloop;
                            } else {
                                System.out.println("Invalid input, please try again.");
                            }
                        }
                    }
                }
            } else if (customerInput.equals("N")) {
                System.out.println("You will not be able to view orders then. Exiting to main menu.");
                validCustomerInput = true;
            } else {
                System.out.println("Invalid input. Please try again (case-sensitive).");
            }
        }
    }

    public static ArrayList<Item> getItems(){
        return Items;
    }
}
