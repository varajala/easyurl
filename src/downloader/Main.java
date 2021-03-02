package downloader;

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
        Downloader.download(url, filepath);
        System.out.println("Finished");
    }
    
}
