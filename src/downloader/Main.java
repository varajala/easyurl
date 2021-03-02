package downloader;

import java.io.IOException;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Main entrypoint to the downloader.
 */
public class Main {
    
    private static final String url = "";
    private static final String filepath = "";
    
    /**
     * @param args Commandline arguments
     */
    public static void main(String[] args) {
        System.out.println("Downloading...");
        try {
            Downloader.download(url, filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished");
    }
    
}
