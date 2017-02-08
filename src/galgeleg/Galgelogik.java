package galgeleg;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

@WebService(endpointInterface = "galgeleg.IGalgelogik")
public class Galgelogik implements IGalgelogik{

  private ArrayList<String> muligeOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;
  UserAuthenticator userAuthenticator;


  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }


  public String getSynligtOrd() {
    return synligtOrd;
  }


  public String getOrdet() {
    return ordet;
  }


  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }


  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }


  public boolean erSpilletVundet() {
    return spilletErVundet;
  }


  public boolean erSpilletTabt() {
    return spilletErTabt;
  }


  public boolean erSpilletSlut() {
    return spilletErTabt || spilletErVundet;
  }


  public Galgelogik() throws RemoteException {
      try {
          hentOrdFraDr();
      }catch (Exception e){
          System.out.println(e.getStackTrace());
      }
      userAuthenticator = new UserAuthenticator();
  }


  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    opdaterSynligtOrd();
  }


  private void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }


  public void gætBogstav(String bogstav) {
    if (bogstav.length() != 1) return;
    System.out.println("Der gættes på bogstavet: " + bogstav);
    if (brugteBogstaver.contains(bogstav)) return;
    if (spilletErVundet || spilletErTabt) return;

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public void logStatus() {
    System.out.println("---------- ");
    System.out.println("- ordet (skult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt) System.out.println("- SPILLET ER TABT");
    if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
  }


  public static String hentUrl(String url) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }


  public void hentOrdFraDr() throws Exception{
    String data = hentUrl("http://dr.dk");
    System.out.println("data = " + data);

    data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
    System.out.println("data = " + data);
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
  }

  public void authenticateUser(String username, String password){

      try{
          URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
          QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
          Service service = Service.create(url, qname);
          Brugeradmin ba = service.getPort(Brugeradmin.class);
          Bruger b = ba.hentBruger("s145182", "jegerenmissekat");
          System.out.println("Fik bruger = " + b);
      }catch (MalformedURLException mfe)
      {
          System.out.println(mfe.getStackTrace().toString());
      }
  }

  public boolean isUserAuthenticated()
  {
      return userAuthenticator.authenticated;
  }
}
