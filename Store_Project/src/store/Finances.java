package store;

public class Finances {

    // Changes in revenue and profit not supported yet
    private static double revenue;
    private static double profit;
    private static double COG;
    private static double value;
    private static double tax;

    public static void setTax(double setTaxTo){
        tax = setTaxTo;
    }

    public static double getTax(){
        return tax;
    }

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

    public static void addRevenue(double addRevenue){
        revenue = revenue + addRevenue;
    }

    public static void reduceRevenue(double reduceRevenue){
        if (reduceRevenue <= revenue) {
            revenue = revenue - reduceRevenue;
        }
    }

    public static void addProfit(double addProfit){
        profit = profit + addProfit;
    }

    public static void reduceProfit(double reduceProfit){
        if (reduceProfit <= profit) {
            profit = profit - reduceProfit;
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
