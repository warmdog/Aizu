import java.io.*;
import java.net.*;

class ExtractTextFromHTMLRExp {
  public static void main(String args[]) throws Exception {

     StringBuilder sb = new StringBuilder();
     BufferedReader br = new BufferedReader(new FileReader("Test.html"));
     String line;
     while ( (line=br.readLine()) != null) {
       sb.append(line);
       // or
       //  sb.append(line).append(System.getProperty("line.separator"));
     }
     String nohtml = sb.toString().replaceAll("\\<.*?>","");
     System.out.println(nohtml);
 } // end of main
}

// http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html

