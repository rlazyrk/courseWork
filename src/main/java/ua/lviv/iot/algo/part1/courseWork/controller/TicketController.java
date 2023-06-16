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
import ua.lviv.iot.algo.part1.courseWork.models.Ticket;
import ua.lviv.iot.algo.part1.courseWork.service.TicketService;

import java.util.List;

@Controller
@RequestMapping("/ticket")
public final class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(ticketService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> get(@PathVariable final int id) {
        if (ticketService.getTicketMap().containsKey(id)) {
            return ResponseEntity.ok(ticketService.getById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Ticket> post(@Valid @RequestBody final Ticket ticket, final Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ticketService.post(ticket));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> put(@PathVariable final int id, @Valid @RequestBody final Ticket ticket,
                                      final Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        } else {
            ticketService.put(id, ticket);
            if (ticketService.put(id, ticket) == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ticket);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> delete(@PathVariable final int id) {
        if (!ticketService.getTicketMap().containsKey(id)) {
            return ResponseEntity.notFound().build();
        } else {
            ticketService.delete(id);
            return ResponseEntity.ok().build();
        }
    }
}
