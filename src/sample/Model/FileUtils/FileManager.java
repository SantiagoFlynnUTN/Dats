package sample.Model.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private List<String> records;


    /**
     * Open and read a file, and return the lines in the file as a list of
     * Strings.
     */
    public List<String> readFileAsListOfStrings(String filename) throws Exception {

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

    public void generateFileWithChanges(List<String> fileHead, List<FileParser.IndexedObj> objects, String fileName) throws IOException {

        String fileData = fileHead.stream().collect(Collectors.joining())
                .concat(objects.stream().map(o -> o.verbose).collect(Collectors.joining()));

        System.out.println(fileData);

        BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));

        bf.write(fileData);
        bf.flush();
        bf.close();
    }


    public List<String> getRecords() {
        return records;
    }
}
