package com.example.accessingdatamysql;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Long> {
    List<Flight> findByFromAirport_IdAndToAirport_IdAndDate(Long fromAirportId, Long toAirportId, LocalDate date);
}

