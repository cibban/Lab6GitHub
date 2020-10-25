import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing the main functions")
public class TestMainFunctions {

    @Test
    @DisplayName("Awesome file existing test")                  //I was tired when I wrote this..

    void fileExists() {
        File mcFileFace = new File("query.json");
        assertTrue(mcFileFace.exists());
    }

    @Test
    @DisplayName("Awesome file reading test")

    void fileReadable() {
        File mcFileFace = new File("query.json");
        assertTrue(mcFileFace.canRead());
    }

    @Test
    @DisplayName("Awesome file containing test")

    void fileContains(){
        File mcFileFace = new File("query.json");
        assertTrue(mcFileFace.length() > 0);
    }
}
