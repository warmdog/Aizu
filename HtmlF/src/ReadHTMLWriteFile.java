import java.io.*;
import java.net.*;

class ReadHTMLWriteFile {
  public static void main(String args[]) {
    String aHTML = new String();

    if (args.length != 2) {
      System.out.println("Usage: java ReadHTMLWriteFile URL OutFileName");
      System.exit(0);
    }
    try {

      // Obtain URL
      URL url = new URL(args[0]);

      // Obtain input stream
      InputStream is = url.openStream(); 

      // Read and display data from URL
      byte buffer[] = new byte[1024];
      int i;
      while((i = is.read(buffer)) != -1) {
	aHTML = aHTML + new String(buffer);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

// End of Read from network, and now write to a file.
   BufferedWriter writer = null;
   try
   {
       writer = new BufferedWriter( new FileWriter( args[1]));
       writer.write( aHTML);
   } catch ( IOException e) {
     }

   finally {
       try  {
           if ( writer != null)
           writer.close( );
       } catch ( IOException e) {
         }
   } // finally close

  } // end of main method
}


// Refer to the Pattern class
// http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html

