package fr.usmb.isc.m2;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class ServeurSSL {
  public static void main(String[] argv) throws Exception {
    int port = 443;
    
    ServerSocketFactory ssocketFactory = SSLServerSocketFactory.getDefault();
    ServerSocket ssocket = ssocketFactory.createServerSocket(port);
    
    while(true){
    	
    	System.out.println("Serveur accepté");
        Socket socket = ssocket.accept();
        
     // Tant que la socket est ouverte
     		while ( true )
     		{
     			
     			// Récupère les informations du buffer
     			InputStreamReader ISR = new InputStreamReader( socket.getInputStream() );
     			BufferedReader BR = new BufferedReader( ISR );
     			String donnee = BR.readLine();
     			System.out.println( donnee );
     			
     			// Si donnee est null
     			if ( donnee == null )
     			{
     				break;
     			}

     			// Renvoie les données dans le buffer
     			PrintWriter PW = new PrintWriter( socket.getOutputStream() );
     			PW.println( "a reçu les données" );
     			PW.flush();
     			
     		}

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // Read from in and write to out...

        in.close();
        out.close();
    }
    
    
  }
}