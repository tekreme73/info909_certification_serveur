package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serveur {

	public static void main(String[] args) throws UnknownHostException, IOException, ExpressionInvalide{

		
		System.out.println("serveur ON");
		
		// Ouverture du socket, avec le port 2011
		ServerSocket ServerS = new ServerSocket(2011);
		// Accepte les clients
		Socket S = ServerS.accept();

		// Tant que la socket est ouverte
		while(true){
			
			// R�cup�re les informations du buffer
			InputStreamReader ISR = new InputStreamReader(S.getInputStream());
			BufferedReader BR = new BufferedReader(ISR);
			String donnee = BR.readLine();
			System.out.println(donnee);
			
			// Si donnee est null
			if (donnee == null) { break; }

			// Renvoie les donn�es dans le buffer
			PrintWriter PW = new PrintWriter(S.getOutputStream());
			PW.println("a re�u les donn�es");
			PW.flush();
			
		}
		
		
		System.out.println("serveur OFF");
	}
	
}
