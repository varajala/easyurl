package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Arrays;

import downloader.FileParser;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Unit tests for CommandParser.
 */
public class FileParserTests {
    
    /**
     * Test commandline argumetn parsing.
     */
    @Test
    public void testCommandParsing() {
        String command = "get https://www.google.com";
        Hashtable<String, String> ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://www.google.com");
        
        command = "get    https://www.google.com";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://www.google.com");
        
        command = "    get https://www.google.com  ";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), "get");
        assertEquals(ht.get("url"), "https://www.google.com");
        
        command = "get";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("command"), null);
        assertEquals(ht.get("url"), null);
    }
    
    
    /**
     * Test parsing commands as given from a file.
     */
    @Test
    public void testFileDataParsing() {
        List<String> commands = new ArrayList<String>();
        commands.add("download https://some.url.com/resource|somefile.txt");
        commands.add("download http://some.url.com |  somefile.txt");
        commands.add("download http://some.url.com | /home/user/somefile.txt");
        
        List<String[]> expected = new ArrayList<String[]>();
        expected.add(new String[] {"download", "https://some.url.com/resource", "somefile.txt"});
        expected.add(new String[] {"download", "http://some.url.com", "somefile.txt"});
        expected.add(new String[] {"download", "http://some.url.com", "/home/user/somefile.txt"});
        
        List<String[]> results = FileParser.parseMultiple(commands);
        for (int i = 0; i < commands.size(); i++) {
            String[] result = results.get(i);
            String[] expectedResult = expected.get(i);
            assertEquals(Arrays.toString(result), Arrays.toString(expectedResult));
        }
    }
}
