package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class Artist {
	private String _artistName; 
	private String _artistID;
	private List<Act> _acts;

	public Artist(String artistName, String artistID) {
		_artistName = artistName;
		_artistID = artistID; 
		_acts = new ArrayList<Act>();
	}

	public String getArtistName() {
		return _artistName;
	}
	
	public String getArtistID() {
		return _artistID;
	}
	
	public List<String> getActIDs(){
		List<String> actIDs = new ArrayList<String>();
		
		for (Act act : _acts) {
			actIDs.add(act.getActID());
		}
		return actIDs;
	}
	
	public void addAct(Act act) {
		_acts.add(act);
	}
	
	
	

	
}
