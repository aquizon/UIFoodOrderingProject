/** Class used to record the details of a housemate
 */
public class Restaurant {
	private int id;
	private String name;
    private static int currId = 0;
	
      /** Constructor initialises the name and room number of the housemate
	*  and sets the payments made to the empty list
  	*  @param  first: first name of driver
  	*  @param  last: last name of driver
	*/
	public Restaurant(String nameIn) {
        currId++;
        id = currId;
        name = nameIn;
	}
    
    /** Reads the id of the order
     *  @return Returns the id of the restaurant
     */
    public int getId() {
        return id;
    }

    /** Reads the name of the restaurant
     *  @return Returns the name of the restaurant
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}
