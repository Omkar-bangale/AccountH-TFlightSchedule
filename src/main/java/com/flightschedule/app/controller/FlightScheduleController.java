package com.flightschedule.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flightschedule.app.model.FlightSchedule;
import com.flightschedule.app.model.SearchResult;
import com.flightschedule.app.services.FlightScheduleService;

@RestController
public class FlightScheduleController {
	
	@Autowired
	private FlightScheduleService scheduleservice;
	
	@Autowired
	private KafkaTemplate<String, FlightSchedule> kafkaTemplate;
	
	private static final String topic="kafkaDemoAirline";
	@PostMapping("/flightschedule/publish")
	public String publishSchedule(@RequestBody FlightSchedule schedule)
	{try {
		kafkaTemplate.send(topic, schedule);
		
		return "Schedule published successfully";
	}
	catch(Exception e)
	{
		throw new RuntimeException("SOmething went wrong while publishing "+e.getMessage());
	}
	}
	
	@GetMapping("/flightschedule/checkschedules")
	public List<FlightSchedule> getSchedules()
	{
		return scheduleservice.getSchedulesByKafka();
	}
	
	@GetMapping("/flightschedule/getall")
	public List<FlightSchedule> gealltFlightsSchedule(@RequestHeader Map<String,String> headers)
	{
		FlightScheduleController.validateToken(headers);
		if(validated)
			return scheduleservice.getallschedules();
		else
		{
			TokenValidationException.message="Token Validation failed";
			throw new TokenValidationException();
		}
	}
	
	@PostMapping("/flightschedule/add")
	public String addSchedule(@RequestHeader Map<String,String> headers, @RequestBody FlightSchedule flightschedule)
	{
		FlightScheduleController.validateToken(headers);
		if(validated)
		{
			if(scheduleservice.postSchedule(flightschedule))
			return "FlightSchedule added successfully ";
			else
				return"Error in adding the schedule";
		}
		else
			throw new RuntimeException("Token Validation failed");
		}
	
	@PutMapping("/flightschedule/update")
	public String updateFlightSchedule(@RequestHeader Map<String,String> headers, @RequestBody FlightSchedule flightschedule, @RequestParam String flightName)
	{
		FlightScheduleController.validateToken(headers);
		if(validated)
		{
		return scheduleservice.updateSchedule(flightschedule, flightName);
		}
		else
			throw new RuntimeException("Token Validation failed");
		}
	
	@DeleteMapping("/flightschedule/remove/{flightName}")
	public String removeFlightSchedule(@RequestHeader Map<String,String> headers, @PathVariable String flightName) 
	{
		FlightScheduleController.validateToken(headers);
		if(validated)
		{
		return scheduleservice.deleteFlightSchedule(flightName);
		}
		else
			throw new RuntimeException("Token Validation failed");
		}
	
	@PostMapping("/flightschedule/getflights/{source}/{destination}")
	public List<SearchResult> searchBasedonSourceandDestination(@RequestHeader Map<String,String> headers,@PathVariable String source, @PathVariable String destination)
	{
		FlightScheduleController.validateToken(headers);
		if(validated)
		{
			return scheduleservice.searchforFlights(source, destination);
		}
		else {
			throw new RuntimeException("Token Validation failed ");
		}
		
	}
	
	private static boolean validated=false;
	
	public  static void validateToken (Map<String, String> header)
	{
	
		String token="";
		for(String key : header.keySet())
		{
			if(key.equals("authorization"))
				token=header.get(key);
		}
		HttpHeaders httpheader = new HttpHeaders();
		httpheader.set("Authorization", token);
		HttpEntity<Void> requestentity = new HttpEntity<>(httpheader);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> response = restTemplate.exchange("http://localhost:9004/validatejwt",HttpMethod.GET, requestentity,boolean.class);
		validated=response.getBody().booleanValue();
	}


}
