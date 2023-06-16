package ua.lviv.iot.algo.part1.courseWork.modelDTO;

import lombok.ToString;
import ua.lviv.iot.algo.part1.courseWork.models.Parking;

import java.util.List;

@ToString
public final class ParkingDTO {
    private final Integer id;
    private final String listOfParkingSpotId;
    private final String tradeNetwork;
    private final String address;

    public ParkingDTO(final Parking parking) {
        this.tradeNetwork = parking.getTradeNetwork();
        this.address = parking.getAddress();
        this.id = parking.getId();
        this.listOfParkingSpotId = convertListToString(parking.getListOfParkingSpotId());
    }

    public String convertListToString(final List<Integer> intList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < intList.size(); i++) {
            stringBuilder.append(intList.get(i));
            if (i < intList.size() - 1) {
                stringBuilder.append(";");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String toCSV() {
        return id + "," + listOfParkingSpotId + "," + tradeNetwork + "," + address;
    }
}
