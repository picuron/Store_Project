package store;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class MerchantView {
    private static ArrayList<Item> Items;

    public static void Run(){

        Items = StoreApplication.getItems();
//        System.out.println(CustomerView.getItems());

        Scanner inputScanner = new Scanner(System.in);
        boolean inProgress = true;
        while(inProgress) {
            boolean validMenuInput = false;
            while(!validMenuInput){
                System.out.println("[1] Stock [2] Finance [3] Orders (view only) [4] Analytics (view only) [5] Exit");
                String userInput = inputScanner.nextLine();

                int userInputFinal= 0;

                try{
                    userInputFinal = Integer.valueOf(userInput);
                }
                catch(NumberFormatException e){
                    validMenuInput = false;
                }

                if(userInputFinal>0 && userInputFinal<7){
                    switch (userInputFinal) {
                        case 1:
                            viewStock();
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;

                    }
                }
                else{
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    public static void viewStock(){
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Items in Stock:");
        for(Item i: Items){
            //Don't want to print out of stock items, so quantity must be greater than 0
            if(i.getQuantity() > 0){
                System.out.println("Item: " + i.getItemName() + " | Quantity: " + i.getQuantity() + " | Price: " + i.getListPrice() + " | COG: " + i.getCOG() + " | Description: " + i.getDescription());
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("[1] Edit stock [2] Exit");
        int userInput = inputScanner.nextInt();

        switch(userInput){
            case 1:
                changeStock();
                break;
            case 2:
                break;
        }
    }

    public static void changeStock(){
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("[1] Edit/delete a specific item [2] Add a new item [3] Exit");
        int userInput = inputScanner.nextInt();

        switch(userInput){
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

    public static void editStock(){
        Scanner inputScanner = new Scanner(System.in);
        int userInput = 0;
        String userStringInput = null;


        boolean validName = false;
        boolean validQuantity = false;
        Item selectedItem = null;

        while(!validName){
            System.out.println("Enter the name of the item you want to edit (case sensitive)");
            userStringInput = inputScanner.nextLine();

            for(Item i: Items){
                if (i.getItemName().equals(userStringInput)){
                    selectedItem = i;
                    validName = true;
                    break;
                }
            }
            if(validName == false){
                System.out.println("The provided item is invalid, please try again. Here are the items you can select:");
                viewStock();
            }
        }

        System.out.println("What do you want to edit? [1] Item name [2] Quantity [3] Price [4] COG [5] Description [6] Delete");
        userInput = inputScanner.nextInt();

        switch(userInput){
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

    public static void editItemName(Item selectedItem){
        Scanner inputScanner = new Scanner(System.in);
        int userInput = 0;
        String userStringInput = null;

        System.out.println("Enter the new name of " + selectedItem.getItemName());
        userStringInput = inputScanner.nextLine();
        selectedItem.setItemName(userStringInput);
        viewStock();
    }

    public static void editItemQuantity(Item selectedItem){
        Scanner inputScanner = new Scanner(System.in);
        int userInput = 0;

        System.out.println("Enter the new quantity of " + selectedItem.getItemName());
        userInput = inputScanner.nextInt();
        selectedItem.setQuantity(userInput);
        viewStock();
    }

    public static void editItemPrice(Item selectedItem){
        Scanner inputScanner = new Scanner(System.in);
        double userInput = 0;

        System.out.println("Enter the new price of " + selectedItem.getItemName());
        userInput = inputScanner.nextDouble();
        selectedItem.setListPrice(userInput);
        viewStock();
    }

    public static void editItemCOG(Item selectedItem){
        Scanner inputScanner = new Scanner(System.in);
        double userInput = 0;

        System.out.println("Enter the new COG of " + selectedItem.getItemName());
        userInput = inputScanner.nextDouble();
        selectedItem.setCOG(userInput);
        viewStock();
    }

    public static void editItemDescription(Item selectedItem){
        Scanner inputScanner = new Scanner(System.in);
        String userInput = null;

        System.out.println("Enter the new description of " + selectedItem.getItemName());
        userInput = inputScanner.nextLine();
        selectedItem.setDescription(userInput);
        viewStock();
    }

    public static void removeItem(Item selectedItem){
        selectedItem.setQuantity(0);
    }

    public static void addStock(){
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

        while (!entryComplete){
            while (!validNewName) {
                System.out.println("Add the item name");
                newName = inputScanner.nextLine();

                if(!(newName instanceof String)){
                    System.out.println("The name is invalid; try again!");
                } else {
                    validNewName = true;
                }
            }

            while (!validNewQuantity) {
                System.out.println("Add the item quantity");
                newQuantity = inputScanner.nextLine();
                newQuantityInt = Integer.valueOf(newQuantity);

                if(!(newQuantityInt == (newQuantityInt))){
                    System.out.println("The quantity is invalid; try again!");
                } else {
                    validNewQuantity = true;
                }
            }

            while (!validNewPrice) {
                System.out.println("Add the item price");
                newPrice = inputScanner.nextLine();
                newPriceDouble = Double.valueOf(newPrice);
                stringVersion = String.valueOf(newPrice);

                if(stringVersion.matches("[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?")){
                    System.out.println("The price is invalid; try again!");
                } else {
                    validNewPrice = true;
                }
            }

            while (!validNewCOG) {
                System.out.println("Add the item COG");
                newCOG = inputScanner.nextLine();
                newCOGDouble = Double.valueOf(newCOG);
                stringVersion = String.valueOf(newCOG);

                if(stringVersion.matches("[-+]?[0-9]*\\\\.?[0-9]+([eE][-+]?[0-9]+)?")){
                    System.out.println("The COG is invalid; try again!");
                } else {
                    validNewCOG = true;
                }
            }

            while (!validNewDescription) {
                System.out.println("Add the item description");
                newDescription = inputScanner.nextLine();

                if(newDescription == null){
                    System.out.println("The description is invalid; try again!");
                } else {
                    validNewDescription = true;
                }
            }

            
            if(validNewName && validNewQuantity && validNewPrice && validNewCOG && validNewDescription){
                newItem = new Item(newName, newQuantityInt, newPriceDouble, newCOGDouble, newDescription);
                Items.add(newItem);
                FileRW.writeItems(Items);
                entryComplete = true;
            }
        }
    }
}
