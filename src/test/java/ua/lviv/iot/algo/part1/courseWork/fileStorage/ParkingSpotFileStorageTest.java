package ua.lviv.iot.algo.part1.courseWork.fileStorage;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part1.courseWork.models.ParkingSpot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ParkingSpotFileStorageTest {
    HashMap<Integer, ParkingSpot> testMap = new HashMap<>();
    ParkingSpotFileStorage testParkingSpotFileStorage = new ParkingSpotFileStorage();

    @BeforeEach
    void setUp() {
        ParkingSpot parkingSpot1 = new ParkingSpot(1, true, "2001-01-01T12:02:33",
                "1951-01-01T12:02:33", "AA1111AA", 10);
        ParkingSpot parkingSpot2 = new ParkingSpot(2, false, "2002-01-01T12:02:33",
                "1952-01-01T12:02:33", "AA2222AA", 20);
        ParkingSpot parkingSpot3 = new ParkingSpot(3, true, "2003-01-01T12:02:33",
                "1953-01-01T12:02:33", "AA3333AA", 30);
        ParkingSpot parkingSpot4 = new ParkingSpot(4, false, "2004-01-01T12:02:33",
                "1954-01-01T12:02:33", "AA4444AA", 40);
        ParkingSpot parkingSpot5 = new ParkingSpot(5, true, "2005-01-01T12:02:33",
                "1955-01-01T12:02:33", "AA5555AA", 50);

        testMap.put(parkingSpot1.getId(), parkingSpot1);
        testMap.put(parkingSpot2.getId(), parkingSpot2);
        testMap.put(parkingSpot3.getId(), parkingSpot3);
        testMap.put(parkingSpot4.getId(), parkingSpot4);
        testMap.put(parkingSpot5.getId(), parkingSpot5);
    }

    @Test
    void readWriteTest() throws IOException {
        for (ParkingSpot parkingSpot : testMap.values()) {
            testParkingSpotFileStorage.write(parkingSpot, "src/test/resources/testTicket.csv");
        }
        HashMap<Integer, ParkingSpot> actual = testParkingSpotFileStorage.readFromFile("src/test/resources/testTicket.csv");
        List<ParkingSpot> list1 = new ArrayList<>();
        List<ParkingSpot> list2 = new ArrayList<>();
        list1.addAll(actual.values());
        list2.addAll(testMap.values());
        String expected = "[ParkingSpot(id=1, isEmpty=true, departureTime=2001-01-01T12:02:33," +
                " arrivalTime=1951-01-01T12:02:33, carNumber=AA1111AA, usageTimeInMinute=10)," +
                " ParkingSpot(id=2, isEmpty=false, departureTime=2002-01-01T12:02:33," +
                " arrivalTime=1952-01-01T12:02:33, carNumber=AA2222AA, usageTimeInMinute=20)," +
                " ParkingSpot(id=3, isEmpty=true, departureTime=2003-01-01T12:02:33, arrivalTime=1953-01-01T12:02:33," +
                " carNumber=AA3333AA, usageTimeInMinute=30), ParkingSpot(id=4, isEmpty=false," +
                " departureTime=2004-01-01T12:02:33, arrivalTime=1954-01-01T12:02:33, carNumber=AA4444AA," +
                " usageTimeInMinute=40), ParkingSpot(id=5, isEmpty=true, departureTime=2005-01-01T12:02:33," +
                " arrivalTime=1955-01-01T12:02:33, carNumber=AA5555AA, usageTimeInMinute=50)]";

        Assertions.assertEquals(list1.toString(), expected);

    }

    @AfterEach
    void deleteFile() {
        File file = new File("src/test/resources/testTicket.csv");
        file.delete();
    }

}