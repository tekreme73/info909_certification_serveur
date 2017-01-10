package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Classe principale de l'application serveur (10/01/2017)
 *
 * @author Abdou ABDELMOUMNI(abdou.abdelmoumni@etu.univ-smb.fr)
 * @author Rémi REBILLARD (remi.rebillard@etu.univ-smb.fr)
 * @author Fanny RIBARD (fanny.ribard@etu.univ-smb.fr)
 * @author Loïc ROBERGEON (loic.robergeon@etu.univ-smb.fr)
 */
public class HTTPSServer
{
	// Thread handling the socket from client
	static class ServerThread extends Thread
	{
		private SSLSocket sslSocket = null;
		
		ServerThread ( SSLSocket sslSocket ) {
			this.sslSocket = sslSocket;
		}
		
		@Override
		public void run ()
		{
			this.sslSocket.setEnabledCipherSuites( this.sslSocket.getSupportedCipherSuites() );
			
			try
			{
				// Start handshake
				this.sslSocket.startHandshake();
				
				// Get session after the connection is established
				SSLSession sslSession = this.sslSocket.getSession();
				
				System.out.println( "SSLSession :" );
				System.out.println( "\tProtocol : " + sslSession.getProtocol() );
				System.out.println( "\tCipher suite : " + sslSession.getCipherSuite() );
				
				// Start handling application content
				InputStream inputStream = this.sslSocket.getInputStream();
				OutputStream outputStream = this.sslSocket.getOutputStream();
				
				BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
				PrintWriter printWriter = new PrintWriter( new OutputStreamWriter( outputStream ) );
				
				String line = null;
				while ( ( line = bufferedReader.readLine() ) != null )
				{
					System.out.println( "Input : " + line );
					
					if ( line.trim().isEmpty() )
					{
						break;
					}
				}
				
				// Write data
				printWriter.print( "HTTP/1.1 200\r\n" );
				printWriter.flush();
				
				this.sslSocket.close();
			} catch ( Exception ex )
			{
				ex.printStackTrace();
			}
		}
	}
	
	public static void main ( String[] args )
	{
		HTTPSServer server = new HTTPSServer();
		server.run();
	}
	
	private int				port			= 8080;
	
	private final boolean	isServerDone	= false;
	
	HTTPSServer () {}
	
	HTTPSServer ( int port ) {
		this.port = port;
	}
	
	// Create the and initialize the SSLContext
	private SSLContext createSSLContext ()
	{
		try
		{
			KeyStore keyStore = KeyStore.getInstance( "JKS" );
			keyStore.load( new FileInputStream( System.getProperty( "user.dir" ) + "/resources/security/clientkeystore" ),
					"azerty".toCharArray() );
			
			// Create key manager
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance( "SunX509" );
			keyManagerFactory.init( keyStore, "azerty".toCharArray() );
			KeyManager[] km = keyManagerFactory.getKeyManagers();
			
			// Create trust manager
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance( "SunX509" );
			trustManagerFactory.init( keyStore );
			TrustManager[] tm = trustManagerFactory.getTrustManagers();
			
			// Initialize SSLContext
			SSLContext sslContext = SSLContext.getInstance( "TLSv1" );
			sslContext.init( km, null, null );
			
			return sslContext;
		} catch ( Exception ex )
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	// Start to run the server
	public void run ()
	{
		SSLContext sslContext = this.createSSLContext();
		
		try
		{
			// Create server socket factory
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
			
			// Create server socket
			SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket( this.port );
			
			System.out.println( "SSL server started" );
			while ( !this.isServerDone )
			{
				SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
				
				// Start the server thread
				new ServerThread( sslSocket ).start();
			}
		} catch ( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}