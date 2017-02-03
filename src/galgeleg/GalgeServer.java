package galgeleg;

import java.rmi.Naming;

/**
 * Created by lasse on 2/3/17.
 */
public class GalgeServer {

    public static void main(String[] arg) throws Exception
    {
        // Enten: Kør programmet 'rmiregistry' fra mappen med .class-filerne, eller:
        java.rmi.registry.LocateRegistry.createRegistry(1099); // start i server-JVM

        IGalgelogik g = new Galgelogik() {
        };
        Naming.rebind("rmi://localhost/galgeserver", g);
        System.out.println("galgeserver registreret.");
    }
}
