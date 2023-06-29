package ua.lviv.iot.algo.part1.courseWork.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public final class Parking extends Item {
    private Integer id;
    private List<Integer> listOfParkingSpotId;
    private String tradeNetwork;
    private String address;

    @JsonIgnore
    public String getHeaders() {
        return "id, listOfParkingSpotId, tradeNetwork, address";
    }

    public String toCSV() {
        return id + "," + listOfParkingSpotId + "," + tradeNetwork + "," + address;
    }

}
