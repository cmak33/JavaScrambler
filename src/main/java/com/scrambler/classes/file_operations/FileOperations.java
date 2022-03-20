package com.scrambler.classes.file_operations;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileOperations {
    private static final Logger LOGGER = Logger.getLogger(FileOperations.class.getName());

    private FileOperations(){}

    public static boolean tryWriteToFile(String path,String value){
        boolean isSuccessful = true;
        try(FileWriter writer = new FileWriter(path)){
            writer.write(value);
        }catch (IOException exception){
            isSuccessful = false;
            LOGGER.log(Level.WARNING,"could not write to file",exception);
        }
        return !isSuccessful;
    }

    public static Pair<Boolean,String> tryReadFile(String path){
        boolean isSuccessful = true;
        String readText;
        try{
            readText= Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        }catch(IOException exception){
            isSuccessful=false;
            readText=null;
            LOGGER.log(Level.WARNING,"could not read from file",exception);
        }
        return new Pair<>(isSuccessful,readText);
    }

    public static Properties loadProperties(String path){
        Properties properties = new Properties();
        try(FileInputStream inputStream = new FileInputStream(path)){
            properties.load(inputStream);
        }catch(IOException exception){
            LOGGER.log(Level.WARNING,"could not load properties",exception);
        }
        return properties;
    }
}
