package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtistList {
	private List<Artist> _artists;
	
	public ArtistList() {
		_artists = new ArrayList<Artist>();
	}
	
	public List<Artist> getArtistList(){
		return _artists;
	}
	
	public void addArtist(Artist artist) {
		_artists.add(artist);
	}
	
	public List<String> getArtistNames(){
		List<String> artistNames = new ArrayList<String>();
		// Do one pass through list of artist objects and add their names to artistNames list
		for (Artist artist : _artists) {
			artistNames.add(artist.getArtistName());
		}
		Collections.sort(artistNames);
		return artistNames;
	}
	
	public List<String> getArtistIDs(){
		List<String> artistIDs = new ArrayList<String>();
		// Do one pass through list of artist objects and add their IDs to artistIDs list
		for (Artist artist : _artists) {
			artistIDs.add(artist.getArtistID());
		}
		Collections.sort(artistIDs);
		return artistIDs;
	}
	
	public boolean containsArtistName(String artistName) {
		// Do one pass through list of artist objects and check if artist name already exists (case insensitive)
		for (Artist artist : _artists) {
			if (artist.getArtistName().equalsIgnoreCase(artistName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsArtistID(String artistID) {
		// Do one pass through list of artist objects and check if artist ID exists
		for (Artist artist : _artists) {
			if (artist.getArtistID().equals(artistID)) {
				return true;
			}
		}
		return false;
	}
	
	public Artist findArtist(String artistID) {
		// Return the artist object associated with the artist ID supplied
		for (Artist artist : _artists) {
			if (artist.getArtistID().equalsIgnoreCase(artistID)) {
				return artist;
			}
		}
		return null;
	}

}
