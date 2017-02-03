package galgeleg;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.ArrayList;

/**
 * Created by lasse on 2/3/17.
 */
public interface IGalgelogik extends Remote, Serializable {
    ArrayList<String> getBrugteBogstaver() throws java.rmi.RemoteException;

    String getSynligtOrd() throws java.rmi.RemoteException;

    String getOrdet() throws java.rmi.RemoteException;

    int getAntalForkerteBogstaver() throws java.rmi.RemoteException;

    boolean erSidsteBogstavKorrekt() throws java.rmi.RemoteException;

    boolean erSpilletVundet() throws java.rmi.RemoteException;

    boolean erSpilletTabt() throws java.rmi.RemoteException;

    boolean erSpilletSlut() throws java.rmi.RemoteException;

    void nulstil() throws java.rmi.RemoteException;

    void gætBogstav(String bogstav) throws java.rmi.RemoteException;

    void logStatus() throws java.rmi.RemoteException;

    void hentOrdFraDr()  throws Exception;
}
