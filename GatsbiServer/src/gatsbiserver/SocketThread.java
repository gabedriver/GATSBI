/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gatsbiserver;

import java.io.*;
import java.net.*;

class SocketThread extends Thread {

    private Socket s = null;

    public SocketThread(Socket s) {
        this.s = s;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                new InputStreamReader(
                s.getInputStream()));) {
            String inputLine;
            String firstInput = in.readLine();

            if (firstInput.charAt(0) == '-') {
                MyReader mr = new MyReader();
                if (firstInput.charAt(1) == '*') {
                    //load all
                    for (File next : mr.files) {
                        if (next != null) {
                            if (!next.isHidden()) {
                                MyReader person = new MyReader(next);
                                out.println(next.getName());
                                while(person.hasMoreData()){
                                    out.println(person.giveMeTheNextLine());
                                }
                                person.close();
                                out.println("*");
                            }
                        }

                    }
                } else {
                    //load one
                    String name = firstInput.substring(1);
                    for (File next : mr.files) {
                        if (next != null) {
                            if (!next.isHidden() && next.getName().equals(name)) {
                                MyReader person = new MyReader(next);
                                while(person.hasMoreData()){
                                    out.println(person.giveMeTheNextLine());
                                }
                                person.close();
                                break;
                            }
                        }

                    }

                }
            } else {
                //revieve
                MyWriter mw = new MyWriter(firstInput);
                while ((inputLine = in.readLine()) != null) {
                    mw.println(inputLine);
                }
                mw.close();
                s.close();
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
