/**
 *
 */
package fr.usmb.isc.m2;

import java.io.IOException;
import java.net.ServerSocket;

import generic.BufferedReader;
import generic.InputStreamReader;
import generic.MoteurCalculSimple;
import generic.PrintWriter;
import generic.String;

/**
 * 
 * @author Abdou ABDELMOUMNI(abdou.abdelmoumni@etu.univ-smb.fr)
 * @author Rémi REBILLARD (remi.rebillard@etu.univ-smb.fr)
 * @author Fanny RIBARD (fanny.ribard@etu.univ-smb.fr)
 * @author Loïc ROBERGEON (loic.robergeon@etu.univ-smb.fr)
 */
public class Serveur
{
	public static int			DEFAULT_PORT	= 2000;

	private final ServerSocket	server;

	public Serveur () throws IOException {
		this( Serveur.DEFAULT_PORT );
	}

	public Serveur ( int port ) throws IOException {
		this.server = new ServerSocket( port );
		
		// Tant que la socket est ouverte
		while(true){
			
			// Récupère les informations du buffer
			InputStreamReader ISR = new InputStreamReader(S.getInputStream());
			BufferedReader BR = new BufferedReader(ISR);			
			
			// Renvoie les données dans le buffer
			PrintWriter PW = new PrintWriter(S.getOutputStream());
			PW.println(res);
			PW.flush();
			
		}
				
				
	}

	/**
	 * @return the server
	 */
	public ServerSocket getServer ()
	{
		return this.server;
	}
}
