package server;

import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

public class Group {
 
	private int groupID;
	private String groupName;
	private String groupImage;
	private Vector<User> members;
	private int meetingTime;
	private int date;
	private String comment;
	private Map<String, Double> budget = new HashMap<String, Double>();
	private Map<String, Integer> songs = new HashMap<String, Integer>();
	private Vector<String> destinations = new Vector<String>();
	// google map API object to be added
	
	/* constructor without group id
	 * songs, budget and destinations will be set later */
	public Group(String groupImage, int time, int date, String comment){
		this.groupImage = groupImage;
		this.meetingTime = time;
		this.date = date;
		this.comment = comment;
	}
	
	/* constructor with group id */
	public Group(int groupID, String groupImage, int time, int date, String comment){
		this.groupID = groupID;
		this.groupImage = groupImage;
		this.meetingTime = time;
		this.date = date;
		this.comment = comment;
	}
		
	public int getGroupID(){
		return groupID;
	}
	
	public void setGroupID(int ID){
		groupID = ID;
	}

	public String getGroupName() { return groupName; }

	public void setGroupName(String name) { groupName = name; }
	
	public int getMeetingTime() {
		return meetingTime;
	}
	
	public void setMeetingTime(int meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	public int getDate() {
		return date;
	}
	
	public void setDate(int date) {
		this.date = date;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getGroupImage(){
		return groupImage;
	}

	public void setGroupImage (String groupImage){
		this.groupImage = groupImage;
	}
	
	public Vector<User> getMembers(){
		return members;
	}
	
	public Vector<String> getMembersNames(){
		Vector<String> memberNames = new Vector<String>();
		for (User user : members){
			memberNames.add(user.getFName()+ " " + user.getLName());
		}
		return memberNames;
	}
	
	public void addMember(User newMember){
		members.add(newMember);
	}
	
	public Map<String, Double> getBudget(){
		return budget;
	}
	
	public void addBudget(String item, double amt){
		budget.put(item, amt);
	}
	
	public Map<String, Integer> getSongQueue(){
		return songs;
	}
	
	public void addSong(String song){
		songs.put(song, 1);
	}
	
	public void removeSong(String songToRemove){
		songs.remove(songToRemove);
	}
	
	public Vector<String> getDestinationList(){
		return destinations;
	}
	
	public void setDestination(int pos, String des){
		destinations.set(pos, des);
	}
	
	public void addDestination(String new_des, int pos){
		destinations.add(new_des);
	}
}
