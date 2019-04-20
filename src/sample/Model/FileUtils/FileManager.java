package sample.Model.FileUtils;

import sample.Model.IndexedObj;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private List<String> records;

    private static final FileManager INSTANCE = new FileManager();

    //constructor es innecesario en este caso, sirve de recordatorio que es PRIVADO
    private FileManager() {}

    public static FileManager getInstance() {
        return INSTANCE;
    }


    /**
     * Open and read a file, and return the lines in the file as a list of
     * Strings.
     */
    public List<String> readFileAsListOfStrings(String filename) throws IOException {

        records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null)
        {
            records.add(line);
        }
        reader.close();

        return records;
    }

    public void generateFileWithChanges(List<String> fileHead, List<IndexedObj> objects, String fileName) throws IOException {

        String fileData = fileHead.stream().collect(Collectors.joining())
                .concat(objects.stream().map(o -> o.verbose).collect(Collectors.joining()));

        BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));

        bf.write(fileData);
        bf.flush();
        bf.close();
    }

    public void addToEndOfFile(String fileData, String fileName) throws IOException {

        FileParser.getInstance().persistObjectsOnFile();
        Files.write(Paths.get(fileName), fileData.getBytes(), StandardOpenOption.APPEND);
        FileParser.getInstance().parseObjectsFromFile(fileName);

    }

    public List<String> getRecords() {
        return records;
    }
}
