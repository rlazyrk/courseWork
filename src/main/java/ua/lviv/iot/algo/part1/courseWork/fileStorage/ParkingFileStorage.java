package ua.lviv.iot.algo.part1.courseWork.fileStorage;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.part1.courseWork.models.Parking;


import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public final class ParkingFileStorage extends StorageHelper {
    public static final String DIRECTORY = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator
            + "parking" + File.separator;

    public HashMap<Integer, Parking> readFromFile(final String file) {
        HashMap<Integer, Parking> data = new HashMap<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withSkipLines(1).build()) {
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                Parking parking = new Parking();
                parking.setId(Integer.parseInt(row[0]));
                parking.setListOfParkingSpotId(getValuesOfIdList(row[1]));
                parking.setTradeNetwork(row[2]);
                parking.setAddress(row[3]);
                data.put(parking.getId(), parking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public HashMap<Integer, Parking> readAll() {
        HashMap<Integer, Parking> finalMap = new HashMap<>();
        for (String file : getAllFilePath(DIRECTORY)) {
            HashMap<Integer, Parking> tmpMap = readFromFile(file);
            finalMap.putAll(tmpMap);
        }
        return finalMap;
    }

    public static List<Integer> getValuesOfIdList(final String str) {
        if (str.equals("[]")) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        String cleanedStr = str.substring(1, str.length() - 1);
        String[] values = cleanedStr.split(";");
        for (String value : values) {
            list.add(Integer.parseInt(value.trim()));
        }
        return list;
    }

    public void rewriteById(final int id, final Parking parking) {

        if (readAll().containsKey(id)) {
            for (String filePath : getAllFilePath(DIRECTORY)) {
                if (readFromFile(filePath).containsKey(id)) {
                    HashMap<Integer, Parking> tempMap = readFromFile(filePath);
                    tempMap.replace(id, parking);
                    File tempFile = new File(filePath);
                    tempFile.delete();
                    for (Parking parking1 : tempMap.values()) {
                        write(parking1, filePath);
                    }
                }
            }
        }
    }

    public void deleteByID(final int id) {
        String tempString = getFileWithId(id);
        HashMap<Integer, Parking> tempMap = readFromFile(getFileWithId(id));
        tempMap.remove(id);
        File tempFile = new File(tempString);
        tempFile.delete();
        for (Parking parking1 : tempMap.values()) {
            write(parking1, tempString);
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

