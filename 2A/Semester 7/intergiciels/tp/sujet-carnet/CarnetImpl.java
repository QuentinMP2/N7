import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

public class CarnetImpl implements Carnet {
    
    public static int NB_CARNETS = 2;
    private Map<String, String> fiches;
    
    public CarnetImpl() {
        this.fiches = new HashMap<String, String>();
    }

    public void Ajouter(SFiche sf) throws RemoteException {
        this.fiches.put(sf.getNom(), sf.getEmail());
    }

    public RFiche Consulter(String n, boolean forward) throws RemoteException {
        RFiche resultat = null;

        try {
            if (!this.fiches.containsKey(n)) {
                for (int i = 0; i <= NB_CARNETS; i++) {
                    Carnet nextCarnet =(Carnet)Naming.lookup("//localhost/Carnet" + i);
                    nextCarnet.Consulter(n, false);
                }
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return resultat;
    }

    public static void main(String args[]) {
        try {
            LocateRegistry.createRegistry(4000);
            for (int i = 1; i <= NB_CARNETS; i++)
                Naming.bind("//localhost/Carnet" + i, new CarnetImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
