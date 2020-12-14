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
                            break;
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
                            for(Item i: cart.keySet()){
                                i.increaseQuantity(cart.get(i));
                            }
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

    public static void printItemNames(){
        if(Items.isEmpty()){
            System.out.println("There is currently no items in the store.");
        }
        else{
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Items in Stock:");
            for(Item i: Items){
                if(i.getQuantity() > 0){
                    System.out.println("Item: " + i.getItemName() + " | Quantity: " + i.getQuantity() + " | Price: " + i.getListPrice() + " | Description: " + i.getDescription());
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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

        if(Items.isEmpty()){
            System.out.println("There is currently no items in the store, so you can't buy anything.");
        }
        else{
            while(!validName){
                System.out.println("Enter the name of the item to add to your cart (case sensitive)");
                itemName = inputScanner.nextLine();

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
            if(cart.get(selectedItem) == null){
                cart.put(selectedItem, quantityInt);
                System.out.println("Added "+ cart.get(selectedItem) + " of " + selectedItem.getItemName() + " to cart.");
                selectedItem.reduceQuantity(quantityInt);
                listCart();
            } else{
                cartQuantity = cart.get(selectedItem);
                cart.remove(selectedItem);
                cart.put(selectedItem, quantityInt + cartQuantity);
                System.out.println("Updated cart to have "+ cart.get(selectedItem) + " of " + selectedItem.getItemName() + ".");
                selectedItem.reduceQuantity(quantityInt + cartQuantity);
                listCart();
            }
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

        if(cart.isEmpty()){
            System.out.println("Your cart is empty, so there is nothing to remove.");
        }
        else{
            while(!validName){
                System.out.println("Enter the name of the item to remove from your cart (case sensitive)");
                itemName = inputScanner.nextLine();

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
                        }else if (customerInput.equals("2")){
                            validCustomerInput = true;
                            customer = createCustomer();
                            break;
                        }else{
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
                        }else{
                            System.out.println("Incorrect password. What would you like to do: ");
                            System.out.println("[1] Try a different password");
                            System.out.println("[2] Create new account");
                            customerInput = inputScanner.nextLine();

                            if(customerInput.equals("1")){
                                validPassword = false;
                            }else if (customerInput.equals("2")){
                                validCustomerInput = false;
                                customer = createCustomer();
                                break outerloop;
                            }else{
                                System.out.println("Invalid input, please try again.");
                            }
                        }
                    }
                }
            }else if(customerInput.equals("N")){
                System.out.println("Okay, you will need to create an account.");
                validCustomerInput = true;
                customer = createCustomer();
            }else{
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
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Order Confirmed. Thank you for your order " + customer.getName() + "!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        cart = new HashMap<Item, Integer>();
    }

    public static String validateCustomerInput(String entryName, String correctExample, String regexMatch){
        boolean validEntry = false;
        Scanner inputScanner = new Scanner(System.in);
        String customerEntry = null;

        while (!validEntry){
            System.out.println("Enter your " + entryName + " (example: " + correctExample + ")");
            customerEntry = inputScanner.nextLine();

            if(!customerEntry.matches(regexMatch)){
                System.out.println("Your " + entryName + " is invalid; please try again!");
            } else{
                validEntry = true;
            }
        }
        return customerEntry;
    }

    public static Customer createCustomer(){
        String[] customer = new String[10];
        customer[0] = validateCustomerInput("name", "Bob", "^[A-Za-z.\\s_-]+$");
        customer[1] = validateCustomerInput("email", "bob@gmail.com", "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
        customer[2] = validateCustomerInput("phone", "123-456-7890","(?:\\d{3}-){2}\\d{4}");
        customer[3] = validateCustomerInput("street name", "123 Street", "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        customer[4] = validateCustomerInput("city", "Ottawa", "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        customer[5] = validateCustomerInput("province", "Ontario", "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
        customer[6] = validateCustomerInput("postal code", "A1A-1A1", "^([A-Za-z]\\d[A-Za-z][-]?\\d[A-Za-z]\\d)");
        customer[7] = validateCustomerInput("credit card number", "1231231231231231", "\\d{16}");
        customer[8] = validateCustomerInput("credit card expiration date", "12/2022", "(0[1-9]|10|11|12)/20[0-9]{2}$");
        customer[9] = validateCustomerInput("password", "Secure123 (everything except spaces and new lines)","^[^\\n ]*$");

        Customer c = new Customer(customer[0], customer[1], customer[2], customer[3], customer[4], customer[5], customer[6], customer[7], customer[8], customer[9]);
        Customers.add(c);
        FileRW.writeCustomer(Customers);
        System.out.println("We have your information saved! Email: " + customer[1] + " | Phone number: " + customer[2]);
        System.out.println("Address: " + customer[3] + ", " + customer[4] + ", " + customer[5] + ", " + customer[6]);
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
