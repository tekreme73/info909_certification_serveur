package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class ServeurSSL_old
{
	public static void main ( String[] args ) throws UnknownHostException, IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException
	{
		System.out.println( "serveur ON" );
		SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		
		
		SSLContext ctx = SSLContext.getInstance("TLS");
				
		InputStream in = new DataInputStream(new FileInputStream(new File(System.getProperty( "user.dir" ) + "/resources/security/client.cer")));
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(null, null);
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		
		in.close();
		
		ks.setCertificateEntry("dts", cert);
		
		char[] newpass = "azerty".toCharArray();
		//String name = "mykeystore.ks";
		//FileOutputStream output = new FileOutputStream(name);
		//ks.store(output, newpass);
		
		/* créer un SSLContext utilisant cette TrustManagerFactory */
		/* ici on configure le contexte SSL */
		
		TrustManagerFactory tmf = TrustManagerFactory.getInstance( "X.509" );
		//tmf.init( new CertPathTrustManagerParameters( params ) );
		
		ctx.init( null, tmf.getTrustManagers(), null );
		
		SSLSocketFactory sslSocketFactory = ctx.getSocketFactory();
		
		//SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket( address, port );

		
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