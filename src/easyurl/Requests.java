package easyurl;

import java.net.*;
import java.io.*;

/**
 * @author Valtteri Rajalainen
 * @version 0.1a Mar 4, 2021
 * Download data from and URL.
 */
public class Requests {
    
    /**
     * Send a GET request and write the resulting data to a given file.
     * @param url URL to the resource
     * @param filepath Absolute filepath where the data is written.
     * @throws RequestFailedException -
     */
    public static void get(String url, String filepath) throws RequestFailedException {
        try (InputStream data = connectAndGetInputStream(makeURL(url));
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
    
    /**
     * @param url URL as a String.
     * @param JSONString Key and value pairs representing the data to be sent.
     * @throws RequestFailedException -
     */
    public static void post(String url, String JSONString) throws RequestFailedException {
        try {
            byte[] requestJSON = JSONString.getBytes();
            int lengthOfData = requestJSON.length;
            HttpURLConnection connection = makeHTTPConnection(makeURL(url));
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", Integer.toString(lengthOfData));
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.connect();
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestJSON);
            }
        } catch (ProtocolException e) {
            String info = "POST is not allowed";
            throw new RequestFailedException(info);
        } catch (IOException e) {
            String info = "Connection failed";
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
    
    
    private static InputStream connectAndGetInputStream(URL url) {
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            String info = "Connection failed";
            throw new RequestFailedException(info);
        }
        return stream;
    }
    
    
    private static HttpURLConnection makeHTTPConnection(URL url) {
        HttpURLConnection httpConnection = null;
        try {
           httpConnection =  (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            String info = "Connection failed";
            throw new RequestFailedException(info);
        }
        return httpConnection;
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
