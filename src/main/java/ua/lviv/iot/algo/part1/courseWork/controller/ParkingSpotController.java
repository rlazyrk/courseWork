package ua.lviv.iot.algo.part1.courseWork.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.lviv.iot.algo.part1.courseWork.models.Item;
import ua.lviv.iot.algo.part1.courseWork.models.ParkingSpot;
import ua.lviv.iot.algo.part1.courseWork.models.Ticket;
import ua.lviv.iot.algo.part1.courseWork.service.ParkingSpotService;
import ua.lviv.iot.algo.part1.courseWork.service.TicketService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/parkingSpot")
public final class ParkingSpotController {
    private final ParkingSpotService parkingSpotService;
    private final TicketService ticketService;

    @Autowired
    public ParkingSpotController(final ParkingSpotService parkingSpotService, final TicketService ticketService) {
        this.parkingSpotService = parkingSpotService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> getAll() {
        return ResponseEntity.ok(parkingSpotService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> get(@PathVariable final int id) {
        if (parkingSpotService.getParkingSpotMap().containsKey(id)) {
            return ResponseEntity.ok(parkingSpotService.getById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> post(@Valid @RequestBody final ParkingSpot parkingSpot, final Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        parkingSpotService.post(parkingSpot);
        return ResponseEntity.ok(parkingSpot);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpot> put(@PathVariable final int id,
                                           @Valid @RequestBody final ParkingSpot parkingSpot, final Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        } else {
            parkingSpotService.put(id, parkingSpot);
            if (parkingSpotService.put(id, parkingSpot) == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(parkingSpot);
        }
    }

    @PutMapping("/{id}/ticket")
    public ResponseEntity<List<Item>> putWithTicket(@PathVariable final int id,
                                                     @Valid @RequestBody final Ticket ticket, final Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        } else {
            if (!parkingSpotService.getParkingSpotMap().containsKey(id)) {
                return ResponseEntity.notFound().build();
            }
            ticketService.post(ticket);
            List<Item> tempList = new ArrayList<>();
            tempList.add(ticket);
            parkingSpotService.putByTicket(id, ticket);
            tempList.add(parkingSpotService.putByTicket(id, ticket));
            return ResponseEntity.ok(tempList);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingSpot> delete(@PathVariable final int id) {
        if (!parkingSpotService.getParkingSpotMap().containsKey(id)) {
            return ResponseEntity.notFound().build();
        } else {
            parkingSpotService.delete(id);
            return ResponseEntity.ok().build();

        }
    }
}
