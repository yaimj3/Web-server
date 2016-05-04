/**
 * **********************************************************************************************
 *  Author:         Minjung Yoo (1001013459)
 *  Course:         CSE 4344-001
 *  Assignment:     Lab #1 Web Server Programming
 *  Description:    This program develops a multithread web server which is able
 *                  to serve multiple requests in parallel.
 **********************************************************************************************
 */
package pkg4;

import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * **********************************************************************************************
 * Class: ClientHandle
 * Description:	This class is created by Server class. it handles client's requests
 *              and sending responses: 200, 301, and 400.
 **********************************************************************************************
 */
public class ClientHandle implements Runnable {

    /* declare global variables */
    private Socket socket;
    private String filename;
    private String filepath;

    /**
     * **********************************************************************************************
     * Method:	ClientHandle Constructor
     * Input:	socket s received from Server class
     * Output:	N/A
     * Description: set the input socket as the private variable socket in ClientHandle
    *********************************************************************************************
     */
    public ClientHandle(Socket s) {
        this.socket = s;
    }

    /**
     * **********************************************************************************************
     * Method:	run
     * Input:	N/A
     * Output	N/A
     * Description: This method handles errors while calling clientRequestProcess method
    **********************************************************************************************
     */
    public void run() {
        try {
            clientRequestProcess();
        } catch (Exception exception) {
            System.out.println(exception);

        }

    }

    /**
     * **********************************************************************************************
     * Method:	clientRequestProcess
     * Input:	N/A
     * Output:	N/A
     * Description: This method gets clientHHTTP requests and sends responses by conditions. only
     *              three HTTP responses are available in the program: 200, 301, and 404.
    **********************************************************************************************
     */
    public void clientRequestProcess() throws IOException {
        /* declare local variables*/
        String templine = new String();
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        /* display messages for server side*/
        System.out.println("Get user's request");

        /* read client requests and parse the line*/
        if ((templine = inputStream.readLine()) != null) {
            if (templine.startsWith("GET")) {
                /* call getFileName method */
                getFilename(templine);
            }
        }

        /* get current path of users*/
        Path path = Paths.get(filepath).toAbsolutePath();
        /* get file using path*/
        File file = path.toFile();

        /*get html file for 404 error message */
        String notFoundFilepath = System.getProperty("user.dir") + "/error.html";
        File notFoundFile = new File(notFoundFilepath);
        /*get html file for 301 error message */
        String pathFor301 = System.getProperty("user.dir") + "/301.html";
        File file301 = new File(pathFor301);

        System.out.println(filepath);

        /* check condition for 301 errors*/
        if (filename.endsWith("a.html")) {

            /* send html responses*/
            outputStream.write("HTTP/1.1 301 Moved Permanently\r\n");
            outputStream.write("Content-Type: " + setFileType("a.html") + "\r\n");
            outputStream.write("Content-length: " + file301.length() + "\r\n");
            outputStream.write("Connection: Closed\r\n\r\n");
            outputStream.flush();

            /* copy file to socket; web browser will direct to the link*/
            Files.copy(file301.toPath(), socket.getOutputStream());

            /* close socket, inputstream and outputstream */
            inputStream.close();
            outputStream.close();
            socket.close();

            System.out.println("Client finished");
            System.out.println("*********************************************");
            return;

        }


        /* check condition for 200 - success to find the file using path*/
        if (file.exists()) {
            /*send html response*/
            outputStream.write("HTTP/1.1 200 OK\r\n");
            outputStream.write("Content-Type: " + setFileType(filename) + "\r\n");
            outputStream.write("Content-length: " + file.length() + "\r\n");
            outputStream.write("Connection: Closed\r\n\r\n");
            outputStream.flush();

            /* copy file to socket; web brower will direct to the link*/
            Files.copy(file.toPath(), socket.getOutputStream());
        } /* otherwise, file is not found. */ else {
            /* send html response */
            outputStream.write("HTTP/1.1 404 Not Found\r\n");
            outputStream.write("Content-Type: " + setFileType("error.html") + "\r\n");
            outputStream.write("Content-length: " + notFoundFile.length() + "\r\n");
            outputStream.write("Connection: Closed\r\n\r\n");
            outputStream.flush();
            /* copy file to socket; web brower will direct to the link*/
            Files.copy(notFoundFile.toPath(), socket.getOutputStream());

        }


        /* close socket, inputstream, and outputstream*/
        socket.close();
        inputStream.close();
        outputStream.close();

        System.out.println("Client finished");
        System.out.println("*********************************************");

    }

    /**
     * **********************************************************************************************
     * Method:	getFilename
     * Input:	String type variable
     * Output:	N/A
     * Description: This method gets filename and filepath from input string. If filename
     *              does not exists, the filename will be "index.html"
	 **********************************************************************************************
     */
    public void getFilename(String myString) {
        /* get user's current directory */
        String currentDir = System.getProperty("user.dir");
        String tempString = myString.split(" ")[1];

        /* check the filename. If filename is empty*/
        if (tempString.equals("/")) {
            /* default name = index.html*/
            this.filepath = currentDir + "/index.html";
            this.filename = "/index.html";
        } /* otherwise */ else {
            /* store private variable filepath and filename*/
            this.filepath = currentDir + tempString;
            this.filename = tempString;
        }
    }

    /**
     * **********************************************************************************************
     * Method:  setFileType
     * Input:   filename
     * Output:	String type of file type for http response format
     * Description: this method returns valid command for file type.
    ***********************************************************************************************
     */
    public String setFileType(String filename) {
        String checkStr = filename.toLowerCase();

        /* if filename's extension is jpg or jpeg, return "image/jpeg" */
        if ((checkStr.endsWith(".jpeg")) || (checkStr.endsWith(".jpg"))) {
            return "image/jpeg";
        } /* if filename's extension is gif, return "image/gif "*/ else if ((checkStr.endsWith(".gif"))) {
            return "image/gif";
        } /* if filename's extension is html or htm, return "text/html" */ else if ((checkStr.endsWith(".html")) || (checkStr.endsWith(".htm"))) {
            return "text/html";
        }
        /* otherwise, "application/octect-strea"*/
        return "application/octect-strea";

    }
}
