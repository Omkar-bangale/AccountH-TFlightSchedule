package com.flightschedule.app.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="scheduledata")
public class FlightSchedule {
	
	private String flightName;
	private String airlineName;
	
	@Id
	private String scheduleId;
	private String source;
	private String destination;
	private Date fromdate;
	private Date todate;
	private String mealtype;
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
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
	public String getMealtype() {
		return mealtype;
	}
	public void setMealtype(String mealtype) {
		this.mealtype = mealtype;
	}
	@Override
	public String toString() {
		return "FlightSchedule [flightName=" + flightName + ", airlineName=" + airlineName + ", scheduleId="
				+ scheduleId + ", source=" + source + ", destination=" + destination + ", fromdate=" + fromdate
				+ ", todate=" + todate + ", mealtype=" + mealtype + "]";
	}
	public FlightSchedule(String flightName, String airlineName, String scheduleId, String source, String destination,
			Date fromdate, Date todate, String mealtype) {
		super();
		this.flightName = flightName;
		this.airlineName = airlineName;
		this.scheduleId = scheduleId;
		this.source = source;
		this.destination = destination;
		this.fromdate = fromdate;
		this.todate = todate;
		this.mealtype = mealtype;
	}
	
	public FlightSchedule()

	{}
	
	
	

}
