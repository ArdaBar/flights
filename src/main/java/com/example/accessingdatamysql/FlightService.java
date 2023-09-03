package com.example.accessingdatamysql;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@Service
public class FlightService {
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public FlightService(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }
    public ResponseEntity<List<MockFlight>> getMockFlights() {
        // Generate mock flight data
        List<MockFlight> flights = Arrays.asList(
                new MockFlight(1L, 2L, LocalDate.of(2023, 9, 4), LocalTime.now(), 100L),
                new MockFlight(1L, 52L, LocalDate.of(2023, 9, 4), LocalTime.now(), 400L)
        );

        return ResponseEntity.ok(flights);
    }

    @Scheduled(cron = "0 0 0 * * *") // runs at midnight every day
    public void fetchFlightData() {
        ResponseEntity<List<MockFlight>> response = getMockFlights();
        List<MockFlight> mockFlights = response.getBody();
        for (MockFlight mockFlight : mockFlights) {
            addFlight(mockFlight.getFrom(), mockFlight.getTo(), mockFlight.getDate(),
                    mockFlight.getTime(), mockFlight.getPrice());
        }
    }
    public String addFlight(Long from, Long to, LocalDate date, LocalTime time, Long price) {
        Flight n = new Flight();
        Optional<Airport> optionalAirport1 = airportRepository.findById(from);
        Optional<Airport> optionalAirport2 = airportRepository.findById(to);
        if ((optionalAirport1).isEmpty() || (optionalAirport2).isEmpty()) {
            return "Airport not Found";
        }
        n.setFromAirport(optionalAirport1.get());
        n.setToAirport(optionalAirport2.get());
        n.setTime(time);
        n.setDate(date);
        n.setPrice(price);
        flightRepository.save(n);
        return "Created";
    }
}