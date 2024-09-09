import java.util.List;
import java.util.Objects;

/** Classe modélisant les groupes d'agendas.
 * @author Quentin Pointeau
 */
public class GroupeAgenda extends AgendaAbstrait {

    /** Le groupe d'agendas. */
    private List<AgendaIndividuel> listeAgendas;

    public GroupeAgenda(String nom, List<AgendaIndividuel> listeAgendas) {
        super(nom);
        this.listeAgendas = listeAgendas;
    }

    @Override
    public void enregistrer(int creneau, String rdv) throws OccupeException {
        for (AgendaIndividuel agenda : this.listeAgendas) {
            agenda.enregistrer(creneau, rdv);
        }
    }

    @Override
    public boolean annuler(int creneau) {
        for (AgendaIndividuel agenda : this.listeAgendas) {
            agenda.annuler(creneau);
        }
    }

    @Override
    public String getRendezVous(int creneau) throws LibreException {
        int nombreCreneauxLibres = 0;
        String[] rdv = new String[this.listeAgendas.size()];

        for (AgendaIndividuel agenda : this.listeAgendas) {
            try {
                rdv[this.listeAgendas.indexOf(agenda)] = agenda.getRendezVous(creneau);
            } catch (LibreException e) {
                nombreCreneauxLibres++;
            }
        }

        if (nombreCreneauxLibres == this.listeAgendas.size()) {
            throw new LibreException("Tous les agendas sont libres pour le créneau " +
                    creneau + ".");
        }

        if () {
            
        }
    }
}
