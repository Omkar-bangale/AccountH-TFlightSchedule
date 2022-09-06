package com.flightschedule.app.model;

import java.util.Date;

public class SearchResult {
	
	private String airlineName;
	private String flightName;
	private Date fromdate;
	private Date todate;
	private String source;
	private String destination;
	private int availableseats;
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getAvailableseats() {
		return availableseats;
	}
	public void setAvailableseats(int availableseats) {
		this.availableseats = availableseats;
	}
	public SearchResult(String airlineName, String flightName, Date fromdate, Date todate, String source,
			String destination, int availableseats) {
		super();
		this.airlineName = airlineName;
		this.flightName = flightName;
		this.fromdate = fromdate;
		this.todate = todate;
		this.source = source;
		this.destination = destination;
		this.availableseats = availableseats;
	}
	@Override
	public String toString() {
		return "SearchResult [airlineName=" + airlineName + ", flightName=" + flightName + ", fromdate=" + fromdate
				+ ", todate=" + todate + ", source=" + source + ", destination=" + destination + ", availableseats="
				+ availableseats + "]";
	}
	
	public SearchResult() {}

}
