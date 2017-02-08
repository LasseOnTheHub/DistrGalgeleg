package galgeleg;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.rmi.Naming;
import java.util.Scanner;



public class GalgelegKlient {



  public static void main(String[] args) throws Exception{
      //IGalgelogik spil =(IGalgelogik) Naming.lookup("rmi://localhost/galgeserver");
      Scanner in = new Scanner(System.in);
      URL url = new URL("http://[::]:9981/galgespil?wsdl");
      QName qname = new QName("http://galgeleg/", "GalgelogikService");
      Service service = Service.create(url, qname);

      IGalgelogik spil = service.getPort(IGalgelogik.class);


      spil.authenticateUser("keg", "mis");

      spil.nulstil();
/*
    try {
      spil.hentOrdFraDr();
    } catch (Exception e) {
      e.printStackTrace();
    }
*/

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
