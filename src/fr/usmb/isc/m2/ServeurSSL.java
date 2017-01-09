package fr.usmb.isc.m2;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServeurSSL {
  public static void main(String[] argv) throws Exception {
    int port = 8080;
    
    SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
    SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(port);
    
    while(true){
    	
    	System.out.println("Serveur accept�");
    	SSLSocket socket = (SSLSocket) serverSocket.accept();
        socket.startHandshake();
        System.out.println(socket.getRemoteSocketAddress());
        
     // Tant que la socket est ouverte
     		while ( true )
     		{
     			
     			// R�cup�re les informations du buffer
     			InputStreamReader ISR = new InputStreamReader( socket.getInputStream() );
     			BufferedReader BR = new BufferedReader( ISR );
     			String donnee = BR.readLine();
     			System.out.println( donnee );
     			
     			// Si donnee est null
     			if ( donnee == null )
     			{
     				break;
     			}

     			// Renvoie les donn�es dans le buffer
     			PrintWriter PW = new PrintWriter( socket.getOutputStream() );
     			PW.println( "a re�u les donn�es" );
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