import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the main functions")
public class TestMainFunctions {

    @Test
    @DisplayName("Awesome file existing test")                  //I was tired when I wrote this..

    void fileExists() {
        File mcFileFace = new File("cocktailData.json");
        assertTrue(mcFileFace.exists());
    }

    @Test
    @DisplayName("Awesome file reading test")

    void fileReadable() {
        File mcFileFace = new File("cocktailData.json");
        assertTrue(mcFileFace.canRead());
    }

    @Test
    @DisplayName("Awesome file containing test")

    void fileContains(){
        File mcFileFace = new File("cocktailData.json");
        assertTrue(mcFileFace.length() > 0);
    }

    @Test
    @DisplayName("Awesome searching test")

    void searchWorks(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Main.readFile("cocktailData.json");
        Main.doSearch("lettuce");
        assertTrue(outContent.toString().contains("BLT cocktail"));
    }

}
