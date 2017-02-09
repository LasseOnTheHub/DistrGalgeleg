package galgeleg;

import server.IGalgelogik;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Scanner;



public class BenytGalgelegModServer {

    private static boolean authenticated=false;

  public static void main(String[] args) throws Exception{
      Scanner in = new Scanner(System.in);
      //URL url = new URL("http://[::]:9981/galgespil?wsdl");
      URL url = new URL("http://ubuntu4.javabog.dk:9981/galgespil?wsdl");
      QName qname = new QName("http://server/", "GalgelogikService");
      Service service = Service.create(url, qname);

      IGalgelogik spil = service.getPort(IGalgelogik.class);

      while (!authenticated){
          System.out.println("Indtast brugernavn");
          String username = in.next();
          System.out.println("Indtast password");
          String password = in.next();

          boolean login = spil.login(username,password);

          if (login) {
              authenticated = true;
          }
          else {
              authenticated = false;
              System.out.println("Forkert brugernavn eller password. Prøv igen.");
          }
      }

      spil.nulstil();

      spil.logStatus();

      while (!spil.erSpilletSlut()) {
          //spil.logStatus();
          System.out.println("Ordet: "+spil.getSynligtOrd());
          System.out.println("Gæt et bogstav");
          String input = in.next();
          spil.gætBogstav(input);
          spil.getBrugteBogstaver();
          spil.getAntalForkerteBogstaver();
      }
      if (spil.erSpilletVundet())
      {
          System.out.println("Tillykke du gættede det rigtige ord: " + spil.getOrdet());
      }
      else
      {
          System.out.println("Desværre tabte du. Ordet var: "+spil.getOrdet());
      }
  }
}
