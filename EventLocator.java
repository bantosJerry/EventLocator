import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.text.DecimalFormat;
import java.math.RoundingMode; 



public class EventLocator {

	private static final int COORDINATE_LOW = -10; // Minimum event co-ordinate
	private static final int COORDINATE_HIGH = 10;	// Maximum event co-ordinate

	private static int numberOfEventLocations = 20; // Maximum number of event locations
	private static int ticketsPerEvent = 50; // Maximum number of tickets available for each event

	private static double lowestTicketPrice = 0.001; // Lowest ticket price
	private static double highestTicketPrice = 998.999;	// Highest ticket price

	private static DecimalFormat formatTicket; // Format ticket amount
	private static DecimalFormat formatEventIdentifier; // Format Event Identifier

	private static List<Double> eventLocationList; // List of event locations
	private static List<Double> manhatanDistanceList; // List of calculated manhatan distances
	private static List<Double> eventTicketList; // List of all event tickets
	private static List<Double> cheapestTicketList; // List of cheapest event ticket per location

	private static Integer[] eventIdentifier; // Array of event identifiers

	private static double[] cleanedUserInput; // Array of user input



	public static void main(String[] args) {
		
		formatEventIdentifier = new DecimalFormat("000");
		formatTicket = new DecimalFormat("000.00");
		formatTicket.setRoundingMode(RoundingMode.CEILING);	

		eventLocationList = new ArrayList<Double>();
		eventTicketList = new ArrayList<Double>();
		manhatanDistanceList = new ArrayList<Double>();
		cheapestTicketList = new ArrayList<Double>();

		eventIdentifier = new Integer[numberOfEventLocations];

		EventLocator eventLocator = new EventLocator();

		// Request for user location. The request repeats itself until the
		// user provides a valid pair of co-ordinates
		boolean userInputIsValid = eventLocator.getAndCleanUserInput();
		while (!userInputIsValid) {
			userInputIsValid = eventLocator.getAndCleanUserInput();
			if (userInputIsValid) {
				break;
			}
		}

		// If the user provides a valid pair of co-ordinates, the program runs 
		// and presents the result to the user
		if (userInputIsValid) {
			eventLocator.generateCoordinates();
			eventLocator.calculateManhatanDistance(cleanedUserInput[0], cleanedUserInput[1]);
			eventLocator.generateEventTickets();
			eventLocator.getCheapestTickets();
			eventLocator.sortDistanceArrayIndex();
			eventLocator.printResult();
		}

	}

	// The user input needs to be checked for consistency with expected input, 
	// if an invalid input is received, a relevant error message is presented
	// to the user
	private Boolean getAndCleanUserInput() {
		int eventLocationArrayLength = 2;
		cleanedUserInput = new double[eventLocationArrayLength];

		Scanner input = new Scanner(System.in);
		System.out.println();
		System.out.println("Please enter a pair of co-ordinates seperated by a comma: ");
		System.out.println();
		String userInputString = input.nextLine();
		System.out.println();
		userInputString = userInputString.replace(" ","");
		String[] userInputStringArray = userInputString.split(",");

		if (userInputStringArray.length == eventLocationArrayLength) {
			for (int i = 0; i < userInputStringArray.length; i++) {
				try {
					cleanedUserInput[i] = Double.parseDouble(userInputStringArray[i]);
					if ( (cleanedUserInput[i] < COORDINATE_LOW) || (cleanedUserInput[i] > COORDINATE_HIGH)) {
						System.out.println("Each co-ordinate should be between -10 and 10. Please try again");
						System.out.println();						
						return false;
					}
				} catch (NumberFormatException ex) {
					System.out.println("You have entered an invalid pair of co-ordinates. Please try again");
					System.out.println();
					return false;
				}
			}			
		} else {
			System.out.println("The co-ordinates you entered do not exist. Please try again");
			System.out.println();
			return false;
		}


		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Success!!! You have entered valid co-ordinates");
		System.out.println();
		System.out.println("Closest events to (" + userInputString + ")");
		System.out.println();
		return true;
	}

	// A random number generator is needed for the event locations and tickets
	private double getRandomData(double min, double max) {
		return ThreadLocalRandom.current().nextDouble(min, max + 1);
	}

	// Co-ordinates are generated and put in a list, the first half of the list 
	// contains x co-ordinates and the other half contains y co-ordinates
	private void generateCoordinates() {
		for (int i=0; i<numberOfEventLocations*2; i++) {
			eventLocationList.add(getRandomData(COORDINATE_LOW, COORDINATE_HIGH));
		}
	}

	// The distance between the user location and the various event locations are
	// calculated using the Manhatan distance algorithm
	private void calculateManhatanDistance(double X, double Y) {
		for(int i=0; i<numberOfEventLocations; i++) {
			double manhatanDistance = Math.abs(eventLocationList.get(i)-X) + 
			Math.abs(eventLocationList.get(i+numberOfEventLocations)-Y);
			manhatanDistanceList.add(manhatanDistance);
		}
	}

	// With the assumption that each event has equal number of tickets, event tickets
	// are generated and put in a list.
	private void generateEventTickets() {
		for (int i=0; i<numberOfEventLocations*ticketsPerEvent; i++) {
			eventTicketList.add(getRandomData(lowestTicketPrice, highestTicketPrice));
		}
	}

	// Cheapest tickets obtainable for each event is generated and put into a list
	private void getCheapestTickets() {
		for (int i=0; i<numberOfEventLocations*ticketsPerEvent; i = i + ticketsPerEvent) {
			cheapestTicketList.add(Collections.min(eventTicketList.subList(i, i + ticketsPerEvent)));
		}
	}

	// In order to get the closest events, the distance list has to be sorted by 
	// value and the respective indexes must be tracked
	private void sortDistanceArrayIndex() {
		for(int i = 0; i < numberOfEventLocations; i++) {
			eventIdentifier[i] = i;
		}
		Comparator<Integer> compareManhatanDistanceList = new CompareArrayIndex(manhatanDistanceList);
		Arrays.sort(eventIdentifier, compareManhatanDistanceList);
	}

	// A specified number of closest events are presented to the user.
	private void printResult() {
		int numberOfClosestEventsRequired = 5;
		for(int i = numberOfEventLocations-1; i >= numberOfEventLocations-numberOfClosestEventsRequired; i--) {
			System.out.println("Event " + formatEventIdentifier.format(eventIdentifier[i]+1) + 
				" - " + "$" + formatTicket.format(cheapestTicketList.get(eventIdentifier[i])) + 
				", Distance " + (int) Math.rint(manhatanDistanceList.get(eventIdentifier[i])));
		}
		System.out.println();
	}

	// This class is responsible for tracking the indexes of the Manhatan distance list.
	private class CompareArrayIndex implements Comparator<Integer> {

		private List<Double> arrayList;

		public CompareArrayIndex(List<Double> list) {
			arrayList = list;
		}

		public int compare(Integer i, Integer j) {
			return Double.compare(arrayList.get(j), arrayList.get(i));
		}
	}
}