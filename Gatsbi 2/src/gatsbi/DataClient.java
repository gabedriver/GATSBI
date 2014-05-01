/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gatsbi;

import java.io.*;
import java.net.*;

/**
 *
 * @author Tyler
 */
public class DataClient {

    String hostName = "";
    int portNumber = 9090;

    DataClient() {
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
        }
    }

    public boolean saveToServer(Person currentPerson) {
        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);) {

            out.println(currentPerson.getName().toLowerCase());
            if (currentPerson.getName().isEmpty()) {

                out.println("-");
            } else {
                out.println(currentPerson.getName());
            }

            if (currentPerson.getLastName().isEmpty()) {
                out.println("-");
            } else {
                out.println(currentPerson.getLastName());
            }
            if (currentPerson.getGender().isEmpty()) {
                out.println("-");
            } else {
                out.println(currentPerson.getGender());
            }

            if (currentPerson.getOccupation() == GLOBALS.NULL) {
                out.println("-");
            } else {
                out.println("" + currentPerson.getOccupation());
            }
            if (currentPerson.getHometown().isEmpty()) {
                out.println("-");
            } else {
                out.println(currentPerson.getHometown());
            }

            if (currentPerson.getAge() == 0) {
                out.println("-");
            } else {
                out.println("" + currentPerson.getAge());
            }

            if (currentPerson.getLikes().isEmpty()) {

                out.println("-");
            } else {
                out.println(currentPerson.getLikes());
            }


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
        return true;
    }

    public File[] getAllFromServer() {
        File[] f = null;

        return f;
    }

    public File getFromServer(String name) {
        File f = null;

        return f;
    }
}
