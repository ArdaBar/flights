package com.example.accessingdatamysql;

import java.time.LocalDate;
import java.time.LocalTime;

public class MockFlight {
    private final Long from;
    private final Long to;
    private final LocalDate date;
    private final LocalTime time;
    private final Long price;

    public MockFlight(Long from, Long to, LocalDate date, LocalTime time, Long price) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Long getPrice() {
        return price;
    }
}