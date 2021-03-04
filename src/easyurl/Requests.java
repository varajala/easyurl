package easyurl;

import java.net.*;
import java.io.*;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Download data from and URL.
 */
public class Requests {
    
    /**
     * Download resources from the internet.
     * @param url URL to the resource
     * @param filepath Absolute filepath where the data is written.
     * @throws RequestFailedException -
     */
    public static void get(String url, String filepath) throws RequestFailedException {
        try (InputStream data = connect(makeURL(url));
             FileOutputStream file = openFileStream(filepath))
        {
            writeDataToFile(data, file);
            data.close();
            file.close();
        } catch (IOException e) {
            String info = "Unknown error";
            throw new RequestFailedException(info);
        }
    }
    
    private static URL makeURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            String info = "Invalid URL";
            throw new RequestFailedException(info);
        }
        return url;
    }    
    
    
    private static InputStream connect(URL url) {
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            String info = "Connection failed";
            throw new RequestFailedException(info);
        }
        return stream;
    }
    
    
    private static FileOutputStream openFileStream(String filepath) {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filepath);
        } catch (FileNotFoundException e) {
            String info = "Invalid filepath. Some directories may be nonexistent.";
            throw new RequestFailedException(info);
        }
        return file;
    }
    
    
    private static void writeDataToFile(InputStream stream, FileOutputStream file) {
        try {
            int data;
            while((data = stream.read()) != -1) {
                file.write(data);
            }
        } catch (IOException e) {
            String info = "Error while writing data to the file";
            throw new RequestFailedException(info);
        }
    }
}
