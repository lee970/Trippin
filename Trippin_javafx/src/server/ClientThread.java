package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import application.Controller;

public class ClientThread extends Thread {
	
	private Socket s = null;
	private BufferedReader br;
	private PrintWriter pw;
	private String username;
	private Controller c;
	
	public ClientThread(String username, Controller c){
		super();
		try{
			s = new Socket("localhost", 6789);
			this.c = c;
			
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.username = username;
			
			pw.println(username);
			pw.flush();
			
			this.start();
			//this.start();
			
		} catch (IOException ioe) {
			//System.out.println("ioe in ChatThread: " + ioe.getMessage());
		} 
		
	}
	
	public String getUsername(){
		return username;
	}

	public void run(){
		try {
			while(true){
				String line = "";

				while (true){

					while(line != null){
						line = br.readLine();
						//System.out.println(line);
						c.addNotification(line);
					}
				}
			}
		} catch (IOException ioe){
			//System.out.println("ioe in ChatThread: " + ioe.getMessage());
		}
	}

	public void sendlineToServer(String line){
		pw.println(line);
		pw.flush();
	}
	
	
	public void killThread(){
		if(s != null){
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		this.username = null;
	}

	public void sendMessageToServer(String groupname, String action, String message){
		
		System.out.println("In the send function...");
		
		pw.println(groupname);
		pw.flush();
		
		pw.println(action);
		pw.flush();
		
		pw.println(message);
		pw.flush();
		
		pw.println(username);
		pw.flush();
	}
}
