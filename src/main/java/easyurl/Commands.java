package easyurl;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Valtteri Rajalainen
 * @version 0.1a Mar 4, 2021
 * Class that holds all static methods to execute commands based on given input.
 * Subclass this and override the actual execute-methods for testing.
 */
public class Commands {
    
    private static final String VERSION = "0.1a";
    protected static final String FILE_HANDLE = "--file";
    protected static final String GET = "get";
    
    private final String[] HELP = {
            "=== Welcome to easyurl version: " + VERSION + " ===\n",
            "Use --file [filepath] to execute all commands in a specified file.\n",
            "Supported commands:",
            "- - - - - - - - - - - -\n\n",
            "/> get [url] [filepath]\n",
            
            "Send a GET request to the specified URL and write the recieved data into the provided filepath.",
            "The URL needs to include the scheme used.",
            "Currently only http and https are supported.",
            "The filepath can be relative or absolute and a file is created if it doesn't exist.",
            "Fails if any directories in the path are nonexistent.",
            "\nEXAMPLE: get https://www.someurl.com index.html\n\n"
    };
    
    /**
     * @param args Command line arguments as passed to the main(String[] args).
     */
    public void execute(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }
        if (containsFileHandle(args)) {
            executeFromFile(args);
            return;
        }
        executeFromCommandline(args);
    }
    
    /**
     * @param args Command line arguments as passed to the main(String[] args).
     */
    public void executeFromFile(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR: Execution from a file needs exactly two arguments.");
            System.exit(1);
        }
        String filepath = args[0] == FILE_HANDLE ? args[0]: args[1];
        try {
            List<String> data = Parser.readCommandFile(filepath);
            List<Hashtable<String, String>> commands = Parser.parseMultiple(data);
            for (Hashtable<String, String> commandArgs : commands) {
                decideAction(commandArgs);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * @param args Command line arguments as passed to the main(String[] args).
     */
    public void executeFromCommandline(String[] args) {
        Hashtable<String, String> parsedCommand = Parser.parseCommand(String.join(" ", args));
        decideAction(parsedCommand);
    }
    
    
    private void decideAction(Hashtable<String, String> commandArgs) {
        String command = commandArgs.get("command");
        if (command == null) {
            System.out.println("Invalid command");
            System.exit(1);
        }
        switch (command) {
            case GET:
                executeGet(commandArgs);
                break;
            default:
                System.out.printf("Unidentified command: %s%n", command);
                break;
        }
    }
    
    /**
     * Execute the get-method from the Requests-class.
     * @param commandArgs Hastable as produced from Parser.parseCommand.
     * The arguments of a given command can be retrieved from this hashtable
     * by the re group as a key.
     */
    public void executeGet(Hashtable<String, String> commandArgs) {
        String url = commandArgs.get("url");
        String filepath = commandArgs.get("filepath");
        if (url == null || filepath == null) {
            String missingArgument = url == null ? url : filepath;
            String errorMessage = String.format(
                    "ERROR: missing or invalid argument %s",
                    missingArgument);
            System.out.println(errorMessage);
            System.exit(1);
        }
        System.out.printf("Downloading %s ...", url);
        try {
            Requests.get(url, filepath);
            System.out.printf(" OK%n");
        } catch (RequestFailedException e) {
            System.out.printf(" FAILED - %s%n", e.getInfo());
        }
    }
    
    
    private boolean containsFileHandle(String[] args) {
        for (String arg : args) {
            if (arg.equals(FILE_HANDLE)) return true;
        }
        return false;
    }
    
    
    
    @SuppressWarnings("javadoc")
    public void printHelp() {
        for (String help : HELP) {
            System.out.println(help);
        }
    }
    
}
