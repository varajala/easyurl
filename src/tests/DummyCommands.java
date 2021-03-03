package tests;

import java.util.Hashtable;
import java.util.List;

import downloader.Commands;



/**
 * @author varajala
 * @version Mar 3, 2021
 *
 */
public class DummyCommands extends Commands{
    
    List<String> log;
    
    /**
     * @param list for output logging.
     */
    public DummyCommands(List<String> list) {
        this.log = list;
    }
    
    @Override
    public void executeDownload(Hashtable<String, String> commandArgs) {
        String url = commandArgs.get("url");
        String filepath = commandArgs.get("filepath");
        if (url == null || filepath == null) {
            log("ERROR");
        }
        log(String.format("EXECUTING download %s %s", url, filepath));
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
    public void printHelp() {
        log("HELP");
    }
    
    
    private void log(String message) {
        this.log.add(message);
    }
}
