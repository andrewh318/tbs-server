package tbs.server;

public class Theatre {
	private String _theatreID;
	private int _seatDimension;
	private int _floorArea;
	
	public Theatre(String theatreID, int seatDimension, int floorArea) {
		_theatreID = theatreID;
		_seatDimension = seatDimension;
		_floorArea = floorArea;
	}
	
	public String getTheatreID() {
		return _theatreID;
	}
	
	public int getSeatDimension() {
		return _seatDimension;
	}
	
	public int getFloorArea() {
		return _floorArea; 
	}
}
