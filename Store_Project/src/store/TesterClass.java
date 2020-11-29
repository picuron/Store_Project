package store;

public class TesterClass {
    public static void test(){
        Item i1 = new Item("Shirt", 3, 39.99, 14.99, "Shirt Description");

        System.out.println(i1.getQuantity());
        i1.reduceQuantity(5);
        i1.reduceQuantity(1);
        System.out.println(i1.getQuantity());
    }
}
