import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/* Squelette d'une solution avec un moniteur.
 * Il manque le moniteur (verrou + variables conditions).
 */
public class PhiloMonApproche2 implements StrategiePhilo {

    // État d'un philosophe : pense, mange, demande ?
    private EtatFourchette[] etat;

    /** Verrou. */
    private final Lock verrou;

    /** Variable condition. */
    private final Condition[] estDispo;

    /****************************************************************/

    public PhiloMonApproche2(int nbPhilosophes) {
        this.etat = new EtatFourchette[nbPhilosophes];
        for (int i = 0; i < nbPhilosophes; i++) {
            etat[i] = EtatFourchette.Table;
        }
        this.verrou = new ReentrantLock();
        this.estDispo = new Condition[nbPhilosophes];
        for (int i = 0; i < nbPhilosophes; i++) {
            this.estDispo[i] = verrou.newCondition();
        }
    }

    public void demanderFourchettes(int no) throws InterruptedException {
        this.verrou.lock();
        int fourchetteGauche = Main.FourchetteGauche(no);
        int fourchetteDroite = Main.FourchetteDroite(no);

        if (etat[fourchetteGauche] == EtatFourchette.AssietteGauche) {
            this.estDispo[fourchetteGauche].await();
        }
        this.etat[fourchetteGauche] = EtatFourchette.AssietteDroite;
        IHMPhilo.poser(Main.FourchetteGauche(no), EtatFourchette.AssietteDroite);

        if (etat[fourchetteDroite] == EtatFourchette.AssietteDroite) {
            this.estDispo[fourchetteDroite].await();
        }
        this.etat[fourchetteDroite] = EtatFourchette.AssietteGauche;
        IHMPhilo.poser(Main.FourchetteDroite(no), EtatFourchette.AssietteGauche);

        this.verrou.unlock();
    }

    public void libererFourchettes(int no) {
        this.verrou.lock();
        int fourchetteGauche = Main.FourchetteGauche(no);
        int fourchetteDroite = Main.FourchetteDroite(no);

        IHMPhilo.poser(Main.FourchetteGauche(no), EtatFourchette.Table);
        this.etat[fourchetteGauche] = EtatFourchette.Table;
        this.estDispo[fourchetteGauche].signal();

        IHMPhilo.poser(Main.FourchetteDroite(no), EtatFourchette.Table);
        this.etat[fourchetteDroite] = EtatFourchette.Table;
        this.estDispo[fourchetteDroite].signal();

        this.verrou.unlock();
    }

    public String nom() {
        return "Moniteur";
    }

}

