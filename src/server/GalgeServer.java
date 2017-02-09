package server;

import javax.xml.ws.Endpoint;

/**
 * Created by lasse on 2/3/17.
 */
public class GalgeServer {

    public static void main(String[] arg) throws Exception
    {
        // Enten: Kør programmet 'rmiregistry' fra mappen med .class-filerne, eller:
        //java.rmi.registry.LocateRegistry.createRegistry(1081); // start i server-JVM

        IGalgelogik g = new Galgelogik() {
        };
        Endpoint.publish("http://[::]:9981/galgespil", new Galgelogik());
        System.out.println("Brugeradmin publiceret over SOAP");
        //Naming.rebind("rmi://localhost/galgeserver", g);
        //System.out.println("galgeserver registreret.");
    }
}
