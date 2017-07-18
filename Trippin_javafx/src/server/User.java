package server;

import java.util.Map;
import java.util.HashMap;


public class User {
	
	private String username, password, fname, lname, image;
	private Map<Integer, Group> trips;
	
	public User(String username, String password, String fname, String lname, String image){
		this.username = username;
		this.password =password;
		this.fname = fname;
		this.lname = lname;
		this.image = image;
		trips = new HashMap<>();
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getImage(){
		return image;
	}
	
	public String getFName(){
		return fname;
	}
	
	public String getLName(){
		return lname;
	}
	
	public Map<Integer, Group> getTrips(){
		return trips;
	}
	
	public void addTrip(Group trip){
		trips.put(trip.getGroupID(), trip);
	}
	
}
