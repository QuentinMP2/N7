import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class RFicheImpl extends UnicastRemoteObject implements RFiche {
	String nom, email;

	public RFicheImpl(SFiche fiche) throws RemoteException {
		this.email = fiche.getEmail();
		this.nom = fiche.getNom();
	}

	@Override
	public String getNom() throws RemoteException {
		return nom;
	}

	@Override
	public String getEmail() throws RemoteException {
		return email;
	}

}
