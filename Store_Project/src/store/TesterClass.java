package store;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class TesterClass {

    private static Order orderName;
    private static Order o1;
    private static Order customerOrder;
    private static ArrayList customerItems;
    private static ArrayList items;

    public TesterClass(Order orderName, Order o1, Order customerOrder, ArrayList customerItems, ArrayList items){
        TesterClass.orderName = orderName;
        TesterClass.o1 = o1;
        TesterClass.customerOrder = customerOrder;
        TesterClass.customerItems = customerItems;
        TesterClass.items = items;
    }

    public static void test() {
        Item i1 = new Item("Shirt", 3, 40, 15, "Soft, cotton");
        Item i2 = new Item("Hat", 10, 10, 5, "Baby blue, bucket-style");

        Item[] items = new Item[3];
        items[0] = i1;
        items[1] = i1;
        items[2] = i2;
        Order o1 = new Order(1, 2, items);

        double totalPrice = 0;
        for (Item i: items){
            //Add first item to hash map
            // If next item is last item, +1 value
            //If next item not in hashmap, make it a new key with value
        }

        //Iterate hash map, print key, value ... Item, Quantity
        System.out.println(totalPrice);

        ArrayList<Item> customerItems = new ArrayList<Item>();
        customerOrder.setOrderID(1);
        customerOrder.setCustomerID(1);

        System.out.println("Hello there! Welcome to our store. Here are the actions you can take:");
        menuOptions(o1);
    }

    public static void printItemNames(Order orderName){
        for (Item i: orderName.getItems()) {
            System.out.println("Item: " + i.getItemName() + " | Quantity: " + i.getQuantity() + " | Price: " + i.getListPrice() + " | Description: " + i.getDescription());
        }
    }

    public static void purchaseItem(Order orderName){
        Scanner userPurchase = new Scanner(System.in);
        System.out.println("What would you like to purchase?");
        String userPurchaseSet = userPurchase.nextLine();
        int x = 0;
        for (Item i: orderName.getItems()){
            x++;
            if (userPurchaseSet == i.getItemName()) {
                System.out.println("What quantity do you want?");
                String userQuantity = userPurchase.nextLine();
                int userQuantityFinal = Integer.valueOf(userQuantity);
                if (userQuantityFinal <= i.getQuantity()){
                    // updates the item's quantity
                    int remainingQuantity = i.getQuantity() - userQuantityFinal;
                    i.setQuantity(remainingQuantity);
                    // updates the customer's cart
                    addItem(x);
                    System.out.println("Successfully added to cart!");
                } else {
                    System.out.println("That's too much! Try again.");
                }

            }
        }
    }

    public static ArrayList getCart(){
        return customerItems;
    }

    public static ArrayList getItem(){
        return items;
    }

    public static Item getItemAtIndex(int index){
        ArrayList<Item> a = getItem();
        return (Item) a.get(index);
    }

    public static void addItem(int itemIndex){
        getCart().add(getItemAtIndex(itemIndex));
    }

    public static void menuOptions(Order orderName){
        Scanner myObj = new Scanner(System.in);
        boolean inProgress = true;
        while(inProgress) {
            System.out.println("[1] View items [2] Purchase an item [2] View cart [3] Checkout [4] Exit");
            String userInput = myObj.nextLine();
            int userInputFinal = Integer.valueOf(userInput);

            switch (userInputFinal) {
                case 1:
                    printItemNames(orderName);
                    break; // exit out of switch statement
                case 2:
                    purchaseItem(orderName);
                    break;
                case 4:
                    inProgress = false;
                    break;
            }
        }
    }
}



//        for (Item i: o1.getItems()){
//            System.out.println(i.getItemName());
//        }



        /*
        System.out.println(i1.getQuantity());
        System.out.println(Finances.getValue());
        System.out.println(Finances.getCOG());
        i1.reduceQuantity(1);
        System.out.println(Finances.getValue());
        System.out.println(Finances.getCOG());
        i1.increaseQuantity(3);
        System.out.println(Finances.getValue());
        System.out.println(Finances.getCOG());
         */

//        System.out.println(Finances.getValue());
//        System.out.println(Finances.getCOG());
//        i1.changeListPrice(50);
//        System.out.println(Finances.getValue() );
//        System.out.println(Finances.getCOG());


