package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import easyurl.FileParser;

import java.util.Hashtable;

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
        
        
        command = "get https://127.0.0.1:5000/ /home/user/somefile.txt";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://127.0.0.1:5000/");
        assertEquals(ht.get("filepath"), "/home/user/somefile.txt");
        
        command = "get";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), null);
        assertEquals(ht.get("filepath"), null);
        
        command = "  post   http://some.url/resource";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "post");
        assertEquals(ht.get("url"), "http://some.url/resource");
        assertEquals(ht.get("filepath"), null);
        
        command = "http:/some.url/resource ";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), null);
        assertEquals(ht.get("url"), null);
        assertEquals(ht.get("filepath"), null);
    }
}
