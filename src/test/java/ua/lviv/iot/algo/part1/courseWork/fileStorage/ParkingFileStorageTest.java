package ua.lviv.iot.algo.part1.courseWork.fileStorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part1.courseWork.models.Parking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class ParkingFileStorageTest {
    HashMap<Integer, Parking> testMap = new HashMap<>();
    ParkingFileStorage testParkingFileStorage = new ParkingFileStorage();

    @BeforeEach
    void setUp() {
        List<Integer> listForParking1 = new ArrayList<>();
        listForParking1.add(1);
        List<Integer> listForParking2 = new ArrayList<>();
        listForParking2.add(1);
        listForParking1.add(2);
        List<Integer> listForParking3 = new ArrayList<>();
        listForParking3.add(1);
        listForParking3.add(2);
        listForParking3.add(3);
        List<Integer> listForParking4 = new ArrayList<>();
        listForParking4.add(1);
        listForParking4.add(2);
        listForParking4.add(3);
        listForParking4.add(4);
        List<Integer> listForParking5 = new ArrayList<>();
        listForParking5.add(1);
        listForParking5.add(2);
        listForParking5.add(3);
        listForParking5.add(4);
        listForParking5.add(5);
        Parking parking1 = new Parking(1,listForParking1,"Forum","Pid Dubom sreet");
        Parking parking2 = new Parking(2,  listForParking2, "Ashan", "Sokilnyky vilage");
        Parking parking3 = new Parking(3,  listForParking3, "Metro", "George Washington Street");
        Parking parking4 = new Parking(4,  listForParking4, "Epicentr", "Sokilnyky vilage");
        Parking parking5 = new Parking(5,  listForParking5, "Rukavychka", "Horodotska street");

        testMap.put(parking1.getId(), parking1);
        testMap.put(parking2.getId(), parking2);
        testMap.put(parking3.getId(), parking3);
        testMap.put(parking4.getId(), parking4);
        testMap.put(parking5.getId(), parking5);
    }

    @Test
    void readWriteTest() throws IOException {
        for (Parking parking : testMap.values()) {
            testParkingFileStorage.write(parking, "src/test/resources/testTicket.csv");
        }
        HashMap<Integer, Parking> actual = testParkingFileStorage.readFromFile("src/test/resources/testTicket.csv");
        List<Parking> list1 = new ArrayList<>();
        List<Parking> list2 = new ArrayList<>();
        list1.addAll(actual.values());
        list2.addAll(testMap.values());
        String expected = "[Parking(id=1, listOfParkingSpotId=[1, 2], tradeNetwork=Forum, address=Pid Dubom sreet)," +
                " Parking(id=2, listOfParkingSpotId=[1], tradeNetwork=Ashan, address=Sokilnyky vilage)," +
                " Parking(id=3, listOfParkingSpotId=[1, 2, 3], tradeNetwork=Metro, address=George Washington Street)," +
                " Parking(id=4, listOfParkingSpotId=[1, 2, 3, 4], tradeNetwork=Epicentr, address=Sokilnyky vilage)," +
                " Parking(id=5, listOfParkingSpotId=[1, 2, 3, 4, 5], tradeNetwork=Rukavychka, address=Horodotska street)]";

        Assertions.assertEquals(list1.toString(), expected);

    }

    @AfterEach
    void deleteFile() {
        File file = new File("src/test/resources/testTicket.csv");
        file.delete();
    }

}