package easyurl;

import java.util.Hashtable;
import java.util.List;


/**
 * @author Valtteri Rajalainen
 * A dummy class for testing the command parsing logic.
 */
public class DummyCommands extends Commands {
    
    List<String> log;
    
    /**
     * @param list for output logging.
     */
    public DummyCommands(List<String> list) {
        this.log = list;
    }
    
    @Override
    public void executeGet(Hashtable<String, String> commandArgs) {
        String url = commandArgs.get("url");
        String filepath = commandArgs.get("filepath");
        if (url == null || filepath == null) {
            log("ERROR");
            return;
        }
        log(String.format("EXECUTING get %s %s", url, filepath));
    }
    
    
    @Override
    public void executeFromFile(String[] args) {
        if (args.length != 2) {
            log("ERROR");
            return;
        }
        String filepath = args[0] != FILE_HANDLE ? args[0]: args[1];
        log(String.format("EXECUTING %s", filepath));
    }
    
    
    @Override
    public void executeFromCommandline(String[] args) {
        Hashtable<String, String> commandArgs = Parser.parseCommand(String.join(" ", args));
        String command = commandArgs.get("command");
        if (command == null) {
            log("NO COMMAND");
            return;
        }
        switch (command) {
            case GET:
                executeGet(commandArgs);
                break;
            default:
                log("INVALID COMMAND");
                break;
        }
    }
    
    
    @Override
    public void printHelp() {
        log("HELP");
    }
    
    
    private void log(String message) {
        this.log.add(message);
    }
}
