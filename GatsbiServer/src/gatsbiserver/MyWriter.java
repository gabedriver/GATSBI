package gatsbiserver;

/**
 * MyWriter.java created by ajansen on Apr 10, 2014 at 2:06:25 PM
 */

import java.io.*;
import java.awt.*;
import java.net.URISyntaxException;
import java.net.URL;

public class MyWriter  {
    protected PrintWriter pw;
    protected String path;

    public String getPath() {return path;}
    
    public MyWriter() {
        openIt(getFileName());
    }
    
    public MyWriter(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("users");
        
        System.out.println(classLoader.toString());
        File file = null;
        String absPath = "";
        try {
            file = new File(url.toURI());
            absPath = file.getAbsolutePath() + "/"+path;
              } catch (URISyntaxException ex) {
            System.out.println("MyWriter Prblem!!");        
        } 
        openIt(absPath);
    }
    

    public MyWriter(String filename, boolean b) {
        openIt(getFileName(filename));
    }

    void openIt (String filename) {
        try {
            path = filename;
            pw = new PrintWriter(new FileWriter(filename));
        } catch (Exception e) {System.out.println("MyWriter -- can't open " + filename + "!" + e);}
    }
    
    
    
    public void print(String s) {
        pw.print(s);
    }
    
    public void println(String s) {
        print(s+"\n");
    }
    
    public void close() {
        pw.close();
    }
    
    
        
     String getFileName() {
        FileDialog fd = new FileDialog(new Frame(), "Select Output File", FileDialog.SAVE);
        fd.setFile("output");
        fd.setVisible(true);
        return fd.getDirectory()+fd.getFile();  // return the complete path
    }
     
     
     String getFileName(String path) {
        FileDialog fd = new FileDialog(new Frame(), "Select Output File", FileDialog.SAVE);
        fd.setDirectory(path);
        fd.setVisible(true);
        System.out.println(fd.getDirectory()+fd.getFile());
        return fd.getDirectory()+fd.getFile();  // return the complete path
         
    }

        
}
