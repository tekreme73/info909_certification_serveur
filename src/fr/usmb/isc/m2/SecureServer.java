package fr.usmb.isc.m2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

class SecureServer
{
	public static void main ( String args[] ) throws Exception
	{
		System.err.println( " main for connection… " );
		SecureServer server = new SecureServer();
		server.runServer();
	}
	
	private final SSLServerSocket serverSocket;
	
	public SecureServer () throws Exception {
		
		System.setProperty( "javax.net.ssl.keyStore", System.getProperty( "user.dir" ) + "/resources/security/monCertificat" );
		System.setProperty( "javax.net.ssl.keyStorePassword", "azerty" );
		
		SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		this.serverSocket = (SSLServerSocket) socketFactory.createServerSocket( 8080 );
	}
	
	private void runServer ()
	{
		while ( true )
		{
			try
			{
				System.err.println( " Waiting for connection… " );
				SSLSocket socket = (SSLSocket) this.serverSocket.accept();
				BufferedReader input = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
				PrintWriter output = new PrintWriter( new OutputStreamWriter( socket.getOutputStream() ) );
				output.println( " C’est bon,  " + input.readLine() );
				output.close();
				input.close();
				socket.close();
			} catch ( IOException ioException )
			{
				ioException.printStackTrace();
			}
		}
	}
}