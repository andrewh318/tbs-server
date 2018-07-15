package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheatreList {
	private List<Theatre> _theatres;
	
	public TheatreList() {
		_theatres = new ArrayList<Theatre>();
	}
	
	public List<Theatre> getTheatreList(){
		return _theatres;
	}
	
	public void addTheatre(Theatre theatre) {
		_theatres.add(theatre);
	}
	
	public List<String> getTheatreIDs(){
		List<String> theatreIDs = new ArrayList<String>(); 
		// Do one pass through list of theatre objects and add their IDs to theatreIDs list
		for (Theatre theatre : _theatres) {
			theatreIDs.add(theatre.getTheatreID()); 
		}
		Collections.sort(theatreIDs);
		return theatreIDs;
	}
	
	
	public boolean containsTheatreID(String theatreID) {
		// Do one pass through list of theatre objects and check if theatre ID exists
		for (Theatre theatre : _theatres) {
			if (theatre.getTheatreID().equals(theatreID)) { 
				return true;
			}
		}
		return false;
	}
	
	public Theatre findTheatre(String theatreID) {
		// Return the theatre object associated with the theatreID supplied
		for (Theatre theatre : _theatres) {
			if (theatre.getTheatreID().equals(theatreID)) { 
				return theatre;
			}
		}
		return null;
	}
	
}
