package ua.lviv.iot.algo.part1.courseWork.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.courseWork.fileStorage.ParkingFileStorage;
import ua.lviv.iot.algo.part1.courseWork.models.Item;
import ua.lviv.iot.algo.part1.courseWork.models.Parking;
import ua.lviv.iot.algo.part1.courseWork.models.ParkingSpot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Service
public final class ParkingService {
    private Map<Integer, Parking> parkingMap;
    private final ParkingSpotService parkingSpotService;
    private final ParkingFileStorage parkingFileStorage;
    private final AtomicInteger maxId;

    @Autowired
    public ParkingService(final ParkingSpotService parkingSpotService, final ParkingFileStorage parkingFileStorage) {
        this.parkingFileStorage = parkingFileStorage;
        this.parkingSpotService = parkingSpotService;
        this.maxId = new AtomicInteger(parkingFileStorage.getMaxId(parkingFileStorage
                .getAllFilePath(ParkingFileStorage.DIRECTORY)));
        this.parkingMap = parkingFileStorage.readAll();
    }

    public List<Parking> getAll() {
        return new LinkedList<>(parkingMap.values());
    }

    public List<Item> getById(final int id) {
        List<Item> tempList = new ArrayList<>();
        for (int i : parkingMap.get(id).getListOfParkingSpotId()) {
            if (parkingSpotService.getParkingSpotMap().containsKey(i)) {
                tempList.add(parkingSpotService.getById(i));
            }
        }
        tempList.add(parkingMap.get(id));
        return tempList;
    }

    public Parking post(final Parking parking) {
        List<Integer> parkingSpotId = new LinkedList<>();
        parkingSpotId.addAll(parking.getListOfParkingSpotId());
        parking.setListOfParkingSpotId(parkingSpotId);
        parking.setId(maxId.incrementAndGet());
        this.parkingMap.put(parking.getId(), parking);
        parkingFileStorage.write(parking, "");
        updateMap();
        return parking;
    }

    public Parking put(final int id, final Parking parking) {
        parking.setId(id);
        this.parkingMap.replace(id, parking);
        parkingFileStorage.rewriteById(id, parking);
        updateMap();
        return parking;
    }

    public List<Item> addParkingSpotToParking(final int id, final ParkingSpot parkingSpot) {
        List<Item> tempList = new ArrayList<>();
        ParkingSpot spot = parkingSpotService.post(parkingSpot);
        parkingMap.get(id).getListOfParkingSpotId().add(spot.getId());
        tempList.add(spot);
        tempList.add(parkingMap.get(id));
        updateMap();
        return tempList;
    }

    public void delete(final int id) {
        parkingFileStorage.deleteByID(id);
        updateMap();
    }

    public void updateMap() {
        this.parkingMap = parkingFileStorage.readAll();
    }
}

