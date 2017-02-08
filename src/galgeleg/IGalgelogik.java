package galgeleg;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.util.ArrayList;

@WebService
public interface IGalgelogik{
    @WebMethod ArrayList<String> getBrugteBogstaver();

    @WebMethod String getSynligtOrd();

    @WebMethod String getOrdet();

    @WebMethod int getAntalForkerteBogstaver();

    @WebMethod boolean erSidsteBogstavKorrekt();

    @WebMethod boolean erSpilletVundet();

    @WebMethod boolean erSpilletTabt();

    @WebMethod boolean erSpilletSlut();

    @WebMethod void nulstil();

    @WebMethod void gætBogstav(String bogstav);

    @WebMethod void logStatus();

    @WebMethod void hentOrdFraDr() throws Exception;

    @WebMethod void authenticateUser(String username, String password);

    @WebMethod boolean isUserAuthenticated();
}
