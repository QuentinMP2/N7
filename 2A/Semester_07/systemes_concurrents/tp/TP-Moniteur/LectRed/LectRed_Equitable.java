import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Synchro.Assert;

/** Lecteurs/rédacteurs
 * stratégie d'ordonnancement : équitable,
 * implantation : avec un moniteur. */
public class LectRed_Equitable implements LectRed
{
    /** Verrou. */
    private final Lock verrou;

    /** Variables d'états. */
    private int nbLecteur;
    private int nbRedac;
    private int nbLecteurAttente;
    private int nbRedacAttente;
    private boolean tourRedac; // true si c'est au tour des rédacteurs, false sinon

    /** Variables conditions. */
    private final Condition accesLec;
    private final Condition accesEcr;

    public LectRed_Equitable() {
        this.verrou = new ReentrantLock();
        this.accesLec = verrou.newCondition();
        this.accesEcr = verrou.newCondition();
        this.nbLecteur = 0;
        this.nbRedac = 0;
        this.nbLecteurAttente = 0;
        this.nbRedacAttente = 0;
        this.tourRedac = true;
    }

    public void demanderLecture() throws InterruptedException {
        this.verrou.lock();
        while (this.nbRedac > 0 || (this.nbRedacAttente > 0 && this.tourRedac)) {
            this.nbLecteurAttente++;
            this.accesLec.await();
            this.nbLecteurAttente--;
        }
        // { nbRedac == 0 && nbRedacAttente == 0 }
        this.nbLecteur++;
        this.accesLec.signalAll();
        this.verrou.unlock();
    }

    public void terminerLecture() throws InterruptedException {
        this.verrou.lock();
        this.nbLecteur--;
        this.tourRedac = true;
        if (this.nbLecteur == 0 && this.nbRedacAttente > 0) {
            // { !nbRedacAttente > 0 }
            // { nbLecteur == 0 }
            this.accesEcr.signal();
        }
        this.verrou.unlock();
    }

    public void demanderEcriture() throws InterruptedException {
        this.verrou.lock();
        while (this.nbRedac > 0 || this.nbLecteur > 0) {
            this.nbRedacAttente++;
            this.accesEcr.await();
            this.nbRedacAttente--;
        }
        // { nbRedac == 0 && nbLecteur == 0 }
        this.nbRedac++;
        this.accesEcr.signal();
        this.verrou.unlock();
    }

    public void terminerEcriture() throws InterruptedException {
        this.verrou.lock();
        this.nbRedac--;
        this.tourRedac = false;
        // { nbRedac == 0 && nbLecteur == 0 }
        if (this.nbLecteurAttente == 0) {
            this.accesEcr.signal();
        } else {
            // { nbRedac == 0 && nbLecteur == 0 && nbRedacAttente == 0 }
            this.accesLec.signal();
        }
        this.verrou.unlock();
    }

    public String nomStrategie() {
        return "Stratégie: Équitable.";
    }
}
