/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gatsbiserver;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GatsbiServer {

    /**
     * @param args the command line arguments
     */
    static int portNumber = 9090;

    public static void main(String[] args) {
        try {
            System.out.println(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ex) {}
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                new SocketThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
