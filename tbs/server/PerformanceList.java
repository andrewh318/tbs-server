package tbs.server;

import java.util.ArrayList;
import java.util.List;

public class PerformanceList {
	private List<Performance> _performances;
	
	public PerformanceList() {
		_performances = new ArrayList<Performance>();
	}
	
	public void addPerformance(Performance performance) {
		_performances.add(performance);
	}
	
	public boolean containsPerformanceID(String performanceID) {
		// Do one pass through list of performance objects and check if performanceID exists
		for (Performance performance : _performances) {
			if (performance.getPerformanceID().equals(performanceID)) {
				return true;
			}
		}
		return false;
	}
	
	public Performance findPerformance(String performanceID) {
		// Return performance object associated with performanceID supplied
		for (Performance performance : _performances) {
			if (performance.getPerformanceID().equals(performanceID)) {
				return performance;
			}
		}
		return null;
	}
	
}
