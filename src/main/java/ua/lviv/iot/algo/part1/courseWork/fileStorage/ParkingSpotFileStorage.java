package ua.lviv.iot.algo.part1.courseWork.fileStorage;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.part1.courseWork.models.ParkingSpot;


import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

@Component
public final class ParkingSpotFileStorage extends StorageHelper {
    public static final String DIRECTORY = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator
            + "parkingSpot" + File.separator;

    public HashMap<Integer, ParkingSpot> readFromFile(final String file) {
        HashMap<Integer, ParkingSpot> data = new HashMap<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withSkipLines(1).build()) {
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(Integer.parseInt(row[0]));
                parkingSpot.setEmpty(Boolean.parseBoolean(row[1]));
                parkingSpot.setDepartureTime(row[2]);
                parkingSpot.setArrivalTime(row[3]);
                parkingSpot.setCarNumber(row[4]);
                if (row[5].equals("null")) {
                    parkingSpot.setUsageTimeInMinute(null);
                } else {
                    parkingSpot.setUsageTimeInMinute(Integer.parseInt(row[5]));
                }

                data.put(parkingSpot.getId(), parkingSpot);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public HashMap<Integer, ParkingSpot> readAll() {
        HashMap<Integer, ParkingSpot> finalMap = new HashMap<>();
        for (String file : getAllFilePath(DIRECTORY)) {
            HashMap<Integer, ParkingSpot> tmpMap = readFromFile(file);
            finalMap.putAll(tmpMap);
        }
        return finalMap;
    }

    public ParkingSpot findById(final int id) {
        return readAll().get(id);
    }


    public void rewriteById(final int id, final ParkingSpot parkingSpot) {
        String tempString = getFileWithId(id);
        HashMap<Integer, ParkingSpot> tempMap = readFromFile(getFileWithId(id));
        tempMap.replace(id, parkingSpot);
        File tempFile = new File(tempString);
        tempFile.delete();
        for (ParkingSpot parkingSpot1 : tempMap.values()) {
            write(parkingSpot1, tempString);
        }
    }

    public void deleteByID(final int id) {
        String tempString = getFileWithId(id);
        HashMap<Integer, ParkingSpot> tempMap = readFromFile(getFileWithId(id));
        tempMap.remove(id);
        File tempFile = new File(tempString);
        tempFile.delete();
        for (ParkingSpot parkingSpot1 : tempMap.values()) {
            write(parkingSpot1, tempString);
        }
    }

    public String getFileWithId(final int id) {
        if (readAll().containsKey(id)) {
            for (String filePath : getAllFilePath(DIRECTORY)) {
                if (readFromFile(filePath).containsKey(id)) {
                    return filePath;
                }
            }
        }
        return null;
    }
}
