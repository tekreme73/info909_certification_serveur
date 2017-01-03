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
			
			// Récupère les informations du buffer
			InputStreamReader ISR = new InputStreamReader(S.getInputStream());
			BufferedReader BR = new BufferedReader(ISR);
			String calcul = BR.readLine();
			

			// Renvoie les données dans le buffer
			PrintWriter PW = new PrintWriter(S.getOutputStream());
			PW.println(res);
			PW.flush();
			
		}
		
		System.out.println("serveur OFF");
		
		
	}
	
}
