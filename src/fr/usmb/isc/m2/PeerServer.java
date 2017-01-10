package fr.usmb.isc.m2;
import java.io.*;
import java.net.*;
import java.security.*;

import javax.net.ssl.*;

import com.sun.net.ssl.*;

public class PeerServer {

  private static final int PORT = 8080;

  public static void main(String[] args) throws Exception {
    char[] passphrase = "password".toCharArray();
    KeyStore keystore = KeyStore.getInstance("JKS");
    keystore.load(new FileInputStream(".keystore"), passphrase);
    KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    kmf.init(keystore, passphrase);
    SSLContext context = SSLContext.getInstance("TLS");
    KeyManager[] keyManagers = kmf.getKeyManagers();

    context.init(keyManagers, null, null);

    SSLServerSocketFactory ssf = context.getServerSocketFactory();
    ServerSocket ss = ssf.createServerSocket(PORT);

    Socket s = ss.accept();

    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

    String line = null;
    while (((line = in.readLine()) != null)) {
      System.out.println(line);
    }
    in.close();
    s.close();
  }
}