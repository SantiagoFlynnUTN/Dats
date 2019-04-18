package sample.Model.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    /**
     * Open and read a file, and return the lines in the file as a list of
     * Strings.
     */
    public static List<String> readFileAsListOfStrings(String filename) throws Exception
    {
        List<String> records = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null)
        {
            records.add(line);
        }
        reader.close();

        records.forEach(System.out::println);
        return records;
    }

}
