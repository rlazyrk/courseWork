package ua.lviv.iot.algo.part1.courseWork.fileStorage;

import com.opencsv.CSVWriter;
import ua.lviv.iot.algo.part1.courseWork.modelDTO.ParkingDTO;
import ua.lviv.iot.algo.part1.courseWork.models.Item;
import ua.lviv.iot.algo.part1.courseWork.models.Parking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class StorageHelper {

    public void write(final Item item, final String fileName) {
        String directory = "src" + File.separator
                + "main" + File.separator
                + "resources" + File.separator
                + item.getClass().getSimpleName();
        String file;
        if (fileName.equals("")) {
            String fileNameTemp = item.getClass().getSimpleName()
                    + "-"
                    + getDate() + ".csv";
            file = directory + File.separator + fileNameTemp;
        } else {
            file = fileName;
        }
        boolean exists = new File(file).exists();

        try {
            if (!exists) {
                Files.createDirectories(Path.of(directory));
                try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
                    writer.writeNext(item.getHeaders().split(","));
                }
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
                if (item instanceof Parking) {
                    ParkingDTO tempObject = new ParkingDTO((Parking) item);
                    writer.writeNext(tempObject.toCSV().split(","));
                } else {
                    writer.writeNext(item.toCSV().split(","));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static String getDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public List<String> getAllFilePath(final String directoryPath) {
        List<String> filePaths = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            collectFilePaths(directory, filePaths);
        }

        return filePaths;
    }

    private void collectFilePaths(final File directory, final List<String> filePaths) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filePaths.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    collectFilePaths(file, filePaths);
                }
            }
        }
    }

    public Integer getMaxId(final List<String> files) {

        int maxId = 0;

        for (String file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] fields = line.split(",");
                    int id = Integer.parseInt(fields[0].replaceAll("\"", ""));

                    if (id > maxId) {
                        maxId = id;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return maxId;
    }
}
