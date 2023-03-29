/** Class used to record the details of a housemate
 */
public class Deliverer {
	private int id;
	private String firstName;
	private String lastName;
    private String fullName;
    private static int currId = 0;
	
      /** Constructor initialises the name and room number of the housemate
	*  and sets the payments made to the empty list
  	*  @param  first: first name of driver
  	*  @param  last: last name of driver
	*/
	public Deliverer(String first, String last) {
        currId++;
        id = currId;
        firstName = first;
        lastName = last;
        fullName = first + " " + last;
	}
    
    /** Reads the id of the order
     *  @return Returns the id of the driver
     */
    public int getId() {
        return id;
    }

    /** Reads the first name of the driver
     *  @return Returns the first name of the driver
     */
    public String getFirst() {
        return firstName;
    }
    
    /** Reads the last name of the driver
     *  @return Returns the last name of the driver
     */
    public String getLast() {
        return lastName;
    }

    /** Reads the full name of the driver
     *  @return Returns the full name of the driver
     */
    public String getFull() {
        return fullName;
    }

    @Override
    public String toString() {
        return id + ": " + fullName;
    }
}
