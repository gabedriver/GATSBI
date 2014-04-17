package gatsbi;

/**
 * MyReader.java created by ajansen on Apr 10, 2014 at 2:00:16 PM
 */
import java.io.*;
import java.awt.*;
import java.net.*;
import java.applet.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyReader {

    private BufferedReader br;
    String path;
    boolean busted = false;
    File[] files;

    public boolean getBusted() {
        return busted;
    }

    public String getPath() {
        return path;
    }

    public MyReader() {
        files = getFilesIn("users");

        
    }
    
    public MyReader(File file){
        openIt(file.getAbsolutePath());
    }

    public MyReader(String filename) {
        openIt(filename);
    }

    public MyReader(String filename, Applet theApplet) {
        try {
            URL theURL = new URL(theApplet.getDocumentBase(), filename);
            InputStreamReader isr = new InputStreamReader(theURL.openStream());
            br = new BufferedReader(isr);
        } catch (Exception e) {
            System.out.println("MyReader -- bad file from net" + e);
        }
    }

    public String giveMeTheNextLine() {
        try {
            return br.readLine();
        } catch (Exception e) {
            System.out.println("MyReader -- eof?!" + e);
        }
        return "";
    }

    public boolean hasMoreData() {
        try {
            return br.ready();
        } catch (Exception e) {
            System.out.println("MyReader -- disaster!" + e);
        }
        return false;
    }

    public void close() {
        try {
            if (br != null)
                br.close();
        } catch (Exception e) {
            System.out.println("MyReader:can't close!" + e);
        }
    }

    private void openIt(String filename) {
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            System.out.println("MyReader -- can't open " + filename + "!" + e);
            busted = true;
        }
    }

    private String getFileName() {
        FileDialog fd = new FileDialog(new Frame(), "Select Input File");
        fd.setFile("input");
        fd.setVisible(true);
        path = fd.getDirectory() + fd.getFile();
        return path;  // return the complete path

    }
    
     File[] getFilesIn(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(path);
        File folder = null;
        try {
            folder = new File(url.toURI());
        } catch (URISyntaxException ex) {
            System.out.println("getFilesIn Problem!!");
        }
            File[] returnMe = new File[folder.listFiles().length]; 
            int i = 0;
             for (File file : folder.listFiles()) {
             if (!file.isHidden()){
//                 System.out.println(file.getName());
             returnMe[i] = file;
             i++;
             }
             }
             return returnMe;

}

 String filesToString(){
     String returnMe ="";
     for(File next: files){
         if(next != null){
         returnMe += "\n" + next.getName();
     }}

     return returnMe;
 }
     
     

}