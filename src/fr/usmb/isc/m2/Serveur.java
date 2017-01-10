package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Serveur {
  public static void main(String args[]) throws Exception {
    System.setProperty("javax.net.ssl.keyStore", System.getProperty( "user.dir" ) + "/resources/security/clientkeystore");
    System.setProperty("javax.net.ssl.keyStorePassword", "azerty");
    
    
    //SSLContext ctx = SSLContext.getInstance("TLS");
	//SSLSocketFactory ssf = ctx.getSocketFactory();
	//SSLSocket ss = (SSLSocket) ssf.createSocket( "127.0.0.1", 8080 );
    
   SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
   ServerSocket ss = ssf.createServerSocket(8080);
    
    // debut
    while (true) {
      Socket s = ss.accept();
      
   // Récupère les informations du buffer
   			InputStreamReader ISR = new InputStreamReader( s.getInputStream() );
   			BufferedReader BR = new BufferedReader( ISR );
//   			String donnee = BR.readLine();
//   			System.out.println( donnee );
//   			
//   			// Si donnee est null
//   			if ( donnee == null )
//   			{
//   				break;
//   			}
   			
      PrintStream out = new PrintStream(s.getOutputStream());
      //out.println("a recu - "+ donnee);
      out.close();
      s.close();
    }
//fin
  }
}