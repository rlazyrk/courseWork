package ua.lviv.iot.algo.part1.courseWork.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.courseWork.fileStorage.ParkingSpotFileStorage;
import ua.lviv.iot.algo.part1.courseWork.models.ParkingSpot;
import ua.lviv.iot.algo.part1.courseWork.models.Ticket;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Getter
public final class ParkingSpotService {
    private Map<Integer, ParkingSpot> parkingSpotMap;

    private final ParkingSpotFileStorage parkingSpotFileStorage;
    private final TicketService ticketService;
    private final AtomicInteger maxId;

    @Autowired
    public ParkingSpotService(final ParkingSpotFileStorage parkingSpotFileStorage, final TicketService ticketService) {
        this.ticketService = ticketService;
        this.parkingSpotFileStorage = parkingSpotFileStorage;
        this.maxId = new AtomicInteger(parkingSpotFileStorage.getMaxId(parkingSpotFileStorage.
                getAllFilePath(ParkingSpotFileStorage.DIRECTORY)));
        this.parkingSpotMap = parkingSpotFileStorage.readAll();
    }

    public List<ParkingSpot> getAll() {
        return new LinkedList<>(parkingSpotMap.values());
    }

    public ParkingSpot getById(final int id) {
        return parkingSpotMap.get(id);
    }

    public ParkingSpot post(final ParkingSpot parkingSpot) {
        parkingSpot.setId(maxId.incrementAndGet());
        this.parkingSpotMap.put(parkingSpot.getId(), parkingSpot);
        parkingSpotFileStorage.write(parkingSpot, "");
        updateMap();
        return parkingSpot;
    }

    public ParkingSpot putByTicket(final int id, final Ticket ticket) {
        ParkingSpot parkingSpot = new ParkingSpot();
        ticket.setId(ticketService.getMaxId().incrementAndGet());
        ticketService.post(ticket);
        parkingSpot.setId(id);
        parkingSpot.setEmpty(false);
        parkingSpot.setCarNumber(ticket.getCarNumber());
        parkingSpot.setArrivalTime(ticket.getArrivalTime());
        parkingSpot.setUsageTimeInMinute(ticket.getDurationInMinute());
        this.parkingSpotMap.replace(id, parkingSpot);
        parkingSpotFileStorage.rewriteById(id, parkingSpot);
        updateMap();
        return parkingSpot;
    }

    public ParkingSpot put(final int id, final ParkingSpot parkingSpot) {
        parkingSpot.setId(id);
        this.parkingSpotMap.replace(id, parkingSpot);
        parkingSpotFileStorage.rewriteById(id, parkingSpot);
        updateMap();
        return parkingSpotMap.replace(id, parkingSpot);
    }

    public void delete(final int id) {
        parkingSpotFileStorage.deleteByID(id);
        updateMap();
    }

    public void updateMap() {
        this.parkingSpotMap = parkingSpotFileStorage.readAll();
    }
}
