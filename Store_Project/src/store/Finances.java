package store;

import java.io.Serializable;

public class Finances implements Serializable {

    private static double revenue;
    private static double profit;
    private static double COG;
    private static double value;
    private static double tax;

    public static void setRevenue(double revenue){
        Finances.revenue = revenue;
    }

    public static void setProfit(double profit) {
        Finances.profit = profit;
    }

    public static void setCOG(double COG) {
        Finances.COG = COG;
    }

    public static void setValue(double value) {
        Finances.value = value;
    }

    public static void setTax(double tax){
        Finances.tax = tax;
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
