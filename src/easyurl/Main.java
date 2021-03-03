package easyurl;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Main entrypoint to the program.
 */
public class Main {
    
    /**
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        Commands cmd = new Commands();
        cmd.execute(args);
    }
}
