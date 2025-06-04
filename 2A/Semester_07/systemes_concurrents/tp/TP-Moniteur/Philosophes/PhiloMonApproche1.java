import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/* Squelette d'une solution avec un moniteur.
 * Il manque le moniteur (verrou + variables conditions).
 */
public class PhiloMonApproche1 implements StrategiePhilo {

    // État d'un philosophe : pense, mange, demande ?
    private EtatPhilosophe[] etat;

    /** Verrou. */
    private final Lock verrou;

    /** Variable condition. */
    private final Condition[] peutManger;

    /****************************************************************/

    public PhiloMonApproche1(int nbPhilosophes) {
        this.etat = new EtatPhilosophe[nbPhilosophes];
        for (int i = 0; i < nbPhilosophes; i++) {
            etat[i] = EtatPhilosophe.Pense;
        }
        this.verrou = new ReentrantLock();
        this.peutManger = new Condition[nbPhilosophes];
        for (int i = 0; i < nbPhilosophes; i++) {
            this.peutManger[i] = verrou.newCondition();
        }
    }

    public void demanderFourchettes(int no) throws InterruptedException {
        this.verrou.lock();
        int philoGauche = Main.PhiloGauche(no);
        int philoDroite = Main.PhiloDroite(no);
        etat[no] = EtatPhilosophe.Demande;
        while (etat[philoGauche] == EtatPhilosophe.Mange || etat[philoDroite] == EtatPhilosophe.Mange) {
            this.peutManger[no].await();
        }
        etat[no] = EtatPhilosophe.Mange;
        // j'ai les fourchette G et D
        IHMPhilo.poser(Main.FourchetteGauche(no), EtatFourchette.AssietteDroite);
        IHMPhilo.poser(Main.FourchetteDroite(no), EtatFourchette.AssietteGauche);
        this.verrou.unlock();
    }

    public void libererFourchettes(int no) {
        this.verrou.lock();
        IHMPhilo.poser(Main.FourchetteGauche(no), EtatFourchette.Table);
        IHMPhilo.poser(Main.FourchetteDroite(no), EtatFourchette.Table);
        etat[no] = EtatPhilosophe.Pense;
        int philoGauche = Main.PhiloGauche(no);
        int philoDroite = Main.PhiloDroite(no);
        int philoGauche2 = Main.PhiloGauche(Main.PhiloGauche(no));
        int philoDroite2 = Main.PhiloDroite(Main.PhiloDroite(no));

        if (etat[philoGauche2] != EtatPhilosophe.Mange) {
            this.peutManger[philoGauche].signal();
        }

        if (etat[philoDroite2] != EtatPhilosophe.Mange) {
            this.peutManger[philoDroite].signal();
        }

        this.verrou.unlock();
    }

    public String nom() {
        return "Moniteur";
    }

}

