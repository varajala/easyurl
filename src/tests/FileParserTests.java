package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

import downloader.FileParser;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Unit tests for CommandParser.
 */
public class FileParserTests {
    
    /**
     * Test commandline argument parsing.
     */
    @Test
    public void testCommandParsing() {
        String command = "get https://www.google.com /home/user/somefile.txt";
        Hashtable<String, String> ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://www.google.com");
        assertEquals(ht.get("filepath"), "/home/user/somefile.txt");
        
        command = "get";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), null);
        assertEquals(ht.get("filepath"), null);
        
        command = "  download   http://some.url/resource";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "download");
        assertEquals(ht.get("url"), "http://some.url/resource");
        assertEquals(ht.get("filepath"), null);
        
        command = "http:/some.url/resource ";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), null);
        assertEquals(ht.get("url"), null);
        assertEquals(ht.get("filepath"), null);
    }
}
