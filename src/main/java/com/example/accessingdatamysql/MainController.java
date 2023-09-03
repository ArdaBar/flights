package com.example.accessingdatamysql;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@RestController
public class MainController {

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;

    public MainController(AirportRepository airportRepository, FlightRepository flightRepository, FlightService flightService) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
        this.flightService = flightService;}

    @GetMapping("/")
    public String index() {
        return "Hello!";
    }

    @GetMapping("/airports")
    public Iterable<Airport> getAllAirports() {
        return airportRepository.findAll();
    }
    @GetMapping("/flights")
    public Iterable<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    @PostMapping("/airports/{city}")
    public String createAirport(@PathVariable String city) {
        Airport n = new Airport();
        n.setCity(city);
        airportRepository.save(n);
        return "Saved";
    }

    @PostMapping("/flights/{from}/{to}/{date}/{time}/{price}")
    public String createFlight(@PathVariable Long from,
                               @PathVariable Long to,
                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                               @PathVariable @DateTimeFormat(pattern = "HH:mm:ss") LocalTime time,
                               @PathVariable Long price) {
        return flightService.addFlight(from, to, date, time, price);
    }

    @PostMapping("/flights")
    public String createFlight(
            @RequestParam Long fromAirportId,
            @RequestParam Long toAirportId,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam Long price) {

        LocalDate da = LocalDate.parse(date);
        LocalTime ti = LocalTime.parse(time);
        return flightService.addFlight(fromAirportId, toAirportId, da, ti, price);
    }

    @GetMapping("/airports/{id}")
    public ResponseEntity<?> getAirport(@PathVariable Long id)
    {
        Optional<Airport> optionalAirport = airportRepository.findById(id);
        if ((optionalAirport).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalAirport.get());}

    @GetMapping("/flights/{id}")
    public ResponseEntity<?> getFlight(@PathVariable Long id)
    {
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if ((optionalFlight).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalFlight.get());
    }

    @PutMapping("/airports/{id}/{city}")
    public String updateAirport(@PathVariable Long id, @PathVariable String city) {
        Optional<Airport> optionalAirport = airportRepository.findById(id);
        if ((optionalAirport).isEmpty()) {
            return "Airport not Found";
        }
        Airport airport = optionalAirport.get();
        airport.setCity(city);
        airportRepository.save(airport);
        return "Updated";
    }

    @PutMapping("/flights")
    public String updateFlight(@RequestParam Long id,
                               @RequestParam Long from,
                               @RequestParam Long to,
                               @RequestParam String date,
                               @RequestParam String time,
                               @RequestParam Long price) {

        LocalDate da = LocalDate.parse(date);
        LocalTime ti = LocalTime.parse(time);

        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if ((optionalFlight).isEmpty()) {
            return "Flight not Found";
        }

        Flight flight = optionalFlight.get();
        Optional<Airport> optionalAirport1 = airportRepository.findById(from);
        Optional<Airport> optionalAirport2 = airportRepository.findById(to);
        if ((optionalAirport1).isEmpty() || (optionalAirport2).isEmpty()) {
            return "Airport not Found";
        }
        flight.setFromAirport(optionalAirport1.get());
        flight.setToAirport(optionalAirport2.get());
        flight.setTime(ti);
        flight.setDate(da);
        flight.setPrice(price);
        flightRepository.save(flight);
        return "Updated";
    }

    @DeleteMapping("/airports/{id}")
    public String deleteAirport(@PathVariable Long id) {
        Optional<Airport> optionalAirport = airportRepository.findById(id);
        if ((optionalAirport).isEmpty()) {
            return "Airport not Found";
        }
        Airport airport = optionalAirport.get();
        airportRepository.delete(airport);
        return "Deleted";
    }

    @DeleteMapping("/flights/{id}")
    public String deleteFlight(@PathVariable Long id) {
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if ((optionalFlight).isEmpty()) {
            return "Flight not Found";
        }
        Flight flight = optionalFlight.get();
        flightRepository.delete(flight);
        return "Deleted";
        }

    @GetMapping("/flights/search")
    public ResponseEntity<Flight> searchFlights(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        List<Flight> flights = flightRepository.findByFromAirport_IdAndToAirport_IdAndDate(from,
                to, date);
        if(flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flights.get(0));
    }

    @GetMapping(value="/flights/search", params="dateStr2")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam String dateStr1,
            @RequestParam String dateStr2
    ) {
        LocalDate date1 = LocalDate.parse(dateStr1);
        LocalDate date2 = LocalDate.parse(dateStr2);

        List<Flight> flights1 = flightRepository.findByFromAirport_IdAndToAirport_IdAndDate(from,
                    to, date1);
        List<Flight> flights2 = flightRepository.findByFromAirport_IdAndToAirport_IdAndDate(to,
                    from, date2);
        if(flights1.isEmpty() || flights2.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Arrays.asList(flights1.get(0), flights2.get(0)));
    }


}


