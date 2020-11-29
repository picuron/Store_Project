package store;

public class TesterClass {
    public static void test(){
        Item i1 = new Item("Shirt", 3, 40, 15, "Shirt Description");
        Item i2 = new Item("Hat", 10, 10, 5, "Hat Description");

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
        System.out.println(Finances.getValue());
        System.out.println(Finances.getCOG());
        i1.changeListPrice(50);
        System.out.println(Finances.getValue());
        System.out.println(Finances.getCOG());
    }
}
