package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class ServeurSSL
{
	public static void main ( String args[] ) throws IOException
	{
		System.setProperty( "javax.net.ssl.keyStore", System.getProperty( "user.dir" ) + "/resources/security/clientkeystore" );
		System.setProperty( "javax.net.ssl.keyStorePassword", "azerty" );
		
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		
		// Ouverture du socket, avec le port sp�cifique
		int port = 8080;
		ServerSocket ss = ssf.createServerSocket( port );
		System.out.println( "serveur ON" );
		
		// Tant que la socket est ouverte
		while ( true )
		{
			// Accepte les clients
			SSLSocket s = (SSLSocket) ss.accept();
			
			// R�cup�re les informations du buffer
			InputStreamReader ISR = new InputStreamReader( s.getInputStream() );
			BufferedReader BR = new BufferedReader( ISR );
			String donnee = BR.readLine();
			System.out.println( donnee );
			
			// Si donnee est null
			if ( donnee == null )
			{
				break;
			}
			
			// Renvoie les donn�es dans le buffer
			PrintWriter PW = new PrintWriter( s.getOutputStream() );
			PW.println( "a re�u les donn�es" );
			PW.flush();
		}
		System.out.println( "serveur OFF" );
		ss.close();
		//fin
	}
}