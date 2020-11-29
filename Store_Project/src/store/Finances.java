package store;

public class Finances {

    // Changes in revenue and profit not supported yet
    static double revenue;
    static double profit;
    static double COG;
    static double value;

    public static void addCOG(double addCOG){
        COG = COG + addCOG;
    }

    public static void reduceCOG(double reduceCOG){
        if (reduceCOG < COG) {
            COG = COG - reduceCOG;
        }
    }

    public static void addValue(double addValue){
        value = value + addValue;
    }

    public static void reduceValue(double reduceValue){
        if (reduceValue <= value) {
            value = value - reduceValue;
        }
    }

    public static double getRevenue() {
        return revenue;
    }

    public static double getProfit() {
        return profit;
    }

    public static double getCOG() {
        return COG;
    }

    public static double getValue() {
        return value;
    }
}