/*
 * Aiden Redmond
 * 1260237
 * adnrdmnd@gmail.com
 * CSC 3280 (002) 
*/

public class MocMartSale {
    //class attributes
    private String firstName;
    private String lastName;
    private int numItemsOnList;
    private int[] itemsPurchased;
    private static int numSales;

    //constructor
    public MocMartSale(String firstName, String lastName, int numItemsOnList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numItemsOnList = numItemsOnList;
        numSales++;
    }

    //accessor methods
    public String getFirstName() {
        return firstName; }

    public String getLastName() {
        return lastName; }

    public int getNumItemsOnList() {
        return numItemsOnList; }

    public int[] getItemsPurchased() {
        return itemsPurchased; }

    public static int getNumSales() {
        return numSales; }

    //mutator methods
    public void setFirstName(String firstName) {
        this.firstName = firstName; }

    public void setLastName(String lastName) {
        this.lastName = lastName; }

    public void setNumItemsOnList(int numItemsOnList) {
        this.numItemsOnList = numItemsOnList; }

    public void setItemsPurchased (int[] itemsPurchased) {
        this.itemsPurchased = itemsPurchased; }

    public static void setNumSales (int numSalesInput) {
        numSales = numSalesInput; }
}