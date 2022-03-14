package com.example.javaproject.classes.fileOperations;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReaderWriter {

    private FileReaderWriter(){}

    public static boolean tryWriteToFile(String path,String value){
        boolean isSuccessful = true;
        try(FileWriter writer = new FileWriter(path)){
            writer.write(value);
        }catch (IOException exception){
            isSuccessful = false;
        }
        return isSuccessful;
    }

    public static Pair<Boolean,String> tryReadFile(String path){
        boolean isSuccessful = true;
        String readText;
        try{
            readText= Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        }catch(IOException exception){
            isSuccessful=false;
            readText=null;
        }
        return new Pair<>(isSuccessful,readText);
    }
}
