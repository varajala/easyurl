package downloader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Parse commandline arguments
 */
public class CommandParser {
    
    private static String COMMAND_RE = "(?<command>[a-zA-Z_0-9]+)";
    //private static String FLAG_RE = "(?<flag>(-[a-zA-Z])|(--[a-zA-Z][a-zA-Z]+))";
    private static String URL_RE = "(?<url>https?://([a-zA-Z0-9]+\\.?)+)";

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
    
    
    @SuppressWarnings("unused")
    private static String createExpression() {
        return String.format("\\s*%s\\s\\s*%s\\s*", COMMAND_RE, URL_RE);
    }
}
