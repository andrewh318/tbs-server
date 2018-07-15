package tbs.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TBSServerImpl implements TBSServer {
	// The following objects represent a list of theatre, acts, artists and performances respectively
	private TheatreList _theatres;
	private ActList _acts;
	private ArtistList _artists; 
	private PerformanceList _performances;
	
	public TBSServerImpl() {
		_theatres = new TheatreList();
		_acts = new ActList();
		_artists = new ArtistList();
		_performances = new PerformanceList();
	}
	
	@Override
	public String initialise(String path) {
		File file = new File(path);
		try {
			Scanner inputStream = new Scanner(file);
			while (inputStream.hasNext()) {
				String data = inputStream.nextLine();
				/* Split each line into 4 sections
				 * Ignore values[0] as it is just "Theatre" text
				 * values[1] = theatreID
				 * values[2] = seat dimensions
				 * Values[3] = floor area 
				 */
				String [] values = data.split("\t");
				/* Get theatreID, seat dimension and floor area from each line and use them as parameters to create theatre object
				 * ERORR check: Make sure that the theatre ID is not in the position of seat dimension or floor area
				 * Implementation: Use parseInt method on values[2] and values[3], if the 'T' character associated 
				 * with theatreID is found, parseInt will throw an exception as it is an invalid String that can not
				 * be converted to an int
				 */
				_theatres.addTheatre(new Theatre(values[1],Integer.parseInt(values[2]),Integer.parseInt(values[3]))); 
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			return "ERROR: File Not Found";
		} catch (NumberFormatException e) {
			return "ERROR: Please check data is in format <TheatreID> <Seat Dimension> <FloorArea>";
		}
		return ""; 
		
	}

	@Override

	public List<String> getTheatreIDs() {
		return _theatres.getTheatreIDs();
	}

	@Override
	public List<String> getArtistIDs() {
		return _artists.getArtistIDs();
	}

	@Override
	public List<String> getArtistNames() {
		return _artists.getArtistNames();
	}

	@Override
	public List<String> getActIDsForArtist(String artistID) {
		List<String> actIDs = new ArrayList<String>();
		// Check if artist ID is empty
		if (artistID == null || artistID.trim().isEmpty()) {
			actIDs.add("ERROR: Artist ID can not be empty ");
		// Check if artist with associated ID exists
		} else if (!(_artists.containsArtistID(artistID))) {
			actIDs.add("ERROR: Artist ID invalid");
		} else {
			// Find artist object associated with artist ID and then use it to get list of act ID's
			actIDs = _artists.findArtist(artistID).getActIDs();
		}
		return actIDs;
	}

	@Override
	public List<String> getPeformanceIDsForAct(String actID) {
		List<String> performanceIDs = new ArrayList<String>();
		// Remove any white space then check if act ID is empty
		if (actID == null || actID.trim().isEmpty()) {
			performanceIDs.add("ERROR: Act ID can not be empty");
		// Search lists of acts and check if actID exists
		} else if (!(_acts.containsActID(actID))) {
			performanceIDs.add("ERROR: Act ID is invalid");
		} else {
			// Find act object associated with act ID and then use it to get list of performance IDs
			performanceIDs = _acts.findAct(actID).getPerformanceIDs();
		}
		return performanceIDs;
	}
		

	@Override
	public List<String> getTicketIDsForPerformance(String performanceID) {
		List<String> ticketIDs = new ArrayList<String>();
		// Remove any white space then check if performance ID is empty
		if (performanceID == null || performanceID.trim().isEmpty()) {
			ticketIDs.add("ERROR: Performance ID can not be empty");
		// Search list of performances to check if performanceID exists
		} else if (!(_performances.containsPerformanceID(performanceID))) {
			ticketIDs.add("ERROR: Performance ID is invalid");
		} else {
			// Find performance object associated with performance ID and then use it to get list of ticket IDs
			ticketIDs = _performances.findPerformance(performanceID).getTicketIDs();
		}
		return  ticketIDs;
	}

	@Override
	public String addArtist(String artistName) {
		// Remove any white space then check if artistName is empty
		if (artistName == null || artistName.trim().isEmpty()) {
			return "ERROR: Artist name can not be empty";
		// Search list of artists to see if artist name exists
		} else if (_artists.containsArtistName(artistName)) {
			return "ERROR: Artist already exists";
		} else {
			String uniqueID = IDs.generateArtistID();
			_artists.addArtist(new Artist(artistName,uniqueID)); // Create new artist and add it to list of artists
			return uniqueID; 	
		}
		
	}

	@Override
	public String addAct(String title, String artistID, int minutesDuration) {
		// Remove any white space and check if title is empty
		if (title == null || title.trim().length() == 0) {
			return "ERROR: Title of act can not be empty";
		// Check if artistID exists and then check if artistID is empty
		} else if (!(_artists.containsArtistID(artistID)) || artistID.trim().length() == 0) {
			return "ERROR: Artist ID not found/invalid";
		// Check if the number of minutes for act is valid 
		} else if (minutesDuration <= 0) {
			return "ERROR: Duration of act can not be negative";
		} else {
			// Find the artist corresponding to the artistID
			// Note this artist must exist otherwise this block of code will not run (error would have been returned earlier)
			String uniqueID = IDs.generateActID(); 
			Artist artist = _artists.findArtist(artistID); 
			Act act = new Act(title, artistID, uniqueID, minutesDuration);
			artist.addAct(act); // Add the act into the list of acts for the artist
			_acts.addAct(act); // Add the act into the list of all acts
			return uniqueID; 
		}
	}

	@Override

	public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr,
			String cheapSeatsStr) {
		// Search list of acts to check if actID exists
		if (!(_acts.containsActID(actID))) {
			return "ERROR: Act with act ID does not exist";
		// Search list of theatres to check if theatreID exists
		} else if (!(_theatres.containsTheatreID(theatreID))) {
			return "ERROR: Theatre with theatre ID does not exist";
		} else {
			// Error checking for date format and price format is done in the constructor for the Performance class 
			// Constructor will throw an exception and fail to create the performance object if format is incorrect
			try {
				String uniqueID = IDs.generatePerformanceID(); 
				// Find the act corresponding to the actID and theatre corresponding to theatreID
				// Note the act and theatre must exist otherwise this block of code will not run (error would have been returned earlier)
				Act act = _acts.findAct(actID); 
				Theatre theatre = _theatres.findTheatre(theatreID); 
				Performance performance = new Performance(uniqueID,theatre,startTimeStr,premiumPriceStr, cheapSeatsStr);
				_performances.addPerformance(performance); // Add performance object to the list of performances
				act.addPerformance(performance); // Add performance to list of all performances associated with the act
				return uniqueID;
			} catch (DateFormatException e) { 
				return "ERROR: Invalid Date Format";
				
			} catch (PriceFormatException e) {
				return "ERROR: Invalid Price Format";
			} 
		}
	}

	@Override
	public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
		// Search list of performances to check if performance ID exists
		if (!(_performances.containsPerformanceID(performanceID))) {
			return "ERROR Performance with performance ID does not exist";
		} else {
			// Find performance associated with performance ID
			Performance performance = _performances.findPerformance(performanceID);
			// Check if seat exists and is available
			if (!(performance.checkSeatExists(rowNumber, seatNumber))){ 
				return "ERROR: Seat does not exist, please check row or seat number";
			} else if (!(performance.checkSeatAvailability(rowNumber, seatNumber))) {
				return "ERROR: Seat has already been taken";
			} else {
				String uniqueID = IDs.generateTicketID();
				Ticket ticket = new Ticket(uniqueID,performanceID, rowNumber, seatNumber);
				performance.addTicket(ticket); // Add ticket to list of tickets in associated performance object
				performance.reserveSeat(rowNumber, seatNumber); // Mark this seat as reserved
				return uniqueID;
			}
		}

	}

	@Override
	public List<String> seatsAvailable(String performanceID) {
		List<String> seatsAvailable = new ArrayList<String>();
		// Remove any white space and check if performanceID is empty
		if (performanceID == null || performanceID.trim().length() == 0) {
			seatsAvailable.add("ERROR: Performance ID can not be empty");
		// Check if performance associated with performance ID exists
		} else if (!(_performances.containsPerformanceID(performanceID))) {
			seatsAvailable.add("ERROR: Performance with performance ID does not exist");
		} else {
			Performance performance = _performances.findPerformance(performanceID);
			seatsAvailable = performance.getSeatsAvailable();
		}
		
		return seatsAvailable;
		
	}

	@Override
	public List<String> salesReport(String actID) {
		List<String> salesReport = new ArrayList<String>();
		// Check if act associated with act ID exists
		if (!(_acts.containsActID(actID))) {
			salesReport.add("ERROR: Act with act ID does not exist");
		} else {
			Act act = _acts.findAct(actID);
			salesReport = act.generateSalesreport();
		}

		return salesReport;
	}

	@Override
	public List<String> dump() {
		List<String> str = new ArrayList<String>();
		return str;
	}

}
