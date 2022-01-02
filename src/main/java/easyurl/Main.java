package easyurl;

/**
 * @author Valtteri Rajalainen
 * @version 1.0.0 Jan 2, 2022
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
