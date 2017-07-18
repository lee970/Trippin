package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
	ServerSocket ss;
	Map<String, ServerThread> st;
	DatabaseTest db = new DatabaseTest();
	
	public Server(int port){
		ServerSocket ss = null;
		st = new HashMap<>();
		try{
			ss = new ServerSocket(port);
			while(true){
				System.out.println("Waiting for connections...");
				Socket s = ss.accept();
				System.out.println("Connection from " + s.getInetAddress());
				
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				
				String username = br.readLine();
				System.out.println("Got Username: "+username);
				ServerThread t = new ServerThread(s, this, username);
				st.put(username, t);
				//pw.println("Added username to the server: " + t.getUsername());
				//pw.flush();
			}
		} catch (IOException ioe){
			System.out.println("ioe in Server: " + ioe.getMessage());
		}
	}
	
	public DatabaseTest getDatabase(){
		return db;
	}
	
	public void sendMessageToUser(String username, String message){
		if(st.containsKey(username)){
			//System.out.println("system cointains the name..." + username);
			ServerThread t = st.get(username);
			t.sendMessage(message);
		}
	}
	
	public static void main(String [] args){
		new Server(6789);
	}
}
