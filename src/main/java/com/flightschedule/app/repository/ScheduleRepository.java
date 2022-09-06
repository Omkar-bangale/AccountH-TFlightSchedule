package com.flightschedule.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.flightschedule.app.model.FlightSchedule;


public interface ScheduleRepository extends MongoRepository<FlightSchedule, String> {

	@Query(value="{flightName:'?0'}")
	FlightSchedule findByFlightNameIn(String flightName);
	
	@Query(value="{flightName:'?0'}", delete=true)
	void deleteFlightScheduledata(String flightName);
	
	
	@Query(value="$and :[{source:'?0'},{destination:'?1'}]")
	List<FlightSchedule> findBySourceDestinationIn(String source, String destination);
}
