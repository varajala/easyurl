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
    
    
    @Test
    public void testURLParsing() {
        String command = "https://127.0.0.1:5000/";
        Hashtable<String, String> ht = FileParser.parseCommand(command);
        assertEquals(ht.get("url"), "https://127.0.0.1:5000/");
        
        command = "https://127:0.0.1:5000/";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("url"), null);
        
        command = "ftp://some.url/resource";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("url"), null);
        
        command = "https://user@host.domain:8000/resource";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("url"), "https://user@host.domain:8000/resource");
        
        command = "https://user:password@host.domain:8000/resource";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("url"), "https://user:password@host.domain:8000/resource");
    }
    
    
    @Test
    public void testFilepathParsing() {
        String command = "/home/usr/file.dat";
        Hashtable<String, String> ht = FileParser.parseCommand(command);
        assertEquals(ht.get("filepath"), "/home/usr/file.dat");
        
        command = "C:Users\\\\username\\\\Documents\\\\file.txt";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("filepath"), "C:Users\\\\username\\\\Documents\\\\file.txt");
        
        command = "../../some-file";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("filepath"), "../../some-file");
        
        command = "../../some-file?";
        ht = FileParser.parseCommand(command);
        assertEquals(ht.get("filepath"), null);
    }
}
