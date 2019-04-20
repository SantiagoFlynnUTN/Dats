package sample.Model.FileUtils;

import sample.Model.IndexedObj;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {

    private List<IndexedObj> objects;
    private List<String> fileHead;
    private String currentFileName;

    private static final FileParser INSTANCE = new FileParser();

    private FileParser() {}

    public static FileParser getInstance() {
        return INSTANCE;
    }

    public List<String> parseObjectsFromDataStream(String dataStream){

        List<String> records;
        List<String> objects = new ArrayList<>();

        records = Arrays.stream(dataStream.split("\\n")).collect(Collectors.toList());

        int i = 0;
        String line = records.get(i);

        while(line != null && i < records.size()){

            if(line.contains("[")){

                String object = line;
                i++;
                line = i < records.size() ? records.get(i): null;

                while(line != null && !line.contains("[")){
                    object = object.concat("\n" + line);
                    i++;
                    line = i < records.size() ? records.get(i): null;
                }

                object = object.concat("\n");

                objects.add(object);

            } else {
                i++;
                line = i < records.size() ? records.get(i): null;
            }
        }

        return objects;
    }

    public void parseObjectsFromFile(String filename) {

        int i = 0;
        boolean isReadingHeader = true;

        currentFileName = filename;
        try {
            FileManager.getInstance().readFileAsListOfStrings(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> records = FileManager.getInstance().getRecords();

        String line = records.get(i);
        objects = new ArrayList<>();
        fileHead = new ArrayList<>();

        while(line != null && i < records.size()){

            if(line.contains("[")){

                isReadingHeader = false;

                IndexedObj object = new IndexedObj();
                object.verbose = line;
                i++;
                line = i < records.size() ? records.get(i): null;

                while(line != null && !line.contains("[")){
                    object.verbose = object.verbose.concat("\n" + line);
                    i++;
                    line = i < records.size() ? records.get(i): null;
                }

                object.verbose = object.verbose.concat("\n");

                objects.add(object);

            } else if(isReadingHeader){
                fileHead.add(line + "\n");
                i++;
                line = i < records.size() ? records.get(i): null;
            } else {
                i++;
                line = i < records.size() ? records.get(i): null;
            }
        }
    }

    public void persistObjectsOnFile(){
        try {
            FileManager.getInstance().generateFileWithChanges(fileHead, objects, currentFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<IndexedObj> getObjects() {
        return objects;
    }

}
