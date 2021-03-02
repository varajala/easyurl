package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test; 
import java.util.Hashtable;


import downloader.CommandParser;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Unit tests for CommandParser.
 */
public class CommandParserTests {
    
    CommandParser parser = new CommandParser();
    
    /**
     * Test commandline argumetn parsing.
     */
    @Test
    public void testCommadParsing() {
        String command = "get https://www.google.com";
        Hashtable<String, String> ht = parser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://www.google.com");
        
        command = "get    https://www.google.com";
        ht = parser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://www.google.com");
        
        command = "    get https://www.google.com  ";
        ht = parser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://www.google.com");
        
        command = "get";
        ht = parser.parseCommand(command);
        assertEquals(ht.get("command"), null);
        assertEquals(ht.get("url"), null);
    }
}
