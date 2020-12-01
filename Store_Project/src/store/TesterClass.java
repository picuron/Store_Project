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

        Item[] items = new Item[2];
        items[0] = i1;
        items[1] = i2;
        Order o1 = new Order(1,2, items);
        System.out.println(o1.getItems());

        for (Item i: o1.getItems()){
            System.out.println(i.getItemName());
        }
    }
}
