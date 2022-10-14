/*
 * Aiden Redmond
 * 1260237
 * adnrdmnd@gmail.com
 * CSC 3280 (002) 
*/

public class MocMartProduct {
    //class attributes
    private int itemNum;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private int restockQuantity;
    private int unitsSold;
    private double totalRevenue;
    private static int numProducts = 0;

    //constructor
    public MocMartProduct (int itemNum, String itemName, double unitPrice, int stockQty, int restockQty) {
        this.itemNum = itemNum;
        this.itemName = itemName;
        itemPrice = unitPrice;
        quantity = stockQty;
        restockQuantity = restockQty;
        numProducts = numProducts + 1;
    }

    //accessor methods
    public int getItemNum() {
        return itemNum; }

    public String getItemName() {
        return itemName; }

    public double getItemPrice() {
        return itemPrice; }

    public int getQuantity() {
        return quantity; }

    public int getRestockQuantity() {
        return restockQuantity; }

    public int getUnitsSold() {
        return unitsSold; }
    
    public double getTotalRevenue() {
        return totalRevenue; }

    public static int getNumProducts() {
        return numProducts; }

    //mutator methods
    public void setItemNum(int itemNum) {
        this.itemNum = itemNum; }

    public void setItemName(String itemName) {
        this.itemName = itemName; }

    public void setItemPrice (double itemPrice) {
        this.itemPrice = itemPrice; }

    public void setQuantity (int quantity) {
        this.quantity = quantity; }

    public void setRestockQuantity (int restockQuantity) {
        this.restockQuantity = restockQuantity; }

    public static void setNumProducts (int numProductsInput) {
        numProducts = numProductsInput; }
    
    //other methods to make my life much much easier
    public void updateUnitsSold (int amount) {
        unitsSold += amount;
    }
    public void updateTotalRevenue (double amount) {
        totalRevenue += (amount * itemPrice);
    }
}
