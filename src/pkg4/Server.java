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

/**
 * **********************************************************************************************
 * Class: Server
 * Description:	This class includes main function of the program. In addition, it handles
 *              potential error when creating multiple threads(requests) in the program.
 **********************************************************************************************
 */
public class Server {

    /* declare global variables */
    private static ServerSocket socket;
    private static boolean serverIsOn = true;

    /* set the port number */
    private static int PORT = 1000;

    /**
     * **********************************************************************************************
     * Method:	main 
     * Input: arguments in array of string types 
     * Output:	N/A
     * Description: start the program. The main purpose is waiting for client requests
     *              and handles potential errors
     *********************************************************************************************
     */
    public static void main(String[] args) throws IOException {

        socket = new ServerSocket(PORT);

        System.out.println("Server is waiting...");
        System.out.println("*********************************************");

        /* server is waiting for client's request*/
        while (serverIsOn) {
            Socket s = socket.accept();


            /* create multiple requests while handling possible errors*/
            try {

                /* call ClientHandle class*/
                ClientHandle clienthandle = new ClientHandle(s);

                /* creating multiple threads */
                Thread thread = new Thread(clienthandle);
                thread.start();

            } /* error handling */ catch (Exception e) {
                /* display error messages and exit */
                System.out.println("Fail to attempt to connect server\n");
                System.exit(-1);
            }

        }
    }
}
