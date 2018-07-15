package tbs.server;

public class IDs {
	
	private static int _artistIDCount = 0;
	private static int _actIDCount = 0;
	private static int _performanceIDCount = 0;
	private static int _ticketIDCount = 0;
	
	/* 
	 * Unique IDs are created using the following format
	 * Artist IDs = "ART" + a unique number starting from 0
	 * Act IDs = "ACT" + a unique number starting from 0
	 * Performance IDs = "PERF" + a unique number starting from 0
	 * Ticket IDs = "TIC" + a unique number starting from 0
	 */
	
	// After an ID generator method is called, increment the associated count field 
	public static String generateArtistID() {
		String uniqueID = "ART" + String.valueOf(_artistIDCount);
		_artistIDCount++; 
		return uniqueID;
	}
	
	public static String generateActID() {
		String uniqueID = "ACT" + String.valueOf(_actIDCount);
		_actIDCount++; 
		return uniqueID;
	}
	
	public static String generatePerformanceID() {
		String uniqueID = "PERF" + String.valueOf(_performanceIDCount);
		_performanceIDCount++;
		return uniqueID;
	}
	
	public static String generateTicketID() {
		String uniqueID = "TIC" + String.valueOf(_ticketIDCount);
		_ticketIDCount++;
		return uniqueID;
	}
	
}
