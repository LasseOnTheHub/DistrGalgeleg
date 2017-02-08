package galgeleg;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lasse on 2/8/17.
 */
public class UserAuthenticator {
Brugeradmin ba;
boolean authenticated=false;
    public void UserAuthenticator() throws MalformedURLException {
        URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
        QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
        Service service = Service.create(url, qname);
        ba = service.getPort(Brugeradmin.class);
    }

    public void authenticate(String username, String password)
    {

        Bruger b = ba.hentBruger("s145182", "jegerenmissekat");
        System.out.println("Fik bruger = " + b);
        //Bruger b = ba.hentBruger(username, password);

        if (b != null)
        {
            authenticated = true;
        }
        else {
            authenticated = false;
        }
        }
}
