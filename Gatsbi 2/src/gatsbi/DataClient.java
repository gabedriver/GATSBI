/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gatsbi;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Tyler
 */
public class DataClient {

    String hostName = "wu-1041.willamette.edu";
    int portNumber = 9090;

    DataClient() {
//        try {
//            hostName = InetAddress.getLocalHost().getHostName();
//        } catch (UnknownHostException ex) {
//        }
        
        
    }

    public boolean saveToServer(Person currentPerson) {
        try (
                Socket s = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);) {

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

    public Person[] getAllFromServer() {
        ArrayList<Person> f = new ArrayList<>();
        try (
                Socket s = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));) {
            out.println("-*");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //Load all!
                //save them
                //name then each line
                //"*" comes after each complete person
                Person next = new Person();
                next.setName(in.readLine());
                next.setLastName(in.readLine());
                next.setGender(in.readLine());

                String oc = in.readLine();
                if (oc.equals("-")||oc.equals("occupation(0-8)")) {
                    next.setOccupation(GLOBALS.NULL);
                } else {
                    next.setOccupation(Short.parseShort(oc));
                }

                next.setHometown(in.readLine());

                String age = in.readLine();
                if (age.equals("-")||age.equals("age(int)")) {
                    next.setAge((short) 0);
                } else {
                    next.setAge(Short.parseShort(age));
                }

                next.setLikes(in.readLine());
                in.readLine();
                f.add(next);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
        Person[] returnMe = new Person[f.size()]; 
        f.toArray(returnMe);
        return  returnMe;
    }

    public Person getFromServer(String name) {
        Person f = new Person();
        try (
                Socket s = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));) {
            out.println("-" + name);
            f.setName(in.readLine());
            f.setLastName(in.readLine());
            f.setGender(in.readLine());

            String oc = in.readLine();
            if (oc.equals("-")) {
                f.setOccupation(GLOBALS.NULL);
            } else {
                f.setOccupation(Short.parseShort(in.readLine()));
            }

            f.setHometown(in.readLine());

            String age = in.readLine();
            if (oc.equals("-")) {
                f.setAge((short) 0);
            } else {
                f.setAge(Short.parseShort(age));
            }

            f.setLikes(in.readLine());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
        return f;
    }
}
