package com.flightschedule.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.flightschedule.app.model.FlightSchedule;
import com.flightschedule.app.model.SearchResult;
import com.flightschedule.app.repository.ScheduleRepository;

@Service
public class FlightScheduleService {

	@Autowired
	private ScheduleRepository schedulerepo;
	

	@Autowired 
	private RestTemplate restTemplate;
	private static final String TOPIC="kafkaDemoAirline";
	private static List<FlightSchedule> l = new ArrayList<>();
	@KafkaListener(topics=TOPIC,groupId="group_id",containerFactory="userKafkaListnerFactory")
	public void Consume(FlightSchedule schedule)
	{
		l.add(schedule);
	}
	
	public List<FlightSchedule> getSchedulesByKafka()
	{
		
		return l;
	}
	
	public boolean isAirlineBlocked(String airlineName)
	{
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:9005/airline/status/"+airlineName, boolean.class);
		
	}
	
	public Integer areSeatsAvailable(String flightName)
	{
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://localhost:9006/flight/seatsavailable/"+flightName, Integer.class);
	}
	
	public List<SearchResult> searchforFlights(String source, String destination)
	{
		try {
		//9005  /airline/status/{airlineName}
		List<FlightSchedule> listofschedules = schedulerepo.findBySourceDestinationIn(source, destination);
		
		
		List<SearchResult> resultlist = new ArrayList<>();
		for(FlightSchedule schedule : listofschedules)
		{
			if(schedule.getSource().equals(source) && schedule.getDestination().equals(destination))
			{
			int temp=areSeatsAvailable(schedule.getFlightName());
		if(!isAirlineBlocked(schedule.getAirlineName()) && 
					temp>0) {
			SearchResult  result1= new SearchResult();
			
			result1.setAirlineName(schedule.getAirlineName());
			result1.setAvailableseats(temp);
			
			result1.setFlightName(schedule.getFlightName());
			
			result1.setSource(schedule.getSource());
			result1.setDestination(schedule.getDestination());
			result1.setFromdate(schedule.getFromdate());
			result1.setTodate(schedule.getTodate());
			resultlist.add(result1);
			}
			}
	
		}
		
		return resultlist;
		}
		catch(Exception e)
		{
			SearchFlightException.message="Unable to process search ";
		throw new SearchFlightException();
		}
	}
	
	public List<FlightSchedule> getallschedules()
	{
		return schedulerepo.findAll();
	}
	
	public boolean postSchedule(FlightSchedule schedule)
	{
		try {
			schedulerepo.save(schedule);
			return true;
			
		}catch(Exception e)
		{
			throw new RuntimeException("Error in adding the schedule "+e.getMessage());
		}
	}
	
	public String updateSchedule(FlightSchedule schedule, String flightName)
	{
		
		try {
			FlightSchedule s1= new FlightSchedule();
			s1=schedulerepo.findByFlightNameIn(flightName);
			s1.setSource(schedule.getSource());
			s1.setDestination(schedule.getDestination());
			s1.setFromdate(schedule.getFromdate());
			s1.setTodate(schedule.getTodate());
			s1.setMealtype(schedule.getMealtype());
			
			schedulerepo.save(s1);
			 return "Schedule updated successfully";
			
			
		}catch(Exception e)
		{
			throw new RuntimeException("Error in adding the schedule "+e.getMessage());
		}
	}
	
	public String deleteFlightSchedule(String flightName)
	{
		try {
			schedulerepo.deleteFlightScheduledata(flightName);
			return "Schedule removed successfully";
		}
		catch(Exception e){
			throw new RuntimeException("Error in deleting the schedule: "+e.getMessage());
		}
	}
	
}
