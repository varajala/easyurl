package downloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * @author varajala
 * @version Mar 3, 2021
 * Class that holds all static methods to execute commands based on given input.
 * Subclass this and override the actual execute-methods for testing.
 */
public class Commands {
    
    protected final String FILE_HANDLE = "--file";
    protected final String DOWNLOAD = "download";
    
    /**
     * @param args -
     */
    public void execute(String[] args) {
        if (args.length == 0) printHelp();
        if (containsFileHandle(args)) {
            executeFromFile(args);
        }
        // TODO handle commands from commandline
    }
    
    /**
     * @param args -
     */
    public void executeFromFile(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR: Execution from a file need exactly two arguments.");
            System.exit(1);
        }
        String filepath = args[0] != FILE_HANDLE ? args[0]: args[1];
        try {
            List<String> data = FileParser.readCommandFile(filepath);
            List<Hashtable<String, String>> commands = FileParser.parseMultiple(data);
            for (Hashtable<String, String> commandArgs : commands) {
                decideAction(commandArgs);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void decideAction(Hashtable<String, String> commandArgs) {
        String command = commandArgs.get("command");
        if (command == null) {
            System.out.println("Invalid command");
            System.exit(1);
        }
        switch (command) {
            case DOWNLOAD:
                executeDownload(commandArgs);
                break;
            default:
                System.out.printf("Unidentified command: %s%n", command);
                break;
        }
    }
    
    /**
     * @param commandArgs -
     *
     */
    public void executeDownload(Hashtable<String, String> commandArgs) {
        String url = commandArgs.get("url");
        String filepath = commandArgs.get("filepath");
        if (url == null || filepath == null) {
            //TODO handle missing arguments
        }
        System.out.printf("Downloading %s ...", url);
        try {
            Downloader.download(url, filepath);
        } catch (IOException e) {
            System.out.print("FAILED%n");
        }
        System.out.print("OK%n");
    }
    
    
    private boolean containsFileHandle(String[] args) {
        for (String arg : args) {
            if (arg.equals(FILE_HANDLE)) return true;
        }
        return false;
    }
    
    
    public void printHelp() {
        // TODO
        System.out.println("No arguments passed...");
    }
    
}
