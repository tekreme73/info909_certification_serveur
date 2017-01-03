/**
 *
 */
package fr.usmb.isc.m2;

import java.io.IOException;

/**
 * Classe principale de l'application serveur (03/01/2017)
 *
 * @author Abdou ABDELMOUMNI(abdou.abdelmoumni@etu.univ-smb.fr)
 * @author Rémi REBILLARD (remi.rebillard@etu.univ-smb.fr)
 * @author Fanny RIBARD (fanny.ribard@etu.univ-smb.fr)
 * @author Loïc ROBERGEON (loic.robergeon@etu.univ-smb.fr)
 */
public class App
{
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main ( String[] args ) throws IOException
	{
		Serveur srv = new Serveur();
		//TODO Démarrer une action sur le serveur
		// srv.handleMessages();
	}
	
}
