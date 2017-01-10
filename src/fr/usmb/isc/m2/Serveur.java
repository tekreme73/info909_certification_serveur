package fr.usmb.isc.m2;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocketFactory;

public class Serveur {
  public static void main(String args[]) throws Exception {
    System.setProperty("javax.net.ssl.keyStore", System.getProperty( "user.dir" ) + "/resources/security/clientkeystore");
    System.setProperty("javax.net.ssl.keyStorePassword", "azerty");
    SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
    ServerSocket ss = ssf.createServerSocket(8080);
    while (true) {
      Socket s = ss.accept();
      PrintStream out = new PrintStream(s.getOutputStream());
      out.println("Hi");
      out.close();
      s.close();
    }

  }
}