/* 
 * Aiden Redmond
 * 1260237
 * adnrdmnd@gmail.com
 * CSC 3280 (002)
 * MocMart AKA program 1 AKA rust buster
 * started on tuesday, august 30. finished sunday the 4th. 20 hours of work in total. sheesh.
 * "I will practice academic and personal integrity and excellence of character and expect the same from others.”
 */

import java.util.Scanner; //import good ol' scanner
public class MocMart {
    public static void main(String[] args) {
        Scanner inputSc = new Scanner(System.in);
        StringBuilder finalOutput = new StringBuilder(500000); //string builder. this was the easiest way to export text for the output

        int maxProducts = inputSc.nextInt(); //amount for products array
        int maxSales = inputSc.nextInt(); //amount for sales array
        String currentLine = inputSc.nextLine(); //for input
        int salesIndex = 0; //keep track of where to input on the sales array. this is only used in customer
        int countForAddItem = 0; //this is an error prevention to prevent the MocMartProduct.numProducts from getting all wack

        //creating 2 arrays of references using ^^ for length
        MocMartProduct[] products = new MocMartProduct[maxProducts];
        MocMartSale[] sales = new MocMartSale[maxSales];

        //while QUIT is not the command, loop to grab a line of text, decipher it, and then do the said method asked of us. I decided to make each "command" a method below the main method. I think it makes the code infintely more readable.
        while (!(currentLine.equals("QUIT"))) {
            //switch case to test what method needs to be done, and then to execute that method.
            currentLine = inputSc.nextLine();
            String testString = currentLine.substring(0,1); //testing just the first letter to decide which method to execute. this was awesome when i was typing late at night it didnt matter if i made typos.
            switch (testString) {
                case "A": 
                    //split the currentLine into readable data, 1 == itemNum, 2 == itemName, 3 == itemPrice, 4 == stockQty, 5 == restockQty
                    String[] dataArray = currentLine.split(" ");
                    //this adds an item to product at the place where "ADDITEM" deems is the correct location
                    MocMart.ADDITEM(dataArray, products, countForAddItem, finalOutput);
                    break;

                case "F":
                    MocMart.FINDITEM(products, currentLine,finalOutput);
                    break;

                case "R":
                    MocMart.RESTOCK(products, finalOutput);
                    break;

                case "C":
                    //split the currentLine into readable data, 1 == firstName, 2 == lastName, 3 == num, 4 to length-1 == itemNums & quantity
                    String[] dataValues = currentLine.split(" ");
                    MocMart.CUSTOMER(dataValues, sales, products,salesIndex,finalOutput);
                    salesIndex++;
                    break;

                case "I":
                    MocMart.INVENTORY(products, sales, finalOutput);
                    break;

                case "P":
                    MocMart.PRINTSUMMARY(products, sales, finalOutput);
                    break;

            }
        }
        
        finalOutput.trimToSize(); //trim down the size of the final string to avoid any errors
        inputSc.close(); //always close your inputs kids!! also no drugs... BUT more importantly, close your inputs. what are you gonna do? just leave them open? I mean, who's gonna tell them they are done? they will forever be waiting for you the programmer to close them, almost equivalent to leaving them on read, i mean seriously it's not cool.
        //final outputs
        finalOutput.append("Goodbye.");
        System.out.print(finalOutput);
    }


    //the methods.
    public static void ADDITEM(String[] dataArray, MocMartProduct[] products, int countForAddItem, StringBuilder finalOutput) {
        //nullLocation and insertion variables used later in the swapping algorithm
        int nullLocation = -1;
        int insertion = -1;

        //find the first null location in the products arry
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) {
                nullLocation = i;
                break;
            }
        }

        //if the first item in the array is 0, just insert it there. also break out of the code when this is done. had fun times trying to figure out what was wrong with this one.
        if (nullLocation == 0) {
            products[0] = new MocMartProduct (Integer.parseInt(dataArray[1]), dataArray[2], Double.parseDouble(dataArray[3]), Integer.parseInt(dataArray[4]), Integer.parseInt(dataArray[5]));
            finalOutput.append("ADDITEM:\n\tItem " + products[0].getItemNum() + ", " + products[0].getItemName() + ", with a cost of $" + products[0].getItemPrice() + " and initial stock of " + products[0].getQuantity() + ", has been added to the product database.\n\n");
            return;
        }
        else {
            //loop through the array to find the insertion point (once we find an itemNum that is greater than the inputted one, insert it there. or if you find that the item to the right of the item you are checking is null, insert in that null) 
            for (int i = 0; i < nullLocation; i++) {
                if (products[i].getItemNum() > Integer.parseInt(dataArray[1])) { 
                    insertion = i;
                    break;
                }
                else if (products[i+1] == null) {
                    insertion = i+1;
                    break;
                }
            }
        }
        
        //swap elements all the way until the closest null is at the insertion point
        while (insertion != nullLocation) {
            MocMartProduct temp = products[nullLocation-1];
            products[nullLocation-1] = products[nullLocation];
            products[nullLocation] = temp;
            nullLocation--;
        }
        
        //inserts the new product at the newly swapped "null"
        products[insertion] = new MocMartProduct(Integer.parseInt(dataArray[1]), dataArray[2], Double.parseDouble(dataArray[3]), Integer.parseInt(dataArray[4]), Integer.parseInt(dataArray[5]));
        String tempString = String.format("ADDITEM:\n\tItem " + products[insertion].getItemNum() + ", " + products[insertion].getItemName() + ", with a cost of $%.2f and initial stock of " + products[insertion].getQuantity() + ", has been added to the product database.\n\n", products[insertion].getItemPrice());
        finalOutput.append(tempString);
    } 
   
    public static void FINDITEM (MocMartProduct[] products, String currentLine, StringBuilder finalOutput) {
        int key = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the number from the command
        binarySearchWithIndices(products, key, finalOutput); //run binary search on products, searching for key, then print out information.
        finalOutput.append("\n");
    }
   
    public static void RESTOCK(MocMartProduct[] products, StringBuilder finalOutput) {
        int indexCount = 0;
        MocMartProduct[] itemsRestocked = new MocMartProduct[MocMartProduct.getNumProducts()];

         //loop through all items. if the stock is 0, restock, and add to a reference list for printing purposes after.
        for (int i = 0; i < MocMartProduct.getNumProducts(); i++) {
            if (products[i].getQuantity() == 0) {
                products[i].setQuantity(products[i].getRestockQuantity());
                itemsRestocked[indexCount] = products[i]; //FOR PRINTING PURPOSES
                indexCount++;
            }
        }
        
        if (indexCount == 0) {
            finalOutput.append("RESTOCK:\n\tThere are no items to restock.\n\n");
            return;
        }

        finalOutput.append("RESTOCK:\n");
        for (int i = 0; i < indexCount; i++) {
            finalOutput.append("\tItem " + itemsRestocked[i].getItemNum() + ", " + itemsRestocked[i].getItemName() + ", restocked to a quantity of " + itemsRestocked[i].getRestockQuantity() + ".\n");
        }
        finalOutput.append("\n");
    }
    
    public static void CUSTOMER(String[] dataValues, MocMartSale[] sales, MocMartProduct[] products, int salesIndex, StringBuilder finalOutput) {
        int numOfElements = Integer.parseInt(dataValues[3]); //get the number of elements we are processing
        sales[salesIndex] = new MocMartSale(dataValues[1], dataValues[2], numOfElements); //create a new sale object

        int purchasedAnything = 0;

        int[] itemNumsAndQuantities = new int[numOfElements]; //creating to a new int array so i dont have to keep seeing "Integer.parseInt(x)" everywhere...
        int[] emptyArrayTT = new int[numOfElements]; //empty array that will eventually get set to the itemsPurchased list for sales[salesIndex]
        int EATTIndex = 0; 
        //adding those elements from dataValues into this new int array
        for (int i = 0; i < numOfElements; i++) {
            itemNumsAndQuantities[i] = Integer.parseInt(dataValues[i+4]);
        }

        //loop through INAQ, check every even number. check if that product is in stock. check how many we can buy. buy the max amount.
        for (int i = 0; i < numOfElements; i = i + 2) {
            int location = binarySearch(products, itemNumsAndQuantities[i]); //get the location of the item we are looking for in the product array 
            if (location == -1) {
                //item was not found. add to EATT list with "0" purchased. add the itemNum first, and then 0 on the next index.
                emptyArrayTT[EATTIndex] = itemNumsAndQuantities[i]; //set to itemNum
                emptyArrayTT[EATTIndex+1] = 0; //set the next indice to 0, since there are no products.
                EATTIndex += 2;
            }
            else {
                //item was found. figure out how much quantity can be bought from that specific item
                int maxAmountPurchasable = products[location].getQuantity(); //maxAmountPurchaseable
                if (products[location].getQuantity() == 0) {
                    
                }
                else if (itemNumsAndQuantities[i+1] >= maxAmountPurchasable) { //if the buyer is requesting more than you can buy, make the amount they want to buy maxAmountPurchasable.
                    emptyArrayTT[EATTIndex] = itemNumsAndQuantities[i]; //add itemNum to the EATT
                    emptyArrayTT[EATTIndex+1] = maxAmountPurchasable; //add the maxAmountPurchaseable to the EATT
                    EATTIndex += 2;
                    products[location].setQuantity(products[location].getQuantity()-maxAmountPurchasable); //set quantity to original - amount purchased
                    products[location].updateUnitsSold(maxAmountPurchasable); //update the amount of items we have sold of this specific product. used for finditem later down the line.
                    products[location].updateTotalRevenue(maxAmountPurchasable); //update the total revenue we have made selling this specific product. used for finditem later down the line.
                    purchasedAnything++;
                }
                else { //if the amount they want to purchase is less than the product Quantity, just do it regular
                    emptyArrayTT[EATTIndex] = itemNumsAndQuantities[i]; //add itemNum to the EATT
                    emptyArrayTT[EATTIndex+1] = itemNumsAndQuantities[i+1]; //add the next index to the final push list
                    EATTIndex += 2;
                    products[location].setQuantity(products[location].getQuantity()-itemNumsAndQuantities[i+1]);
                    products[location].updateUnitsSold(itemNumsAndQuantities[i+1]); //update the amount of items we have sold of this specific product. used for finditem later down the line.
                    products[location].updateTotalRevenue(itemNumsAndQuantities[i+1]); //update the total revenue we have made selling this specific product. used for finditem later down the line.
                    purchasedAnything++;
                }
            }
        }

        sales[salesIndex].setItemsPurchased(emptyArrayTT);

        //testing if they purchased anything
        if (purchasedAnything > 0) {
            finalOutput.append("CUSTOMER:\n\tCustomer " + dataValues[1] + " " + dataValues[2] + " came and made some purchases.\n\n");
        }
        else {
            finalOutput.append("CUSTOMER:\n\tCustomer " + dataValues[1] + " " + dataValues[2] + " came and made no purchases.\n\n");
        }
    }
    
    public static void INVENTORY(MocMartProduct[] products, MocMartSale[] sales, StringBuilder finalOutput) {
        int indexOfItem = -1;
        String formatString = "";
        //if a product was added, loop over every product obkect and get information about it
        if (MocMartProduct.getNumProducts() > 0) { 
            finalOutput.append("INVENTORY:\n\tContains the following items:\n");
            for (int i = 0; i < MocMartProduct.getNumProducts(); i++) {
                indexOfItem = binarySearch(products, products[i].getItemNum());
                formatString = String.format("\t| Item %6d | %-20s | $%7.2f | %4d unit(s) |%n", products[indexOfItem].getItemNum(), products[indexOfItem].getItemName(), products[indexOfItem].getItemPrice(), products[indexOfItem].getQuantity());
                finalOutput.append(formatString);
            }
        finalOutput.append("\n");
        }
        //if there are no products, just print out this message. ez.
        else {
            finalOutput.append("INVENTORY:\n\tContains no items.\n\n");
        }
    }
    
    public static void PRINTSUMMARY(MocMartProduct[] products, MocMartSale[] sales, StringBuilder finalOutput) {
        //variables for tracking cost and formatting strings
        String tempFormat = "";
        double totalCost = 0.00;
        double overallCost = 0.00;
        int count = 1;

        //header stuff
        finalOutput.append("PRINTSALESSUMMARY:\nMoc Mart Sales Report:\n");
        for (int i = 0; i < MocMartSale.getNumSales(); i++) { //loop over every item in the sales array
            int amountOfSales = 0; //keep track of the amount of sales we have (for printing)
            int[] tempNumArray = sales[i].getItemsPurchased(); //get the numbers that the customer purchased
            for (int k = 0; k < tempNumArray.length; k = k + 2) { //calculate the amount of items said customer purchased
                amountOfSales += tempNumArray[k+1];
            }
            if (amountOfSales != 0) {
                finalOutput.append("\tSale #" + count + ", " + sales[i].getFirstName() + " " + sales[i].getLastName() + " purchased " + amountOfSales + " item(s):\n"); //initial header for each customer.
                //loop over every item num, find information about it, and add it to the final output
                for (int j = 0; j < tempNumArray.length; j = j + 2) {
                    int index = binarySearch(products, tempNumArray[j]);
                    if (tempNumArray[j+1] != 0) {
                        tempFormat = String.format("\t\t| Item %6d | %-20s | $%7.2f (x%4d) |%n", products[index].getItemNum(), products[index].getItemName(), products[index].getItemPrice(), tempNumArray[j+1]);
                        finalOutput.append(tempFormat);
                        totalCost += products[index].getItemPrice() * tempNumArray[j+1]; //get the cost 
                    }
                }
                count++;
                tempFormat = String.format("\t\tTotal: $%.2f\n",totalCost);
                finalOutput.append(tempFormat);
                overallCost += totalCost;
                totalCost = 0.00;
            }
        }
        //final grand total line
        String testString = String.format("\tGrand Total: $%.2f\n\n", overallCost);
        finalOutput.append(testString);
    }
    
    public static void binarySearchWithIndices(MocMartProduct[] products, int value, StringBuilder finalOutput) {
        //declare starting vars for algorithm
        String tempString = "";
        String indexChecked = "";    
        int finalIndex = -1;
        int low = 0;
        int high = MocMartProduct.getNumProducts() - 1;
        int mid = 0;
        boolean foundIndex = false;

        //if there are no products made, return error message
        if (high == -1) {
            finalOutput.append("FINDITEM:\n\tCannot perform command; there are no items in the product database.\n");
            return;
        }
        
        // run until you have found the element or you have searched every item 
        while (low <= high) {
            mid = (low + high) / 2;
            indexChecked += mid + " "; //for printing purposes
            
            if (products[mid].getItemNum() == value) {
                // found the key. update stuff and break.
                finalIndex = mid;
                foundIndex = true;
                break;
            }
            // if mid is greater than the value we are searching for
            else if (products[mid].getItemNum() > value) {
                high = mid - 1; 
            }
            
            //if the mid index is lower than the value
            else {
                low = mid + 1;
            }
        }
        if (!foundIndex) { //if we did not find the item, print an error message
            finalOutput.append("FINDITEM:\n\tPerforming Binary Search...(Indices viewed: " + indexChecked + ")\n\tItem #" + value + " was not found in the product database.\n");
        }
        else { //we found the item. normal printing shennanigans
            tempString = String.format("FINDITEM:\n\tPerforming Binary Search...(Indices viewed: " + indexChecked + ")" + "\n\tItem #" + products[finalIndex].getItemNum() + " (" + products[finalIndex].getItemName() + ")\n\tPrice            :  $%.2f\n\tCurrent Quantity :  " + products[finalIndex].getQuantity() + "\n\tUnits Sold       :  " +  products[finalIndex].getUnitsSold() + "\n\tTotal Amount     :  $%.2f\n",products[finalIndex].getItemPrice(), products[finalIndex].getTotalRevenue());
            finalOutput.append(tempString);
        }
    }
    
    public static int binarySearch (MocMartProduct[] products, int value) {
        //binary search without the shitty string of indices searched
        int high = MocMartProduct.getNumProducts() - 1; 
        int mid = 0;
        int low = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            if (products[mid].getItemNum() == value) {
                return mid; //if found return that index
            }
            else if (products[mid].getItemNum() > value) {
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }
        return -1; //if not found return -1
    }
}

//this was a doozey. but looking back, i oddly enjoyed sitting down and coding every day for 5 hours. it was super tiring on top of running, but i'm defintely glad i chose this major.