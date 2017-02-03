package galgeleg;

import java.rmi.Naming;
import java.util.Scanner;

public class GalgelegKlient {

  public static void main(String[] args) throws Exception{
      IGalgelogik spil =(IGalgelogik) Naming.lookup("rmi://localhost/galgeserver");
      spil.nulstil();
/*
    try {
      spil.hentOrdFraDr();
    } catch (Exception e) {
      e.printStackTrace();
    }
*/
      Scanner in = new Scanner(System.in);
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
