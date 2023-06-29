package ua.lviv.iot.algo.part1.courseWork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ua.lviv.iot.algo.part1.courseWork.models.Item;
import ua.lviv.iot.algo.part1.courseWork.models.Parking;
import ua.lviv.iot.algo.part1.courseWork.models.ParkingSpot;
import ua.lviv.iot.algo.part1.courseWork.service.ParkingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequestMapping("/parking")
public final class ParkingController {
    private final ParkingService parkingService;

    @Autowired
    public ParkingController(final ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public ResponseEntity<List<Parking>> getAll() {
        return ResponseEntity.ok(parkingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Item>> get(@PathVariable final int id) {
        if (parkingService.getParkingMap().containsKey(id)) {
            return ResponseEntity.ok(parkingService.getById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Parking> post(@RequestBody final Parking parking) {
        return ResponseEntity.ok(parkingService.post(parking));
    }

    @PostMapping("/{id}/parkingSpot")
    public ResponseEntity<List<Item>> postParkingSpotToParking(@RequestBody final ParkingSpot parkingSpot,
                                                     @PathVariable final int id) {
        if (!parkingService.getParkingMap().containsKey(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(parkingService.addParkingSpotToParking(id, parkingSpot));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parking> put(@RequestBody final Parking parking, @PathVariable final int id) {
        return ResponseEntity.ok(parkingService.put(id, parking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Parking> delete(@PathVariable final int id) {
        if (parkingService.getParkingMap().containsKey(id)) {
            parkingService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();

        }
    }
}
