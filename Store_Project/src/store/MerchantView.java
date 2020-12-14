package store;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class MerchantView {
    private static ArrayList<Item> Items;
    private static ArrayList<Order> Orders;
    private static ArrayList<Customer> Customers;

    public static void Run() {

        Items = StoreApplication.getItems();
        Orders = StoreApplication.getOrders();
        Customers = StoreApplication.getCustomers();

        Scanner inputScanner = new Scanner(System.in);

        //Keeps Merchant menu running after each action is completed
        boolean inProgress = true;
        while (inProgress) {
            boolean validMenuInput = false;
            while (!validMenuInput) {
                System.out.println("[1] Stock [2] Finance [3] Orders (view only) [4] Customers (view only) [5] Exit");
                String userInput = inputScanner.nextLine();

                int userInputFinal = 0;

                //Exception handling if user inputs invalid menu option
                try {
                    userInputFinal = Integer.valueOf(userInput);
                } catch (NumberFormatException e) {
                    validMenuInput = false;
                }

                //Checks if user input is in the correct numerical range
                if (userInputFinal > 0 && userInputFinal < 7) {
                    //Navigates to correct corresponding function based on user input
                    switch (userInputFinal) {
                        case 1:
                            viewStock();
                            break;
                        case 2:
                            viewFinances();
                            break;
                        case 3:
                            viewOrders();
                            break;
                        case 4:
                            viewCustomers();
                            break;
                        case 5:
                            //Ends the program in terminal and saves data to file
                            FileRW.writeItems(Items);
                            System.out.println("Bye!");
                            System.exit(1);
                            break;

                    }
                } else {
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    //Prints ALL information for each item (including COG!)
    public static void viewStock() {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Items in Stock:");
        for (Item i : Items) {
            if (i.getQuantity() > 0) {
                System.out.println("Item: " + i.getItemName() + " | Quantity: " + i.getQuantity() + " | Price: " + i.getListPrice() + " | COG: " + i.getCOG() + " | Description: " + i.getDescription() + " | Number Sold: " + i.getNumSold());
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //Merchant can select whether or not they'd like to edit stock or exit and return to Merchant menu
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("[1] Edit stock [2] Exit");
        int userInput = inputScanner.nextInt();

        switch (userInput) {
            case 1:
                changeStock();
                break;
            case 2:
                break;
        }
    }

    //Merchant can select from this menu on how they want to make edit to stock
    public static void changeStock() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("[1] Edit/delete a specific item [2] Add a new item [3] Exit");
        int userInput = inputScanner.nextInt();

        switch (userInput) {
            case 1:
                editStock();
                break;
            case 2:
                addStock();
                break;
            case 3:
                break;
        }
    }

    //Allows Merchant to edit a single item
    public static void editStock() {
        Scanner inputScanner = new Scanner(System.in);
        int userInput = 0;
        String userStringInput = null;

        boolean validName = false;
        Item selectedItem = null;

        //Validation for user input for what item they want to edit
        while (!validName) {
            System.out.println("Enter the name of the item you want to edit (case sensitive)");
            userStringInput = inputScanner.nextLine();

            for (Item i : Items) {
                if (i.getItemName().equals(userStringInput)) {
                    selectedItem = i;
                    validName = true;
                    break;
                }
            }
            if (validName == false) {
                System.out.println("The provided item is invalid, please try again. Here are the items you can select:");
                viewStock();
            }
        }

        //Another menu that allows the user to determine what action they want to perform to the selected object
        System.out.println("What do you want to edit? [1] Item name [2] Quantity [3] Price [4] COG [5] Description [6] Delete");
        userInput = inputScanner.nextInt();

        //Navigates to correct corresponding function
        switch (userInput) {
            case 1:
                editItemName(selectedItem);
                FileRW.writeItems(Items);
                break;
            case 2:
                editItemQuantity(selectedItem);
                FileRW.writeItems(Items);
                break;
            case 3:
                editItemPrice(selectedItem);
                FileRW.writeItems(Items);
                break;
            case 4:
                editItemCOG(selectedItem);
                FileRW.writeItems(Items);
                break;
            case 5:
                editItemDescription(selectedItem);
                FileRW.writeItems(Items);
                break;
            case 6:
                removeItem(selectedItem);
                break;
        }
    }

    //Edits name of an item
    public static void editItemName(Item selectedItem) {
        Scanner inputScanner = new Scanner(System.in);
        String userStringInput = null;

        System.out.println("Enter the new name of " + selectedItem.getItemName());
        userStringInput = inputScanner.nextLine();
        selectedItem.setItemName(userStringInput);
        viewStock();
    }

    //Edits quantity of an item
    public static void editItemQuantity(Item selectedItem) {
        Scanner inputScanner = new Scanner(System.in);
        int userInput = 0;

        System.out.println("Enter the new quantity of " + selectedItem.getItemName());
        userInput = inputScanner.nextInt();
        selectedItem.setQuantity(userInput);
        viewStock();
    }

    //Edits price of an item
    public static void editItemPrice(Item selectedItem) {
        Scanner inputScanner = new Scanner(System.in);
        double userInput = 0;

        System.out.println("Enter the new price of " + selectedItem.getItemName());
        userInput = inputScanner.nextDouble();
        selectedItem.setListPrice(userInput);
        viewStock();
    }

    //Edits COG of an item
    public static void editItemCOG(Item selectedItem) {
        Scanner inputScanner = new Scanner(System.in);
        double userInput = 0;

        System.out.println("Enter the new COG of " + selectedItem.getItemName());
        userInput = inputScanner.nextDouble();
        selectedItem.setCOG(userInput);
        viewStock();
    }

    //Edits description of an item
    public static void editItemDescription(Item selectedItem) {
        Scanner inputScanner = new Scanner(System.in);
        String userInput = null;

        System.out.println("Enter the new description of " + selectedItem.getItemName());
        userInput = inputScanner.nextLine();
        selectedItem.setDescription(userInput);
        viewStock();
    }

    //Sets item quantity to 0, which removes it
    public static void removeItem(Item selectedItem) {
        selectedItem.setQuantity(0);
    }

    //Adds another item
    public static void addStock() {
        Scanner inputScanner = new Scanner(System.in);
        String newName = null;
        String newQuantity = null;
        int newQuantityInt = 0;
        String stringVersion = null;
        String newPrice = null;
        double newPriceDouble = 0;
        String newCOG = null;
        double newCOGDouble = 0;
        String newDescription = null;
        Item newItem = null;
        boolean validNewName = false;
        boolean validNewQuantity = false;
        boolean validNewPrice = false;
        boolean validNewCOG = false;
        boolean validNewDescription = false;
        boolean entryComplete = false;

        //While loop ensures that the entire entry is complete before the method ends
        //Resolves a bug we had earlier where the method would just exit without allowing the user to input item description
        while (!entryComplete) {
            //Validates item name (must be String)
            while (!validNewName) {
                System.out.println("Add the item name");
                newName = inputScanner.nextLine();

                if (!(newName instanceof String)) {
                    System.out.println("The name is invalid; try again!");
                } else {
                    validNewName = true;
                }
            }

            //Validates item quantity (must be int)
            while (!validNewQuantity) {
                System.out.println("Add the item quantity");
                newQuantity = inputScanner.nextLine();
                newQuantityInt = Integer.valueOf(newQuantity);

                if (!(newQuantityInt == (newQuantityInt))) {
                    System.out.println("The quantity is invalid; try again!");
                } else {
                    validNewQuantity = true;
                }
            }

            //Validates item price (must be double)
            while (!validNewPrice) {
                System.out.println("Add the item price");
                newPrice = inputScanner.nextLine();
                newPriceDouble = Double.valueOf(newPrice);
                stringVersion = String.valueOf(newPrice);

                if (stringVersion.matches("[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
                    System.out.println("The price is invalid; try again!");
                } else {
                    validNewPrice = true;
                }
            }

            //Validates item COG (must be double)
            while (!validNewCOG) {
                System.out.println("Add the item COG");
                newCOG = inputScanner.nextLine();
                newCOGDouble = Double.valueOf(newCOG);
                stringVersion = String.valueOf(newCOG);

                if (stringVersion.matches("[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?")) {
                    System.out.println("The COG is invalid; try again!");
                } else {
                    validNewCOG = true;
                }
            }

            //Validates item description (must be String)
            while (!validNewDescription) {
                System.out.println("Add the item description");
                newDescription = inputScanner.nextLine();

                if (newDescription == null) {
                    System.out.println("The description is invalid; try again!");
                } else {
                    validNewDescription = true;
                }
            }

            //Ensures that all of the new variables are valid inputs before creating a new Item
            if (validNewName && validNewQuantity && validNewPrice && validNewCOG && validNewDescription) {
                //Creates a new item
                newItem = new Item(newName, newQuantityInt, newPriceDouble, newCOGDouble, newDescription);
                //Adds to all the Items
                Items.add(newItem);
                //Writes to file to save
                FileRW.writeItems(Items);
                //Exits the while loop to exit the method!
                entryComplete = true;
            }
        }
    }

    //Shows the finances
    public static void viewFinances() {
        Scanner inputScanner = new Scanner(System.in);

        boolean validMenuInput = false;
        //Repeats until a valid menu input is given
        while (!validMenuInput) {
            System.out.println("[1] View Finance Data [2] Set Taxes [3] Exit");
            String userInput = inputScanner.nextLine();
            int userInputFinal = 0;

            //Try switching users input into an integer
            try {
                userInputFinal = Integer.valueOf(userInput);
            } catch (NumberFormatException e) {
                validMenuInput = false;
            }

            //Ensure user gives a number in the correct range
            if (userInputFinal > 0 && userInputFinal < 4) {
                switch (userInputFinal) {
                    case 1:
                        double profitMargin = ((Finances.getRevenue() - (Finances.getRevenue()-Finances.getProfit()))/Finances.getRevenue()) * 100;
                        String formattedProfitMargin = String.format("%.2f", profitMargin);

                        System.out.println("Store Finances:");
                        System.out.println("Store Revenue: $" + Finances.getRevenue());
                        System.out.println("Store Profit: $" + Finances.getProfit());
                        System.out.println("Unsold Inventory Cost of Goods: $" + Finances.getCOG());
                        System.out.println("Unsold Inventory Value: $" + Finances.getValue());

                        //If revenue is 0, output this so we don't get NaN
                        if(Finances.getRevenue() == 0){
                            System.out.println("Current profit margin: 0%");
                        }else{
                            System.out.println("Current profit margin: " + formattedProfitMargin + "%");
                        }

                        System.out.println("Current Tax Rate: " + Finances.getTax());
                        Item mostPopular = null;
                        int mostPopularValue = -1;
                       //Iterate through items to find most popular one
                        for (Item i: Items){
                            if(i.getNumSold() >= mostPopularValue){
                                mostPopularValue = i.getNumSold();
                                mostPopular = i;
                            }
                        }

                        System.out.println("Most popular item is " + mostPopular.getItemName() + " with " + mostPopularValue + " units sold.");
                        break;
                    case 2:
                        boolean validTax = false;
                        String taxString;

                        System.out.println("Store Finances:");
                        //Repeats until valid input is given
                        while (!validTax){
                            System.out.println("Enter the tax you'd like to set (Example: 0.13 for 13%): ");
                            taxString = inputScanner.nextLine();
                            double taxDouble = 0;

                            //Try to convert given string to a double
                            try {
                                taxDouble = Double.valueOf(taxString);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid tax input. Please try again.");
                                validTax = false;
                            }

                            //Only accept taxes if given in decimal form
                            if(taxDouble >= 0.00 && taxDouble <= 1.00) {
                                System.out.println("Setting tax to " + taxDouble * 100 + "%");
                                Finances.setTax(taxDouble);
                                FileRW.writeFinances(Finances.getRevenue(), Finances.getProfit(), Finances.getCOG(), Finances.getValue(), taxDouble);
                                validTax = true;
                            } else {
                                System.out.println("Invalid tax input. Please try again.");
                                validTax = false;
                            }
                        }
                        break;

                    case 3:
                        validMenuInput = true;
                        break;
                }
            }
        }
    }

    //Show all orders placed on store
    public static void viewOrders(){

        //If there are no orders, output this, else output orders
        if(Orders.isEmpty()){
            System.out.println("You currently have no orders.");
        }
        else{
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            int counter = 1;

            //Iterates through all orders in the store
            for(Order o: Orders){
                HashMap<Item, Integer> items = new HashMap<Item, Integer>();
                items = o.getItems();

                System.out.println("Order " + counter + ":");
                //Iterate through items in orders
                for(Item i: items.keySet()){
                    System.out.println("Item: " + i.getItemName() + " | Quantity: " + items.get(i) + " | Customer: " + o.getCustomer().getName());
                }
                counter++;
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    //Shows the stores customers
    public static void viewCustomers(){
        //If store has no customers, output this, else show customers
        if(Customers.isEmpty()){
            System.out.println("You currently have no customers.");
        }
        else{
            int counter = 1;
            //Iterate through each customer and output details
            for(Customer c: Customers){
                System.out.println("Customer #" + counter + ":");
                System.out.println("Customer Name: " + c.getName() + " | Email: " + c.getEmail() + " | Phone Number: " + c.getPhoneNumber()
                        + " | Address: " + c.getStreetName() + ", " + c.getCity() + ", " + c.getProvince() + ", " + c.getPostalCode()
                        + " | Credit Card Number: " + c.getCreditCardNumber()  + " | Credit Card Expiry: " + c.getCreditCardExpiry()
                        + " | Password: " + c.getPassword());
                counter++;
            }
        }
    }
}
