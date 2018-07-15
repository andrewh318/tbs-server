package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class ActList {
	private List<Act> _acts; 
	
	public ActList() {
		_acts = new ArrayList<Act>();
	}

	public List<Act> getActList(){
		return _acts;
	}
	
	public void addAct(Act act ) {
		_acts.add(act);
	}
	
	public boolean containsActID(String actID) {
		// Do one pass through act objects and check if act ID exists
		for (Act act : _acts) {
			if (act.getActID().equals(actID)) {
				return true;
			}
		}
		return false;
	}
	
	public Act findAct(String actID) {
		// Return act object associated with act ID 
		for (Act act : _acts) {
			if (act.getActID().equals(actID)) {
				return act;
			}
		}
		return null;
	}
}
