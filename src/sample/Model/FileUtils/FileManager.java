package sample.Model.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private List<IndexedObj> objects;
    private List<String> records;
    private List<String> fileHead;
    private String currentFileName;

    public class IndexedObj{
        public String verbose;
        public int priority;
    }

    /**
     * Open and read a file, and return the lines in the file as a list of
     * Strings.
     */
    public List<String> readFileAsListOfStrings(String filename) throws Exception
    {

        currentFileName = filename;
        records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.ISO_8859_1));
        String line;
        while ((line = reader.readLine()) != null)
        {
            records.add(line);
        }
        reader.close();

        return records;
    }

    public void parseObjects() throws Exception {

        int i = 0;
        boolean isReadingHeader = true;

        String line = records.get(i);
        objects = new ArrayList<>();
        fileHead = new ArrayList<>();

        while(line != null && i < records.size()){

            if(line.contains("[")){

                isReadingHeader = false;

                IndexedObj object = new IndexedObj();
                object.verbose = line;
                i++;
                line = i < records.size() ? records.get(i): "";

                while(line.length() > 0 && !line.contains("[")){
                    object.verbose = object.verbose.concat("\n" + line);
                    i++;
                    line = i < records.size() ? records.get(i): "";
                }

                object.verbose = object.verbose.concat("\n\n");

                objects.add(object);

            } else if(isReadingHeader){
                fileHead.add(line + "\n");
                i++;
                line = i < records.size() ? records.get(i): "";
            } else {
                i++;
                line = i < records.size() ? records.get(i): "";
            }
        }

        System.out.println(objects);
        generateFileWithChanges();
    }

    public void generateFileWithChanges() throws IOException {

        String fileData = fileHead.stream().collect(Collectors.joining())
                .concat(objects.stream().map(o -> o.verbose).collect(Collectors.joining()));

        System.out.println(fileData);

        BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentFileName), StandardCharsets.UTF_8));

        bf.write(fileData);
        bf.flush();
        bf.close();
    }

    public List<IndexedObj> getObjects() {
        return objects;
    }
}
