package ua.lviv.iot.algo.part1.courseWork.fileStorage;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import ua.lviv.iot.algo.part1.courseWork.models.Ticket;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

@Component
public final class TicketFileStorage extends StorageHelper {
    public static final String DIRECTORY = "src" + File.separator
            + "main" + File.separator
            + "resources" + File.separator
            + "ticket" + File.separator;

    public HashMap<Integer, Ticket> readFromFile(final String file) {
        HashMap<Integer, Ticket> data = new HashMap<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withSkipLines(1).build()) {
            List<String[]> allData = csvReader.readAll();
            for (String[] row : allData) {
                Ticket ticket = new Ticket();
                ticket.setId(Integer.parseInt(row[0]));
                ticket.setCarNumber(row[1]);
                ticket.setArrivalTime(row[2]);
                ticket.setDurationInMinute(Integer.parseInt(row[3]));
                data.put(ticket.getId(), ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public HashMap<Integer, Ticket> readAll() {
        HashMap<Integer, Ticket> finalMap = new HashMap<>();
        for (String file : getAllFilePath(DIRECTORY)) {
            HashMap<Integer, Ticket> tmpMap = readFromFile(file);
            finalMap.putAll(tmpMap);
        }
        return finalMap;
    }

    public void rewriteById(final int id, final Ticket ticket) {
        String tempString = getFileWithId(id);
        HashMap<Integer, Ticket> tempMap = readFromFile(getFileWithId(id));
        tempMap.replace(id, ticket);
        File tempFile = new File(tempString);
        tempFile.delete();
        for (Ticket ticket1 : tempMap.values()) {
            write(ticket1, tempString);
        }
    }

    public void deleteByID(final int id) {
        String tempString = getFileWithId(id);
        HashMap<Integer, Ticket> tempMap = readFromFile(getFileWithId(id));
        tempMap.remove(id);
        File tempFile = new File(tempString);
        tempFile.delete();
        for (Ticket ticket1 : tempMap.values()) {
            write(ticket1, tempString);
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
