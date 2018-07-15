package tbs.server;

public class Ticket {
	private String _ticketID;
	private String _performanceID;
	
	private int _rowNumber;
	private int _seatNumber;

	
	public Ticket(String ticketID, String performanceID, int rowNumber, int seatNumber) {
		_ticketID = ticketID;
		_performanceID = performanceID;
		_rowNumber = rowNumber;
		_seatNumber = seatNumber;

	}
	
	public String getTicketID() {
		return _ticketID;
	}
	
	public int getRowNumber() {
		return _rowNumber;
	}
	
	public int getSeatNumber() {
		return _seatNumber;
	}
	
	public String performanceID() {
		return _performanceID;
	}
	
	
	
	

}
