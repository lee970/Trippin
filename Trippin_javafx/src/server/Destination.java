package server;

public class Destination {
	int groupID, order;
	String start_date, destination;
	double lon, lat;
	
	public Destination(int order, String destination, double lon, double lat){
		this.order = order;
		this.destination = destination;
		this.lon = lon; this.lat = lat;
	}

	public int getOrder() {
		return order;
	}
	
	public String getDestination() {
		return destination;
	}

	public double getLon() {
		return lon;
	}

	public double getLat() {
		return lat;
	}
}
