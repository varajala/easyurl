package downloader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Main entrypoint to the downloader.
 */
public class Main {
    
    private static final String FILE_HANDLE = "--file";
    private static final String DOWNLOAD = "download";
    
    
    /**
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        execute(args);
    }
    
    
    private static void execute(String[] args) {
        if (args.length == 0) printHelp();
        if (containsFileHandle(args)) {
            executeFromFile(args);
        }
        // TODO handle commands from commandline
    }


    private static void executeFromFile(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR: Execution from a file need exactly two arguments.");
            System.exit(1);
        }
        String filepath = args[0] != FILE_HANDLE ? args[1]: args[0];
        try {
            List<String> data = FileParser.readCommandFile(filepath);
            List<String[]> commands = FileParser.parseMultiple(data);
            for (String[] command : commands) {
                executeCommand(command);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void executeCommand(String[] commandData) {
        String command = commandData[0];
        String url = commandData[1];
        //String filepath = commandData[2];
        switch (command) {
            case DOWNLOAD:
                System.out.printf("Downloading... %s%n", url);
               /* try {
                    //Downloader.download(url, filepath);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                System.out.println("Finished");
            break;
        default:
            System.out.printf("Unidentified command: %s%n", command);
            break;
        }
    }


    private static boolean containsFileHandle(String[] args) {
        for (String arg : args) {
            if (arg.equals(FILE_HANDLE)) return true;
        }
        return false;
    }
    
    
    private static void printHelp() {
        // TODO
        System.out.println("No arguments passed...");
    }
    
}
