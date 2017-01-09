package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;

public class ServeurSSL
{
	public static void main ( String[] args ) throws UnknownHostException, IOException
	{
		System.out.println( "serveur ON" );
		
		// Ouverture du socket, avec le port spécifique
		int port = 8080;
		ServerSocket ServerS = new ServerSocket( port );
		// Accepte les clients
		SSLSocket S = (SSLSocket) ServerS.accept();
		S.startHandshake();
        System.out.println(S.getRemoteSocketAddress());
		
		// Tant que la socket est ouverte
		while ( true )
		{
			
			// Récupère les informations du buffer
			InputStreamReader ISR = new InputStreamReader( S.getInputStream() );
			BufferedReader BR = new BufferedReader( ISR );
			String donnee = BR.readLine();
			System.out.println( donnee );
			
			// Si donnee est null
			if ( donnee == null )
			{
				break;
			}
			
			// Renvoie les données dans le buffer
			PrintWriter PW = new PrintWriter( S.getOutputStream() );
			PW.println( "a reçu les données" );
			PW.flush();
			
		}
		
		System.out.println( "serveur OFF" );
		ServerS.close();
	}
}