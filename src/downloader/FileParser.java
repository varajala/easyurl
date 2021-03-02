package downloader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Parse commandline arguments
 */
public class FileParser {
    
    private static String COMMAND_RE = "(?<command>[a-zA-Z_0-9]+)";
    private static String URL_RE = "(?<url>https?://([a-zA-Z0-9]+\\.?/?)+)";
    private static final String DELIMETER = "\\|";
    
    
    private static List<String> readCommandFile(String filepath) throws FileNotFoundException {
        List<String> list = new ArrayList<String>();
        File file = new File(filepath);
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            list.add(s.nextLine());
        }
        return list;
    }
        
    
    /**
     * @param data List of strings as read from the command file
     * @return Arrays of Strings: {command, url, filepath}
     */
    public List<String[]> parseMultiple(List<String> data) {
        List<String[]> parsedCommands = new ArrayList<String[]>();
        for (String line : data) {
            String[] parsedCommand = new String[3];
            String[] splittedLine = line.split(DELIMETER);
            String filepath = null;
            if (splittedLine.length != 2) {
                // TODO errors
            }
            filepath = splittedLine[1].trim();
            Hashtable<String, String> ht = parseCommand(splittedLine[0]);
            parsedCommand[0] = ht.get("command");
            parsedCommand[1] = ht.get("url");
            parsedCommand[2] = filepath;
            parsedCommands.add(parsedCommand);
        }
        return parsedCommands;
    }

    /**
     * @param command Command to be parsed.
     * @return Array of all parts of the command.
     */
    public Hashtable<String, String> parseCommand(String command) {
        Hashtable<String, String> results = new Hashtable<String, String>();
        Pattern pattern = Pattern.compile(createExpression());
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            String cmd = matcher.group("command");
            String url = matcher.group("url");
            if (cmd != null) results.put("command", cmd);
            if (url != null) results.put("url", url);
        }
        return results;
    }
    
    
    private static String createExpression() {
        return String.format("\\s*%s\\s\\s*%s\\s*", COMMAND_RE, URL_RE);
    }
}
