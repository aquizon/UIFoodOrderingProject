/** Class used to record the details of a housemate
 */
import java.time.*;

public class Order {
	private int id;
	private String orderedby;
	private String driver;
      private String restaurant;
      private static int currId = 0;
      private String timeStart; 
      private String timeCompleted;
	
      /** Constructor initialises the name and room number of the housemate
	*  and sets the payments made to the empty list
  	*  @param  ordered_byIn: name of person who ordered
  	*  @param  driverIn: name of driver
      *  @param restaurantIn: name of restaurant ordered from 
	*/
	public Order(String ordered_byIn, String driverIn, String restaurantIn) {
            currId++;
            id = currId;
            orderedby = ordered_byIn;
            driver = driverIn;
            restaurant = restaurantIn;
            timeStart = getTimeStr();
	}

      private String getTimeStr() {
            LocalDateTime currTime = LocalDateTime.now();
            String am_pm;
            int int_hr = currTime.getHour();
            if (int_hr >= 12) {
                  int_hr -= 12;
                  am_pm = "PM";
            }
            else {
                  am_pm = "AM";
            }
            String hours = numToStr(int_hr);
            String mins = numToStr(currTime.getMinute());
            String secs = numToStr(currTime.getSecond());
            return hours + ":" + mins + ":" + secs + " " + am_pm;
      }

      private String numToStr(int number) {
            if (number < 10) {
                  return "0"+number;
            }
            return Integer.toString(number);
      }

      public void complete() {
            timeCompleted = getTimeStr();
      }
        
      /** Reads the id of the order
       *  @return Returns the id of the order
       */
      public int getId() {
            return id;
      }

      /** Reads the name of person who ordered
       *  @return Returns the name of person who ordered
       */
      public String getOrderedby() {
            return orderedby;
      }
      
      /** Reads the name of the driver
       *  @return Returns the name of the driver
       */
      public String getDriver() {
            return driver;
      }

      /** Reads the name of the restaurant
       *  @return Returns the name of the restaurant
       */
      public String getRestaurant() {
            return restaurant;
      }

      public String getTimeStart() {
            return timeStart;
      }
      
      public String getTimeCompleted() {
            return timeCompleted;
      }

      @Override
      public String toString() {
            return "Order #" + id + ": " + orderedby + ", " + driver + ", " + restaurant;
      }
}
