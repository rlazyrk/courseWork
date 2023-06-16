package ua.lviv.iot.algo.part1.courseWork.fileStorage;


import org.junit.jupiter.api.*;
import ua.lviv.iot.algo.part1.courseWork.models.Ticket;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class TicketFileStorageTest {
    HashMap<Integer, Ticket> testMap = new HashMap<>();
    TicketFileStorage testTicketFileStorage = new TicketFileStorage();

    @BeforeEach
    void setUp() {
        Ticket ticket1 = new Ticket("AA1111AA", "1956-01-01T12:02:33", 1, 10);
        Ticket ticket2 = new Ticket("AA2222AA", "1956-01-01T12:02:33", 2, 20);
        Ticket ticket3 = new Ticket("AA3333AA", "1956-01-01T12:02:33", 3, 30);
        Ticket ticket4 = new Ticket("AA4444AA", "1956-01-01T12:02:33", 4, 40);
        Ticket ticket5 = new Ticket("AA5555AA", "1956-01-01T12:02:33", 5, 50);

        testMap.put(ticket1.getId(), ticket1);
        testMap.put(ticket2.getId(), ticket2);
        testMap.put(ticket3.getId(), ticket3);
        testMap.put(ticket4.getId(), ticket4);
        testMap.put(ticket5.getId(), ticket5);
    }

    @Test
    void readWriteTest() throws IOException {
        for (Ticket ticket : testMap.values()) {
            testTicketFileStorage.write(ticket, "src/test/resources/testTicket.csv");
        }
        HashMap<Integer, Ticket> actual = testTicketFileStorage.readFromFile("src/test/resources/testTicket.csv");
        List<Ticket> list1 = new ArrayList<>();
        List<Ticket> list2 = new ArrayList<>();
        list1.addAll(actual.values());
        list2.addAll(testMap.values());
        String expected = "[Ticket(carNumber=AA1111AA, arrivalTime=1956-01-01T12:02:33, id=1, durationInMinute=10)," +
                " Ticket(carNumber=AA2222AA, arrivalTime=1956-01-01T12:02:33, id=2, durationInMinute=20)," +
                " Ticket(carNumber=AA3333AA, arrivalTime=1956-01-01T12:02:33, id=3, durationInMinute=30)," +
                " Ticket(carNumber=AA4444AA, arrivalTime=1956-01-01T12:02:33, id=4, durationInMinute=40)," +
                " Ticket(carNumber=AA5555AA, arrivalTime=1956-01-01T12:02:33, id=5, durationInMinute=50)]";

        Assertions.assertEquals(list1.toString(),expected);

    }

    @AfterEach
    void deleteFile(){
        File file = new File("src/test/resources/testTicket.csv");
        file.delete();
    }

}