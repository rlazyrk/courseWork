package ua.lviv.iot.algo.part1.courseWork.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part1.courseWork.fileStorage.TicketFileStorage;
import ua.lviv.iot.algo.part1.courseWork.models.Ticket;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Service
public final class TicketService {
    private HashMap<Integer, Ticket> ticketMap;

    private final TicketFileStorage ticketFileStorage;

    private final AtomicInteger maxId;

    @Autowired
    public TicketService(final TicketFileStorage ticketFileStorage) throws IOException {
        this.ticketFileStorage = ticketFileStorage;
        this.maxId = new AtomicInteger(ticketFileStorage.getMaxId(ticketFileStorage.
                getAllFilePath(TicketFileStorage.DIRECTORY)));
        this.ticketMap = ticketFileStorage.readAll();
    }

    public List<Ticket> getAll() {
        return new LinkedList<>(ticketMap.values());
    }

    public Ticket getById(final int id) {
        return ticketMap.get(id);
    }

    public Ticket post(final Ticket ticket) {
        ticket.setId(maxId.incrementAndGet());
        ticketMap.put(ticket.getId(), ticket);
        ticketFileStorage.write(ticket, "");
        updateMap();
        return ticket;
    }

    public Ticket put(final int id, final Ticket ticket) {
        ticket.setId(id);
        ticketMap.replace(id, ticket);
        ticketFileStorage.rewriteById(id, ticket);
        updateMap();
        return ticketMap.replace(id, ticket);
    }

    public void delete(final int id) {
        ticketFileStorage.deleteByID(id);
        updateMap();
    }

    public void updateMap() {
        this.ticketMap = ticketFileStorage.readAll();
    }
}
