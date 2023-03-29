import java.util.ArrayList;

/** Collection class to hold a list of housemates
 */

public class OrdersList  {
    private ArrayList<Order> tList;
    public final int MAX = 0;
        
    /** Constructor initialises the empty order list and sets the maximum list size 
     *  @param   maxIn The maximum number of orders in the list
     */
    // public OrdersList(int maxIn) {
    //     tList = new ArrayList<>();
    //     MAX = maxIn;
    // }
	
    public OrdersList() {
        tList = new ArrayList<>();
    }

    /** Adds a new housemate to the list
     *  @param  tIn The order to add
     *  @return Returns true if the order was added successfully and false otherwise
     */
    // public boolean add(Order tIn) {
    //     if(!isFull()) {
    //         tList.add(tIn);
    //         return true;
    //     } 
    //     return false;
    // }
    public void add(Order tIn) {
        tList.add(tIn);
    }
        
    // Complete order

    /** Removes the housemate in the given room number
     *  @param roomIn The room number to of the housemate to remove
     *  @return Returns true if the housemate is removed successfully or false otherwise
     */
    // public boolean completeOrder(int roomIn) {
    //     Housemate findT = search(roomIn); // call search method
    //     if (findT != null) { // check housemate is found at given room
    //         tList.remove(findT); // remove given housemate
    //         return true;
    //     }
    //     return false; // no housemate in given room
        
    // }       
       
    /** Searches for the housemate in the given room number
     *  @param roomIn The room number to search for
     *  @return Returns the housemate in the given room or null if no housemate in the given room
     */
    // public Housemate search(int roomIn) {
    //     for(Housemate currenthousemate: tList) {  
    //         // find housemate with given room number
    //         if(currenthousemate.getRoom() == roomIn) {
    //             return currenthousemate;
    //         }
    //     }
    //     return null; // no housemate found with given room number
    // }
        
    /** Reads the housemate at the given position in the list
     *  @param      positionIn The logical position of the housemate in the list
     *  @return     Returns the housemate at the given logical position in the list
     *              or null if no housemate at that logical position
     */
    // public Housemate getHousemate(int positionIn)
    // {
    //     if (positionIn<1 || positionIn>getTotal()) {// check for valid position
    //         return null; // no object found at given position
    //     }
    //     else {
    //         // remove one frm logical poition to get ArrayList position
    //         return tList.get(positionIn -1);
    //     }
    // }
 

	/** Reports on whether or not the list is empty
     *  @return Returns true if the list is empty and false otherwise
     */
    public boolean isEmpty() {
        return tList.isEmpty();
    }
	
    /** Reports on whether or not the list is full
     *  @return Returns true if the list is full and false otherwise
     */	
    public boolean isFull() {
        return tList.size()== MAX;
    }
        
    /** Gets the total number of housemates 
     *  @return Returns the total number of housemates currently in the list 
     */
    public int getTotal() {       
        return tList.size();
    }

    public ArrayList<Order> getList() {
        return tList;
    }
        
    @Override
    public String toString() {
        return tList.toString();
    }
}

