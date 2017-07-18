package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
	BufferedReader br;
	PrintWriter pw;
	Server server;
	String username;
	Socket s = null;
	
	public ServerThread(Socket s, Server server, String username){
		this.username = username;
		this.s = s;
		try{
			this.server = server;
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			
			this.start();
			
		} catch (IOException ioe){
			System.out.println("ioe in ServerThread: "+ ioe.getMessage());
		}
	}
	
	public void sendMessage(String message){
		pw.println(message);
		pw.flush();
	}

	public String getUsername(){
		return username;
	}
	
	

	public void run(){
		try{

			String line = "";
			String groupname = "";
			String action = "";
			String sender ="";

			while (true){
				groupname = br.readLine();
				action = br.readLine();
				line = br.readLine();
				sender = br.readLine();
				
				if(line != null && groupname != null && action !=null && sender !=null){
			
					if(action.equals("AddUser")){
						server.sendMessageToUser(line, sender+" added you to the trip "+groupname+ " you can now see it in your trips! Enjoy Trippin");
					}
					else if(action.equals("AddSong")){
						DatabaseTest db = server.getDatabase();
						ArrayList<String> usernames = db.getUsersFromGroup(db.getGroupID(groupname));
						for(int i=0; i<usernames.size();i++){
							if(usernames.get(i).equals(sender)){
								
							}
							else {
								server.sendMessageToUser(usernames.get(i), sender+" added a new song: " + line + " in "+groupname+ " you can vote for it now!");
							}
						}
					} 
					else if(action.equals("AddBudget")){
						DatabaseTest db = server.getDatabase();
						ArrayList<String> usernames = db.getUsersFromGroup(db.getGroupID(groupname));
						for(int i=0; i<usernames.size();i++){
							if(usernames.get(i).equals(sender)){
								
							}
							else {
								server.sendMessageToUser(usernames.get(i), sender+" added/changed a budget item: " + line + " in "+groupname);
							}
						}
					}
					else if(action.equals("AddDestination")){
						DatabaseTest db = server.getDatabase();
						ArrayList<String> usernames = db.getUsersFromGroup(db.getGroupID(groupname));
						for(int i=0; i<usernames.size();i++){
							if(usernames.get(i).equals(sender)){
								
							}
							else {
								server.sendMessageToUser(usernames.get(i), sender+" added/changed a destination pin: " + line + " in "+groupname + " have a good Trip!");
							}
						}
					}
				}
			}
		} catch (IOException ioe){
			System.out.println("ioe in Server Thread: " + ioe.getMessage());
		}
	}

}
