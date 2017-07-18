package server;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest {
	private Connection conn = null;
	private Statement st = null;
	
	int destinationID = 1;
	
	/* constructor
	 * initialize connection to localhost database
	 */
	public DatabaseTest(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("waiting for connection...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/GroupData?user=root&password=root&useSSL=false");
			System.out.println("connection successful");
			st = conn.createStatement();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe exception: " + cnfe.getMessage());
		} catch (SQLException sqle){
			System.out.println("sqle exception: " + sqle.getMessage());
		}
	}
	
	/* method to add a user in database
	 * can't have two users with the same username
	 */
	public void addUser(String username, String pwd, String fname, String lname, String image){
		if (getUserID(username) == 0){
			try{
				st.executeUpdate("INSERT INTO User(username, password, fname, lname, profileImg) VALUES ('" 
						+ username + "', '" + pwd + "', '" + fname + "', '" + lname + "', '" + image + "');");
			} catch (SQLException sqle){
				System.out.println("sqle in addUser, " + sqle.getMessage());
			}
		} else System.out.println("failed, " + username + " is already existed in database");
	}
	
	/* delete a user from database, any group-user relationship
	 * will also be deleted. Do nothing if user doesn't
	 * exist
	 */
	public void deleteUser(String username){
		int userID = getUserID(username);
		try {
			st.executeUpdate("DELETE FROM User WHERE userID = " + userID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in deleteUser " + sqle.getMessage());
		}
	}
	
	/* find userID based on username 
	 * return the ID of target user
	 * 0 if not in the table*/
	@SuppressWarnings("finally")
	public int getUserID(String username){
		int result = 0;
		try {
			ResultSet rs = st.executeQuery("SELECT userID "
					+ "FROM User u WHERE u.username = '" + username + "';");
			if (rs.next()) result = rs.getInt("userID");
		} catch (SQLException sqle) {
			System.out.println("sqle in findUser, " + sqle.getMessage());
		} finally {
			return result;
		}
	}
	
	/* function to get username given userID
	 * return 0 if no such username exists
	 */
	@SuppressWarnings("finally")
	public String getUserUsername(int userID){
		String result = new String();
		try {
			ResultSet rs = st.executeQuery("SELECT username FROM User u WHERE u.userID = '" + userID + "';");
			if (rs.next()) result = rs.getString("username");
		} catch (SQLException sqle) {
			System.out.println("sqle in findUser(), " + sqle.getMessage());
		} finally {
			return result;
		}
	}
	
	/* function used to validate if the username exists in the database 
	 * return true if no same username exists
	 * REMEMBER TO CALL BEFORE LOG IN THE USER*/
	@SuppressWarnings("finally")
	public boolean validateUsername(String input){
		boolean ifValid = true;
		int userID = 0;
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM User u WHERE u.username = '" + input + "';");
			if (rs.next()){
				userID = rs.getInt("userID");
				ifValid = false;
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in findUser(), " + sqle.getMessage());
		} finally {
			return ifValid;
		}
	}
	
	/* function to get password once username is known
	 * could be easily merged into validateUsername mehthod
	 * depends on server implementation
	 */
	@SuppressWarnings("finally")
	public String getPassword(String username){
		String pwd = new String();
		try {
			ResultSet rs = st.executeQuery("SELECT password FROM User u WHERE u.username = '" + username + "';");
			if (rs.next()) pwd = rs.getString("password");
		} catch (SQLException sqle) {
			System.out.println("sqle in getPassword, " + sqle.getMessage());
		} finally {
			return pwd;
		}
	}
	
	
	
	
	
	
	/* function to check if a user with the same username already in
	 * the database. Return true if no group with the same name in
	 * the database. REMEMBER TO CALL BEFORE SAVE THE GROUP
	 */
	@SuppressWarnings("finally")
	public boolean validateGroupName(String input){
		boolean ifValid = true;
		String group_name = new String();
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM Trip t WHERE t.tripName = '" + input + "';");
			if (rs.next()){
				group_name = rs.getString("tripName");
				ifValid = false;
			}
			System.out.println("The group's name is " + group_name);
		} catch (SQLException sqle) {
			System.out.println("sqle in validateGroupName(), " 
					+ sqle.getMessage());
		} finally {
			return ifValid;
		}
	}

	/* pretty basic function to store the group, most of the
	 * data are set to default for now
	 */
	public void createNewGroup(String groupName, boolean ifPublic, 
			String startDate, String endDate, String meetingTime, String comment){
		int a = 0;
		if (ifPublic) a = 1;
		try {
			st.executeUpdate("INSERT INTO Trip(tripName, ifPublic, startDate, endDate, meetingTime, commentTrip) VALUES ('" 
					+ groupName + "', " + a + ", '" + startDate + "', '" + endDate + "', '" 
					+ meetingTime + "', '" + comment + "');");
			System.out.println(groupName + "group stored!");
		} catch (SQLException sqle){
			System.out.println("sqle in createNewGroup, " + sqle.getMessage());
		}
	}
	
	/* function to retrieve the groupID given
	 * the group name. Return 0 if the given 
	 * group name doesn't exist
	 */
	@SuppressWarnings("finally")
	public int getGroupID(String groupName){
		int result = 0;
		try {
			ResultSet rs = st.executeQuery("SELECT tripID "
					+ "FROM Trip t WHERE t.tripName = '" + groupName + "';");
			if (rs.next()) result = rs.getInt("tripID");
		} catch (SQLException sqle) {
			System.out.println("sqle in getGroupID" + sqle.getMessage());
		} finally {
			return result;
		}
	}
	
	/* function to retrieve group name given group id
	 * return an empty string if not in the database
	 */
	@SuppressWarnings("finally")
	public String getTripName(int groupID){
		String tripName = new String();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT * FROM groupdata.trip WHERE tripID = " + groupID + ";");
			if (rs.next()) tripName = rs.getString("tripName");
		} catch (SQLException sqle) {
			System.out.println("sqle in getTripName, " + sqle.getMessage());
		} finally {
			return tripName;
		}
	}
	
	/* setter function to change the name of a trip
	 * the rest are setters of detailed info of a trip
	 */
	public void changeTripName(int groupID, String new_name){
		try {
			st.executeUpdate("UPDATE Trip SET tripName = '" + new_name + "' WHERE tripID = " + groupID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in changeTripname " + sqle.getMessage());
		}
	}
	
	@SuppressWarnings("finally")
	public String getMeetingTime(int groupID){
		String meetingTime = new String();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT * FROM groupdata.trip WHERE tripID = " + groupID + ";");
			if (rs.next()) meetingTime = rs.getString("meetingTime");
		} catch (SQLException sqle) {
			System.out.println("sqle in getmeetingTime, " + sqle.getMessage());
		} finally {
			return meetingTime;
		}
	}
	
	public void changeMeetingTime(int groupID, String time){
		try {
			st.executeUpdate("UPDATE Trip SET meetingTime = '" + time + "'WHERE tripID = " + groupID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in changeTripname, " + sqle.getMessage());
		}
	}
	
	@SuppressWarnings("finally")
	public String getStartDate(int groupID){
		String startDate = new String();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT * FROM groupdata.trip WHERE tripID = " + groupID + ";");
			if (rs.next()) startDate = rs.getString("startDate");
		} catch (SQLException sqle) {
			System.out.println("sqle in getStartDate, " + sqle.getMessage());
		} finally {
			return startDate;
		}
	}
	
	public void changeStartDate(int groupID, String new_date){
		try {
			st.executeUpdate("UPDATE Trip SET startDate = '" + new_date + "'WHERE tripID = " + groupID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in changeStartDate, " + sqle.getMessage());
		}
	}
	
	@SuppressWarnings("finally")
	public String getEndDate(int groupID){
		String endDate = new String();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT endDate FROM groupdata.trip WHERE tripID = " + groupID + ";");
			if (rs.next()) endDate = rs.getString("endDate");
		} catch (SQLException sqle) {
			System.out.println("sqle in getEndDate, " + sqle.getMessage());
		} finally {
			return endDate;
		}
	}
	
	public void changeEndDate(int groupID, String new_date){
		try {
			st.executeUpdate("UPDATE Trip SET endDate = '" + new_date + "'WHERE tripID = " + groupID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in changeEndDate, " + sqle.getMessage());
		}
	}
	
	/* return a boolean value, "true" if the trip is public
	 * and "false" otherwise
	 */
	@SuppressWarnings("finally")
	public boolean getPublic(int groupID){
		boolean ifPublic = false;
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT * FROM groupdata.trip WHERE tripID = " + groupID + ";");
			if (rs.next()){
				int temp = rs.getInt("ifPublic");
				if (temp == 1) ifPublic = true;
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in getPublic, " + sqle.getMessage());
		} finally {
			return ifPublic;
		}
	}
	
	/* "true" for public group, "false" for private */
	public void changePublic(int groupID, boolean ifPublic){
		int p = 0; // 0 means the group is private
		if (ifPublic) p = 1; // 1 means the group is public
		try {
			st.executeUpdate("UPDATE Trip SET ifPublic = '" + p + "'WHERE tripID = " + groupID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in changePublic, " + sqle.getMessage());
		}
	}
	
	@SuppressWarnings("finally")
	public String getComment(int groupID){
		ResultSet rs;
		String comment = new String();
		try {
			rs = st.executeQuery("SELECT commentTrip FROM groupdata.trip WHERE tripID = " + groupID + ";");
			if (rs.next()) comment = rs.getString("commentTrip");
		} catch (SQLException sqle) {
			System.out.println("sqle in getEndDate, " + sqle.getMessage());
		} finally {
			return comment;
		}
	}
	
	/* store the input text into the database */
	public void changeComment(int groupID, String comment){
		try {
			st.executeUpdate("UPDATE Trip t SET t.commentTrip = '" + comment + "' WHERE tripID = " + groupID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in changeComment " + sqle.getMessage());
		}
	}
	 
	/* returns a map of all existing groups mapping tripID to tripName
	 * if want to get lon and lat on the first destination
	 * call getFirstDesLon and getFirstDesLat on tripID
	 */
	@SuppressWarnings("finally")
	public Map<Integer, String> getAllGroup(){
		Map<Integer, String> trips = new HashMap<Integer, String>();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT tripID, tripName FROM groupdata.trip;");
			while(rs.next()){
				int tripID = rs.getInt("tripID");
				String name = rs.getString("tripName");
				trips.put(tripID, name);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in getAllGroup, " + sqle.getMessage());
		} finally {
			return trips;
		}
	}
	
	/* returns a map all public groups mapping tripID to tripName
	 * if want to get lon and lat on the first destination
	 * call getFirstDesLon and getFirstDesLat on tripID
	 */
	@SuppressWarnings("finally")
	public Map<Integer, String> getAllPublicGroup(){
		Map<Integer, String> trips = new HashMap<Integer, String>();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT tripID, tripName FROM groupdata.trip WHERE ifPublic = 1;");
			while(rs.next()){
				int tripID = rs.getInt("tripID");
				String name = rs.getString("tripName");
				trips.put(tripID, name);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in getAllPublicGroup, " + sqle.getMessage());
		} finally {
			return trips;
		}
	}
	
	/* delete a trip from Trip class, all songs, destinations and
	 * budgets of that class will be deleted as well
	 * nothing happens if the tripID doesn't exist
	 */
	public void deleteGroup(int groupID){
		try {
			st.executeUpdate("DELETE FROM Trip WHERE tripID = " + groupID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in delete group, " + sqle.getMessage());
		}
	}
	
	
	
	/* function to retrieve longitude of first destination of the group
	 * return -1 if the first destination doesn't exist */
	@SuppressWarnings("finally")
	public double getFirstDesLon(int groupID){
		ResultSet rs;
		double lon = -1;
		try{
			rs = st.executeQuery("SELECT * FROM Destination d WHERE d.groupID = " 
					+ groupID + " ORDER BY d.des_order ASC LIMIT 1;");
			if (rs.next()) {
				lon = rs.getDouble("lon");
			}
		} catch (SQLException sqle){
			System.out.println("sqle in getFirstDesLon " + sqle.getMessage());
		} finally {
			return lon;
		}
	}
	
	/* function to retrieve lattitude of first destination of the group 
	 * return -1 if the first destination doesn't exist */
	@SuppressWarnings("finally")
	public double getFirstDesLat(int groupID){
		ResultSet rs;
		double lat = -1;
		try{
			rs = st.executeQuery("SELECT * FROM Destination d WHERE d.groupID = " 
					+ groupID + " ORDER BY d.des_order ASC LIMIT 1;");
			if (rs.next()) {
				lat = rs.getDouble("lat");
			}
		} catch (SQLException sqle){
			System.out.println("sqle in getFirstDesLat " + sqle.getMessage());
		} finally {
			return lat;
		}
	}
	
	/* return "true" if can add the destination into the database,
	 * "false" otherwise
	 */
	@SuppressWarnings("finally")
	public boolean checkIfDestinationExist(int groupID, double lon, double lat){
		ResultSet rs;
		boolean ifInTheDatabase = true;
		try {
			rs = st.executeQuery("SELECT * FROM Destination d WHERE d.groupID = " 
					+ groupID + " AND d.lon = " + lon + " AND	d.lat = " + lat + ";");
			if (rs.next()) ifInTheDatabase = false;
		} catch (SQLException sqle) {
			System.out.println("sqle in checkIfDestinationExist, " + sqle.getMessage());
		} finally {
			return ifInTheDatabase;
		}
	}
	
	/* add new destination to given trip. The order of destination within
	 * a group is automatically incremented
	 */
	public void addDestination(String destination, int groupID, double lon, double lat){
		ResultSet rs;
		int order = 0;
		try{
			rs = st.executeQuery("SELECT * FROM Destination d WHERE d.groupID = " 
					+ groupID + " ORDER BY d.des_order DESC LIMIT 1;");
			if (rs.next()) order = rs.getInt("des_order");
			st.executeUpdate("INSERT INTO Destination(groupID, destinations, lon, "
					+ "lat, des_order) VALUES (" + groupID + ", '" + destination + "', " 
					+ lon + ", " + lat + ", " + ++order + ");");
		} catch (SQLException sqle){
			System.out.println("sqle in getFirstDesLon " + sqle.getMessage());
		}
	}   
	
	/* return a list of destinations. To retrieve individual info call the 
	 * corresponding getters in Destination class
	 */
	@SuppressWarnings("finally")
	public ArrayList<Destination> getDestination(int groupID){
		ResultSet rs;
		ArrayList<Destination> des_list = new ArrayList<Destination>();
		try {
			rs = st.executeQuery("SELECT * FROM groupdata.destination WHERE groupID = " + groupID + ";");
			while(rs.next()){
				int order = rs.getInt("des_order");
				String destination = rs.getString("destinations");
				double lon = rs.getDouble("lon");
				double lat = rs.getDouble("lat");
				Destination temp = new Destination(order, destination, lon, lat);
				des_list.add(temp);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in getDestination" + sqle.getMessage());
		} finally {
			return doInsertionSort(des_list);
		}
	}
	
	/* helper func to sort the list of destination in the order of the trip */
	public static ArrayList<Destination> doInsertionSort(ArrayList<Destination> input){
        Destination temp;
        for (int i = 1; i < input.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(input.get(j).getOrder() < input.get(j - 1).getOrder()){
                    temp = input.get(j);
                    input.set(j, input.get(j - 1));
                    input.set(j - 1, temp);
                }
            }
        }
        return input;
    }
	
	/* function to add a song to the given group. If the song already
	 * ecixts in the group "false" is returned. "true" otherwise.
	 */
	@SuppressWarnings("finally")
	public boolean addSong(int groupID, String song){
		boolean result = true;
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM Song s WHERE s.song = '" 
					+ song + "' AND s.groupID = " + groupID + ";");
			if (rs.next()) result = false;
			else{
				st.executeUpdate("INSERT INTO Song(groupID, song, vote) VALUES (" 
						+ groupID + ", '" + song + "', 0);");
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in add song, " + sqle.getMessage());
		} finally {
			return result;
		}
	}
	
	public void upvoteSong(String song, int groupID){
		ResultSet rs;
		int current_vote = 0;
		try {
			rs = st.executeQuery("SELECT * FROM Song WHERE song = '" + song + "' AND groupID = " + groupID + ";");
			if (rs.next()){
				current_vote = rs.getInt("vote");
				++current_vote;
				st.executeUpdate("UPDATE Song s SET	s.vote = " + current_vote + " WHERE s.groupID = " + groupID + " AND s.song= '" + song + "';");
			} else {
				System.out.println("can't update, song doesn't exist");
			}
		} catch (SQLException sqle){
			System.out.println("sqle in upvoteSong, " + sqle.getMessage());
		}
	}
	
	public void downvoteSong(String song, int groupID){
		ResultSet rs;
		int current_vote = 0;
		try {
			//System.out.println(target);
			rs = st.executeQuery("SELECT * FROM Song WHERE song = '" + song + "' AND groupID = " + groupID + ";");
			if (rs.next()){
				current_vote = rs.getInt("vote");
				if (current_vote > 0){
					--current_vote;
					st.executeUpdate("UPDATE Song s SET	s.vote = " + current_vote + " WHERE s.groupID = " + groupID + " AND s.song= '" + song + "';");
				}
			} else {
				System.out.println("can't update, song doesn't exist");
			}
		} catch (SQLException sqle){
			System.out.println("sqle in downvoteSong, " + sqle.getMessage());
		}
	}
	
	/* function to retrieve the song queue of each group
	 * return a map of the songs and their votes; 
	 * returns nothing if the group doesn't exist or its song queue is empty
	 */
	@SuppressWarnings("finally")
	public ArrayList<Song> getSongQueue(int groupID){
		ArrayList<Song> songs = new ArrayList<Song>();
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM groupdata.song WHERE song.groupID = " + groupID +" ORDER BY song.vote DESC;");
			while(rs.next()) {
				String name = rs.getString("song");
				int vote = rs.getInt("vote");
				Song temp = new Song(name);
				temp.setVote(vote);
				songs.add(temp);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in downvoteSong, " + sqle.getMessage());
		} finally {
			return songs;
		}
	}
	 
	/* function to delete a song. Do nothing if the song doesn't exist */
	public void deleteSong(int groupID, String song){
		try {
			st.executeUpdate("DELETE FROM Song WHERE groupID = " + groupID + " "
					+ "AND song = '" + song + "';");
		} catch (SQLException sqle) {
			System.out.println("sqle in song" + sqle.getMessage());
		}
	}
	
	/* function to a budget to the database. If the item already exists
	 * in the group, "false" is returned. "true" otherwise
	 */
	@SuppressWarnings("finally")
	public boolean addBudget(int groupID, String item, int amount){
		boolean result = true;
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM Budget b WHERE b.item = '" + item 
					+ "' AND b.groupID = " + groupID + ";");
			if (rs.next()) result = false;
			else {
				st.executeUpdate("INSERT INTO Budget(groupID, item, amount) VALUES (" 
						+ groupID + ", '" + item + "', '" + amount + "');");
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in add budget, " + sqle.getMessage());
		} finally {
			return result;
		}
	}
	
	/* update amount of the input item
	 * nothing happens if the target item
	 * doesn't exist
	 */
	public void updateBudgetAmount(int groupID, String item, int newAmount){
		try {
			st.executeUpdate("UPDATE Budget b SET b.amount = " 
					+ newAmount + " WHERE b.groupID = " + groupID + " AND b.item='" + item + "';");
		} catch (SQLException sqle) {
			System.out.println("sqle in updateBudget, " + sqle.getMessage());
		}
	}
	
	/* function to retrieve the list of budget of each group
	 * return a map of the budgets; returns nothing if the group
	 * doesn't exist or the budget is empty
	 */
	@SuppressWarnings("finally")
	public Map<String, Integer> getBudgetList(int groupID){
		Map<String, Integer> budgets = new HashMap<String, Integer>();
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM groupdata.budget WHERE budget.groupID = " + groupID +";");
			while(rs.next()) {
				String item = rs.getString("item");
				int amt = rs.getInt("amount");
				budgets.put(item, amt);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in getBudgetList" + sqle.getMessage());
		} finally {
			return budgets;
		}
	}
	
	/* function to delete a budget item. Do nothing if the item doesn't exist */
	public void deleteBudget(int groupID, String item){
		try {
			st.executeUpdate("DELETE FROM Budget WHERE groupID = " + groupID + " "
					+ "AND item = '" + item + "';");
		} catch (SQLException sqle) {
			System.out.println("sqle in deleteBudget, " + sqle.getMessage());
		}
	}
	
	
	
	
	
	
	
	/* function to check if a pair already exists in the database */
	@SuppressWarnings("finally")
	public boolean checkPair(int tripID, int userID){
		boolean ifAlreadyAPair = false;
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM UserGroup ug WHERE ug.userID = " + userID + " AND ug.tripID = " + tripID + ";");
			if (rs.next()) ifAlreadyAPair = true;
		} catch (SQLException sqle) {
			System.out.println("sqle in checkPair, " + sqle.getMessage());
		} finally {
			return ifAlreadyAPair;
		}	
	}
	
	/* mehtod to connect a user and a group
	 * can't add the same pair twice
	 */
	public void addUserGroup(int tripID, String username){
		int userID = getUserID(username);
		if (!checkPair(tripID, userID)){
			try {
				st.executeUpdate("INSERT INTO UserGroup(tripID, userID) VALUES (" + tripID + ", " + userID + ");");
			} catch (SQLException sqle) {
				System.out.println("exception in matching user and group" + sqle.getMessage());
			}
		} else System.out.println("pair of " + tripID + " and " + username + " already exists");
	}
	
	/* function to retrieve user members from the given groupID */
	@SuppressWarnings("finally")
	public ArrayList<String> getUsersFromGroup(int groupID){
		ArrayList<String> users = new ArrayList<String>();
		ResultSet rs;
		try {
			rs = st.executeQuery("SELECT u.username FROM UserGroup ug, User u WHERE ug.tripID = " + groupID + " AND ug.userID = u.userID;");
			while (rs.next()){
				String name = rs.getString("username");
				users.add(name);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in getUsersFromGroup" + sqle.getMessage());
		} finally {
			return users;
		}
	}
	
	/* function to get the list of groups the given user is 
	 * a member of; returns nothing if the user doesn't exist
	 * or he or she is not in any group
	 */
	@SuppressWarnings("finally")
	public ArrayList<String> getGroupsByUser(int userID){
		ArrayList<String> trips = new ArrayList<String>();
		try {
			ResultSet rs = st.executeQuery("SELECT p.tripName FROM UserGroup ug, Trip p WHERE ug.userID = " + userID + " AND ug.tripID = p.tripID;");
			while (rs.next()){
				String trip = rs.getString("tripName");
				trips.add(trip);
			}
		} catch (SQLException sqle) {
			System.out.println("sqle in getGroupsByUser, " + sqle.getMessage());
		} finally {
			return trips;
		}
	}
	 
	/* delete a user_group relationship, called
	 * when a user leaves a group
	 */
	public void deleteUserGroup(int tripID, String username){
		int userID = getUserID(username);
		try {
			st.executeUpdate("DELETE FROM UserGroup WHERE tripID = " + tripID + " AND userID = " + userID + ";");
		} catch (SQLException sqle) {
			System.out.println("sqle in deleteUserGroup, " + sqle.getMessage());
		}
	}
	
	
	
	
	
	public static void main(String[] args){
		DatabaseTest a = new DatabaseTest();
		a.addUser("max the g", "mzeng", "Taixiang", "Zeng", "");
		a.addUser("a_arav", "amalpani", "Aarav", "Malpani","");
		a.addUser("bei", "bdeng", "Bei", "Deng", "");
		a.addUser("steph", "slee", "Stephanie", "Lee", "");
		/*
		System.out.println(a.validateUsername("bei"));
		System.out.println(a.validateUsername("sdfsdfsdfsd"));
		//a.match_user_group(1, 3);
		System.out.println(a.validateUsername("max the g"));
		System.out.println(a.validateUsername("a_arav"));
		System.out.println(a.validateUsername("steph"));
		System.out.println(a.getPassword("a_arav"));
		*/
		a.createNewGroup("trip to UCSB", true, "2017-05-09", "2017-08-09", "23:45", "");
		a.createNewGroup("trip to UCLA, sdfsdfsssssssssssssdsdsds", true, "2018-03-04", "2019-01-02", "", "fk Bruins");
		a.addUserGroup(2, "max the g");
		a.addUserGroup(3, "a_arav");
		a.addUserGroup(2, "a_arav");
		a.addUserGroup(1, "a_arav");
		a.addUserGroup(3, "bei");
		a.addUserGroup(3, "steph");
		a.addUserGroup(3, "bei");
		a.addUserGroup(3, "bei");
		a.addUserGroup(3, "bei");
		a.addDestination("Dehli", 3, -28.7041, 77.1025);
		a.addDestination("Shanghai", 3, 31.2304, -121.4737);
		a.addDestination("New York", 2, 24.987789, -90.34555);
		a.addSong(2, "Humble");
		a.addSong(2, "Bounce Back");
		a.addSong(1, "Side to Side");
		a.addSong(3, "Chandelier");
		a.addSong(3, "Love on the rain");
		a.addSong(1, "Shots");
		a.addSong(3, "Chandelier");
		a.addBudget(1, "tent", 100);
		a.addBudget(1, "transportation", 700);
		a.addBudget(3, "alc", 200);
		a.upvoteSong("Bad & Boujee", 1);
		a.upvoteSong("Bounce Back", 2);
		a.upvoteSong("Side to Side",1);
		a.updateBudgetAmount(3, "alc", 29);
		a.addUser("max the g", "dsfdfdf", "max", "zeng", "");
		a.deleteUserGroup(2, "max the g");
		System.out.println("lon: " + a.getFirstDesLon(2));
		System.out.println("lat: " + a.getFirstDesLat(2));
		ArrayList<String> b = a.getUsersFromGroup(3);
		System.out.println(a.getTripName(3) + "'s member are: ");
		for (String user : b)
			System.out.println(user);
		a.changeMeetingTime(3, "16:40");
		a.changeTripName(3, "Hogwartz");
		a.changeMeetingTime(3, "09:04:03");
		a.changeStartDate(3, "2001-07-31");
		a.changeEndDate(3, "2007-07-21");
		a.changePublic(3, true);
		a.changeComment(3, "The boy who lives");
		System.out.println(a.validateGroupName("trip to USC"));
		System.out.println(a.getGroupID("trip to USC"));
		System.out.println(a.getGroupID("triptoUSC"));
		System.out.println(a.getFirstDesLat(2));
		a.deleteBudget(80, "ac");
		a.addBudget(2, "tent", 1000);
		a.deleteSong(2, "Humble");
		a.deleteSong(3, "Humble");
		ArrayList<String> c = a.getGroupsByUser(1);
		System.out.println(a.getUserUsername(1) + "'s trips are: ");
		for (String trip : c)
			System.out.println(trip);
		ArrayList<Song> d = a.getSongQueue(1);
		System.out.println("getting song queue from 1");
		for (Song ele : d){
			System.out.println(ele.getName() + " / " + ele.getVotes());
		}
		Map<String, Integer> e = a.getBudgetList(4);
		for (Map.Entry<String, Integer> entry : e.entrySet()){
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		ArrayList<Destination> arr = a.getDestination(1);
		for (Destination element : arr){
			System.out.println("destination is " + element.getDestination());
			System.out.println("lon is " + element.getLon());
			System.out.println("lat is " + element.getLat());
		}
		Map<Integer, String> f = a.getAllPublicGroup();
		for (Map.Entry<Integer, String> entry : f.entrySet()){
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}
		System.out.println(a.getTripName(3));
		System.out.println(a.getMeetingTime(3));
		System.out.println(a.getStartDate(3));
		System.out.println(a.getEndDate(3));
		System.out.println(a.getPublic(3));
		System.out.println(a.getComment(3));
		System.out.println(a.checkIfDestinationExist(1, -128.2444, 44.054));
		System.out.println(a.checkIfDestinationExist(1, 34.054935, -118.2444759));
		System.out.println(a.addBudget(1, "food", 23));
		System.out.println(a.addSong(1, "Bad & Boujee"));
		System.out.println(a.addBudget(1, "fuck", 23));
		System.out.println(a.addSong(1, "Bad and Boujee"));
	}
}
