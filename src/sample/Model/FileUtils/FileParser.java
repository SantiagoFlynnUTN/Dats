package sample.Model.FileUtils;

import sample.Model.IndexedObj;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {

    private List<IndexedObj> objects;
    private List<String> fileHead;
    private String currentFileName;
    private FileManager fileManager;


    public FileParser(){
        fileManager = new FileManager();
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

        System.out.println(objects);

        return objects;
    }

    public void parseObjectsFromFile(String filename) throws Exception {

        int i = 0;
        boolean isReadingHeader = true;

        currentFileName = filename;
        FileManager fileManager = new FileManager();
        fileManager.readFileAsListOfStrings(filename);
        List<String> records = fileManager.getRecords();

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

        System.out.println(objects);

        fileManager.generateFileWithChanges(fileHead, objects, currentFileName);
    }

    public List<IndexedObj> getObjects() {
        return objects;
    }

}
