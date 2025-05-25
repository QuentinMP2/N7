import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.net.Socket;
import java.net.ServerSocket;

public class LoadBalancer extends Thread {
    
    static String hosts[] = {"localhost", "localhost"};
    static int ports[] = {8081,8082};
    static int nbHosts = 2;
    static Random rand = new Random();
    Socket client;
    

    public LoadBalancer(Socket s) {
        client = s;
    }
    
    public static void main(String arg[]) {
        try {
            ServerSocket ss = new ServerSocket(8080); // port 8080 par exemple
            while (true) {
                new LoadBalancer(ss.accept()).start(); // /!\ start et pas pas de run sinon on run sur 
                // /!\ le thread principal du coup on bloque
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        try {
            int nb = LoadBalancer.rand.nextInt(LoadBalancer.nbHosts); // le serveur à qui on va filer la requête
            Socket server = new Socket(LoadBalancer.hosts[nb], LoadBalancer.ports[nb]);
            InputStream cis = client.getInputStream();
            InputStream sis = server.getInputStream();
            OutputStream cos = client.getOutputStream();
            OutputStream sos = server.getOutputStream();
            
            /* On fait l'hypothèse qu'on peut tout lire en une seule fois 
            * (~ réel sur un réseau local). */
            int nbLus;
            byte[] buff = new byte[1024];
            nbLus = cis.read(buff);
            sos.write(buff, 0, nbLus);
            nbLus = sis.read(buff);
            cos.write(buff, 0, nbLus);
            server.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
