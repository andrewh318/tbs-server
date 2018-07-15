package tbs.server;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Performance {
	private String _performanceID;
	private String _startTime;
	private String _premiumPrice;
	private String _cheapPrice;
	
	private Theatre _theatre;
	
	private int[][] _seatsAvailable;
	private List<Ticket> _tickets; 
	
	// These patterns are used by methods checkTime and checkPrice to check format of parameters
	private static final Pattern DATE_FORMAT = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");
	private static final Pattern PRICE_FORMAT = Pattern.compile("\\$(\\d+)");
	

	
	public Performance(String performanceID, Theatre theatre, String startTime, String premiumPrice, String cheapPrice) 
			throws DateFormatException, PriceFormatException  {
		
		// If there is an issue with the formatting with startTime or the prices, an exception is thrown
		// The performance object is not created
		if (!(checkTime(startTime))) {
			throw new DateFormatException();
		} else if (!(checkPrice(premiumPrice, cheapPrice))) {
			throw new PriceFormatException();
		}
		
		_performanceID = performanceID;
		_startTime = startTime;
		_premiumPrice = premiumPrice;
		_cheapPrice = cheapPrice;
		
		_theatre = theatre;

		_tickets = new ArrayList<Ticket>();
		
		/* 
		 * Create a 2D array that will determine if a seat is available or not 
		 * Index array using Row Number and Seat Number
		 * Need to minus 1 from Row Number and Seat Number as the first seat in the first row corresponds to index  [0][0]
		 * E.g _seatsAvailable[rowNumber-1][seatNumber-1] 
		 * If the element in this position is equal to 1 then the seat is available otherwise it is already reserved
		 */
		int seatDimension = theatre.getSeatDimension();
		_seatsAvailable = new int[seatDimension][seatDimension];
		// Initially all seats are available so set all elements to 1
		for (int i = 0; i < seatDimension; i++) {
			for (int j = 0; j < seatDimension; j++) {
				_seatsAvailable[i][j] = 1;
			}
		}
	}
	
	public String getPerformanceID() {
		return _performanceID;
	}
	
	public Theatre getTheatre() {
		return _theatre;
	}
		
	public String getStartTime() {
		return _startTime;
	}
	
	public String getNumberOfTicketsSold() {
		// Return the number of ticket objects in list which corresponds to number of tickets sold
		String ticketsSold = Integer.toString(_tickets.size());
		return ticketsSold;
	}
	
	public void addTicket(Ticket ticket) {
		_tickets.add(ticket);
	}
	
	public List<String> getTicketIDs(){
		List<String> ticketIDs = new ArrayList<String>();
		// Do one pass through list of ticket objects and add their IDs into ticketIDs list
		for (Ticket ticket : _tickets) {
			ticketIDs.add(ticket.getTicketID());
		}
		Collections.sort(ticketIDs);
		return ticketIDs;
	}
	
	public void reserveSeat(int rowNumber, int seatNumber) {
		// Change the element corresponding to the seat in the 2D array from 1 to 0 which represents reserving the seat
		_seatsAvailable[rowNumber-1][seatNumber-1] = 0;
	}

	public boolean checkSeatAvailability(int rowNumber, int seatNumber) {
		/* 
		 * Use rowNumber and seatNumber to index 2D array to check if seat is available
		 * Note that array starts from 0 so need to -1 to check correct seat 
		 * E.g The first seat in row one corresponds to index [0][0] in the array
		 */
		if (_seatsAvailable[rowNumber-1][seatNumber-1] == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkSeatExists(int rowNumber, int seatNumber) {
		int seatDimension = _theatre.getSeatDimension();
		boolean flag = true;
		// Check if row of seat is valid
		if (rowNumber < 1 || rowNumber > seatDimension) {
			flag = false;
		} 
		// Check if seat number in row is valid 
		if (seatNumber < 1 || seatNumber > seatDimension) {
			flag = false;
		}
		return flag;
	}

	public List<String> getSeatsAvailable() {
		
		int seatDimension = _theatre.getSeatDimension();
		List<String> seatsAvailable = new ArrayList<String>();
		
		// Do one pass through the 2D array representing the seats in the theatre
		for (int i = 1; i <= seatDimension;i++) {
			for (int j = 1; j <= seatDimension; j++) {
				// If the element being currently indexed is equal to 1 then create a String by concatenating 
				// row and seat number
				if (_seatsAvailable[i-1][j-1] == 1) {
					String seat = Integer.toString(i) + "\t" + Integer.toString(j); 
					seatsAvailable.add(seat); // Add String to list of seats available
				} 
			}
		}
		return seatsAvailable;
	}
	
	public String getSalesReceipt() {
		int seatDimension = _theatre.getSeatDimension();
		/*
		 *  Find the last row of premium seats
		 *  E.g If there are 5 rows in total, then 5/2 = 2 
		 *  Rows 1 and 2 will be premium and any row after contain the cheap seats
		 */
		int seatBoundary = seatDimension/2;
		int totalCost = 0;
		// Remove $ sign and create integer representation of premium and cheap seat price
		int premPrice = Integer.parseInt(_premiumPrice.replace("$", "").trim());
		int cheapPrice = Integer.parseInt(_cheapPrice.replace("$", "").trim());
		
		for (Ticket ticket : _tickets) {
			// Get the row number associated with each ticket
			int rowNumber = ticket.getRowNumber();
			// If the row number is less or equal to the seat boundary it is a premium seat
			if (rowNumber <= seatBoundary) {
				totalCost = totalCost + premPrice;
			// If the row number is greater than the seat boundary it is a cheap seat
			} else if (rowNumber > seatBoundary) {
				totalCost = totalCost + cheapPrice;
			}
		}
		// Return the total price in string form including a $ sign
		return "$" + Integer.toString(totalCost);
	}
	
	public static boolean checkTime(String startTime) {
		// Check if date is in the correct format by comparing it to regex pattern
		Matcher dateMatcher = DATE_FORMAT.matcher(startTime);
		return (dateMatcher.matches());
	}
	
	public static boolean checkPrice(String premiumPrice, String cheapPrice) {
		// Check if price is in the correct format by comparing it to regex pattern
		Matcher premiumPriceMatcher = PRICE_FORMAT.matcher(premiumPrice);
		Matcher cheapPriceMatcher = PRICE_FORMAT.matcher(cheapPrice);
		if (!(premiumPriceMatcher.matches()) || !(cheapPriceMatcher.matches())) {
			return false;
		} else {
			return true;
		}
	}
	

	

}
