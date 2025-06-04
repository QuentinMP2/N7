import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class CarnetImpl extends UnicastRemoteObject implements Carnet {

	private Map<String, SFiche> fiches;
	private int n;
	public static int nb_carnets = 2;

	public CarnetImpl(int n) throws RemoteException {
		fiches = new HashMap<>(); // or any other desired size
		this.n = n;
	}

	@Override
	public void Ajouter(SFiche sf) throws RemoteException {
		fiches.put(sf.getNom(), sf);
		System.out.println("Ajout de la fiche " + sf.getNom());

	}

	@Override
	public RFiche Consulter(String n, boolean forward) throws RemoteException {
		System.out.println("Consulter(" + n + "," + forward + ")");
		SFiche sf = fiches.get(n);

		if (sf == null && forward) {
			for (int i = 1; i <= nb_carnets; i++) {
				if (i != this.n) {
					Carnet carnet;
					try {
						carnet = (Carnet) Naming.lookup("//localhost:4000/Carnet" + i);
						RFiche rf = carnet.Consulter(n, false);
						if (rf != null) {
							return rf;
						}
					} catch (MalformedURLException | RemoteException | NotBoundException e) {
						e.printStackTrace();
					}

				}
			}
		}

		if (sf == null) {
			return null;
		}

		return new RFicheImpl(sf);

	}

	public static void main(String args[]) throws RemoteException, MalformedURLException, AlreadyBoundException {
		LocateRegistry.createRegistry(4000);
		Naming.bind("//localhost:4000/Carnet1", new CarnetImpl(1));
		Naming.bind("//localhost:4000/Carnet2", new CarnetImpl(2));
	}

}
