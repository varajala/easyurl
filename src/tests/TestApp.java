package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


/**
 * @author varajala
 * @version Mar 3, 2021
 */
public class TestApp {
    
    DummyCommands cmd;
    List<String> log = new ArrayList<String>();
    

    @Before
    public void setupTestApplication() {
        this.cmd = new DummyCommands(this.log);
    }
    
    
    @Test
    public void testExecutionFromFile() {
        String[] cmdArgs = {"--file", "script.txt"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[EXECUTING script.txt]");
        clearLog();
        
        cmdArgs = new String[] {"script.txt", "--file"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[EXECUTING script.txt]");
        clearLog();
        
        cmdArgs = new String[] {"script.txt", "--file", "some arg"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[ERROR]");
        clearLog();
        
        cmdArgs = new String[] {"--file"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[ERROR]");
        clearLog();
        
        cmdArgs = new String[] {};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[HELP]");
        clearLog();
    }
    
    
    @Test
    public void testExecutionFromCommandline() {
        String[] cmdArgs = {"download", "https://some.url.com/resource", "file.txt"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[EXECUTING download https://some.url.com/resource file.txt]");
        clearLog();
        
        cmdArgs = new String[] {"get", "https://some.url.com", "file.txt"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[INVALID COMMAND]");
        clearLog();
        
        cmdArgs = new String[] {"script.txt", "argument"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[INVALID COMMAND]");
        clearLog();
        
        cmdArgs = new String[] {"download", "https://some.url.com/resource"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[ERROR]");
        clearLog();
        
        cmdArgs = new String[] {":download:", "https://some.url.com/resource"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[NO COMMAND]");
        clearLog();
        
        cmdArgs = new String[] {"download", "https:/some.url.com/resource", "file.txt"};
        cmd.execute(cmdArgs);
        assertEquals(Arrays.toString(log.toArray()), "[ERROR]");
        clearLog();
    }
    
    
    private void clearLog() {
        this.log.clear();
    }
}
