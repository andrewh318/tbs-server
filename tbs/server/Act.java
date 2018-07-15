package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Act {
	private String _title;
	private String _artistID; 
	private String _actID;
	
	private int _duration;
	
	private List<Performance> _performances;

	
	public Act(String title, String artistID, String actID, int duration) {
		_title = title;
		_artistID = artistID;
		_actID = actID;
		
		_duration = duration;
		
		_performances = new ArrayList<Performance>();
	}
	public String getArtistID() {
		return _artistID;
	}
	
	public int getDuration() {
		return _duration;
	}
	public String getTitle() {
		return _title;
	}
	
	public String getActID() {
		return _actID;
	}	
	
	public void addPerformance(Performance performance) {
		_performances.add(performance);
	}
	
	public List<String> getPerformanceIDs(){
		List<String> performanceIDs = new ArrayList<String>();
		// Do one pass through list of performance objects and add their IDs to performanceIDs list
		for (Performance performance : _performances) {
			performanceIDs.add(performance.getPerformanceID());
		}
		return performanceIDs;
	}
	
	public List<String> generateSalesreport(){
		List<String> salesReport = new ArrayList<String>();
		if (_performances.isEmpty()) {
			salesReport.add("No current performances for this act");
		} else {
			// Do one pass through list of performance objects and collect required information
			for (Performance performance : _performances) {
				String performanceID = performance.getPerformanceID();
				String startTime = performance.getStartTime();
				String ticketsSold = performance.getNumberOfTicketsSold();
				String salesReceipt = performance.getSalesReceipt();
				String output = performanceID + "\t" + startTime + "\t" + ticketsSold + "\t" + salesReceipt; // Concatenate information into single string
				salesReport.add(output);
			}
		}
		return salesReport;
	}
	
	

}
