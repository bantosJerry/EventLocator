# EventLocator

HOW TO BUILD AND RUN PROGRAM 
 * Clone GitHub repository from a terminal (Requirements - Java installed on local machine)
   - Create a folder for the project (i.e. mkdir folderName)
   - Navigate to the created folder (i.e. cd folderName)
   - Clone the 'EventLocator' repository (i.e. git clone git@github.com:bantosJerry/EventLocator.git)
   - Compile the program (javac EventLocator.java)
   - Run program (i.e. java EventLocator)

 * Alternatively the 'EventLocator' repository can be download in a ZIP file and extracted. The project can then be compiled and run as above



ASSUMPTIONS MADE
 - The program should have at least five event locations since it has been hardcoded to produce results for five closest events.
 - The minimum and maximum values of the X and Y co-ordinates are -10 and 10 respectively
 - The X co-ordinate is entered first when inputing the pair of co-ordinates
 - Each co-rdinate can hold a maximum of one event
 - Each event has a unique identifier
 - Each event has an equal number of non-zero tickets
 - The ticket price for each event ranges from $0.001 to $998.999
 - The Manhattan distance is calculated as the distance between two points



HOW THE PROGRAM CAN BE CHANGED TO SUPPORT MULTIPLE EVENTS AT THE SAME LOCATION
 - The algorithm for the event identifiers would need to be modified.
 - Currently, the indexes of the Manhatan distances list are used as the event identifiers (i.e. 1,2,3....n where n is the total number of locations)
 - The algorithm for the event identifiers will need to be changed (i.e. to 11,12,13,14......21,22,23,24........nm where n is the total number of locations and m is the total number of events at a single location)



 HOW THE PROGRAM CAN BE CHANGED TO WORK WITH A MUCH LARGER WORLD SIZE
  * A larger world size will require more effective speed and memory management techniques
  	- By further reducing iterations through loops. This technique has already been applied to the 'getCheapestTickets()' function but it can be used more extensively in other parts of the program
  	- It could be made faster by using shift operators (i.e. x >> 2 can be used in place of x / 4)
  	- By applying code motion (i.e. in loops) where the code is moved so that it only executes when the result may change
  	- If memory becomes very critical, the program can be re-written in another language (i.e. C++) that allows extensive tuning of memory operations.



 USEFUL TIP
  - If the 'numberOfEventLocations' value is assigned to the 'numberOfClosestEventsRequired' variable, the program outputs the cheapest ticket price for all locations/events.