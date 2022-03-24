package com.scrambler.tests;

import com.scrambler.classes.file_operations.FileOperations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileOperationsTest {
    private static final Logger LOGGER = Logger.getLogger(FileOperationsTest.class.getName());
    private File testFile;
    private File propertiesFile;
    private static final String PROPERTY_NAME = "test";
    private static final String PROPERTY_VALUE = "testValue";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    @Before
    public void setUp(){
        try {
            testFile = temporaryFolder.newFile("testFile.txt");
            propertiesFile = temporaryFolder.newFile("prop.properties");
            writePropertyToFile(propertiesFile, PROPERTY_NAME, PROPERTY_VALUE);
        }catch (IOException exception){
            LOGGER.log(Level.SEVERE,"could not create files",exception);
        }
    }

    private void writePropertyToFile(File file,String propertyName,String propertyValue) throws IOException{
        Path path = Paths.get(file.getPath());
        String strToWrite = String.format("%s = %s",propertyName,propertyValue);
        Files.writeString(path,strToWrite);
    }

    @Test
    public void writeToClosedFileTest(){
        testFile.setWritable(false);

        boolean expected = false;
        boolean actual = FileOperations.tryWriteToFile(testFile.getPath(),"");

        testFile.setWritable(true);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void writeTest(){
        boolean expected = true;
        boolean actual = FileOperations.tryWriteToFile(testFile.getPath(),"");

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void readFromNotExistingFileTest(){
        String notExistingPath = "";

        boolean expected = false;
        boolean actual = FileOperations.tryReadFile(notExistingPath).getFirst();

        testFile.setReadable(true);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void readTest(){
        boolean expected = true;
        boolean actual = FileOperations.tryReadFile(testFile.getPath()).getFirst();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void loadPropertyTest(){
        Properties properties = FileOperations.loadProperties(propertiesFile.getPath());

        String actual = properties.getProperty(PROPERTY_NAME);

        Assert.assertEquals(PROPERTY_VALUE,actual);
    }

}