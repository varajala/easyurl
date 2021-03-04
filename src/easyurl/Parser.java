package easyurl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * @author Valtteri Rajalainen
 * @version 0.1a Mar 4, 2021
 * Parse commandline arguments
 */
public class Parser {
    
    private static String SCHEME_EXP = "https?://";
    private static String USERINFO_EXP = "([a-zA-Z0-9_]+(:[a-zA-Z0-9_]+)?@)?";
    private static String HOST_EXP = "[a-zA-Z0-9_\\.-]+(?:\\.[a-zA-Z0-9_\\.-]+)+";
    private static String PORT_EXP = "(:[0-9]{2,5})?";
    private static String RESOURCE_EXP = "(/[a-zA-Z0-9_]*)*([a-zA-Z0-9_\\.-]*)?";
     
    private static final String[] EXPRESSIONS = {
            "(?<command>(?<=(\\A|\\s))[a-zA-Z_0-9]+(?=(\\s|\\Z)))",
            "(?<url>" + SCHEME_EXP + USERINFO_EXP + HOST_EXP + PORT_EXP + RESOURCE_EXP + ")",
            "(?<filepath>(?<=(\\s|\\A))(\\w:)?[\\w-\\./\\\\]+(?=(\\s|\\Z)))"
    };
    
    private static final String[] GROUPS = {"command", "url", "filepath"};
    
    
    /**
     * @param filepath -
     * @return -
     * @throws FileNotFoundException -
     */
    public static List<String> readCommandFile(String filepath) throws FileNotFoundException {
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
    public static List<Hashtable<String, String>> parseMultiple(List<String> data) {
        List<Hashtable<String, String>> parsedCommands = new ArrayList<Hashtable<String, String>>();
        for (String line : data) {
            Hashtable<String, String> parsedCommand = parseCommand(line);
            parsedCommands.add(parsedCommand);
        }
        return parsedCommands;
    }

    /**
     * @param command Command to be parsed.
     * @return Array of all parts of the command.
     */
    public static Hashtable<String, String> parseCommand(String command) {
        Hashtable<String, String> results = new Hashtable<String, String>();
        Pattern pattern = Pattern.compile(createExpression());
        Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            for (String groupName : GROUPS) {
                String match = matcher.group(groupName);
                if (match != null) {
                    results.put(groupName, match);
                }
            }
        }
        return results;
    }
    
    private static String createExpression() {
        return String.join("|", EXPRESSIONS);
    }
}
