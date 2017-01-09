package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ServeurSSL
{
	public static void main ( String[] args ) throws UnknownHostException, IOException, NoSuchAlgorithmException, KeyStoreException
	{
		System.out.println( "serveur ON" );
		SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		
		
		SSLContext ctx = SSLContext.getInstance("TLS");
		

		TrustManager tm = new X509TrustManager() {
	    

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// TODO Auto-generated method stub xx
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub xx
			return null;
		}
	};
	
		//KeyStore ks = KeyStore.getInstance("JKS");
		//FileInputStream in = new FileInputStream("<path to cacerts"");
		//ks.load(in, "changeit".toCharArray);
	    
		
		InputStream in = new DataInputStream(new FileInputStream(new File("server.crt")));
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(null, null);
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		in.close();
		
		ks.setCertificateEntry("dts", cert);
		
		char[] newpass = "password".toCharArray();
		String name = "mykeystore.ks";
		FileOutputStream output = new FileOutputStream(name);
		ks.store(output, newpass);
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, "password".toCharArray());
		
		
		// Ouverture du socket, avec le port spécifique
		int port = 8080;
		SSLServerSocket ServerS = (SSLServerSocket) factory.createServerSocket( port );
		//ServerS.set
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