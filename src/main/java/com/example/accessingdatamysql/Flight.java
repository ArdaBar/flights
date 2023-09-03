package com.example.accessingdatamysql;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_airport_id", referencedColumnName = "id")
    private Airport fromAirport;

    @ManyToOne//(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_airport_id", referencedColumnName = "id")
    private Airport toAirport;
    private LocalDate date;
    private LocalTime time;
    private Long price;

    public Flight() {
    }
    public Long getId() {
        return id;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}

