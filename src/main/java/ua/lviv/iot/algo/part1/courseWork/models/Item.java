package ua.lviv.iot.algo.part1.courseWork.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class Item {
    public abstract String toCSV();

    public abstract String getHeaders();

    @JsonIgnore
    public List<ParkingSpot> getListOfParkingSpot() {
        return null;
    }
}
