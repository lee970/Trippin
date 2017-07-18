package server;

public class Song {
	String name;
	int votes;
	
	public Song(String name){
		votes = 0;
		this.name = name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setVote(int votes){
		this.votes = votes;
	}
	
	public String getName(){
		return name;
	}
	public int getVotes(){
		return votes;
	}
}
