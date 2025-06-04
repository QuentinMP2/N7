/**
 * Définition d'un agenda individuel.
 */
public class AgendaIndividuel extends AgendaAbstrait {

	/** Le texte des rendez-vous. */
	private String[] rendezVous;


	/**
	 * Créer un agenda vide (avec aucun rendez-vous).
	 *
	 * @param nom le nom de l'agenda
	 * @throws IllegalArgumentException si nom nul ou vide
	 */
	public AgendaIndividuel(String nom) {
		super(nom);
		this.rendezVous = new String[Agenda.CRENEAU_MAX + 1];
			// On gaspille une case (la première qui ne sera jamais utilisée)
			// mais on évite de nombreux « creneau - 1 »
	}


	@Override
	public void enregistrer(int creneau, String rdv) throws OccupeException {
		verifierCreneauValide(creneau);
		if (this.rendezVous[creneau] != null) {
			throw new OccupeException("Créneau occupé.");
		}
		if (rdv == null || rdv.isEmpty()) {
			// On utilise ici le concept de l'évaluation paresseuse.
			throw new IllegalArgumentException("Le nom d'un objet doit contenir au" +
					"moins un caractère.");
		}
		this.rendezVous[creneau] = rdv;
	}


	@Override
	public boolean annuler(int creneau) {
		verifierCreneauValide(creneau);
		boolean modifie = this.rendezVous[creneau] != null;
		this.rendezVous[creneau] = null;
		return modifie;
	}


	@Override
	public String getRendezVous(int creneau) throws LibreException {
		verifierCreneauValide(creneau);
		if (this.rendezVous[creneau] == null) {
			throw new LibreException("Le créneau est libre.");
		}
		return this.rendezVous[creneau];
	}


}
