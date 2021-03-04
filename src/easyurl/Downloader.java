package easyurl;

import java.net.*;
import java.io.*;

/**
 * @author varajala
 * @version Mar 2, 2021
 * Download data from and URL.
 */
public class Downloader {
    
    /**
     * Download resources from the internet.
     * @param url URL to the resource
     * @param filepath Absolute filepath where the data is written.
     * @throws IOException -
     */
    public static void get(String url, String filepath) throws IOException {
        try (InputStream data = connect(makeURL(url));
             FileOutputStream file = openFileStream(filepath))
        {
            writeDataToFile(data, file);
            data.close();
            file.close();
        }
    }
    
    private static URL makeURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    
    
    private static InputStream connect(URL url) {
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
    
    
    private static FileOutputStream openFileStream(String filepath) {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
