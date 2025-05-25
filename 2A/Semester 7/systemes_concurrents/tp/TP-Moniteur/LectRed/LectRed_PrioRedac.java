// Time-stamp: <28 oct 2022 09:24 queinnec@enseeiht.fr>

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Lecteurs/rédacteurs
 * stratégie d'ordonnancement : priorité aux rédacteurs,
 * implantation : avec un moniteur. */
public class LectRed_PrioRedac implements LectRed
{
    /** Verrou. */
    private final Lock verrou;

    /** Variables d'états. */
    private int nbLecteur;
    private int nbRedac;
    private int nbRedacAttente;

    /** Variables conditions. */
    private final Condition accesLec;
    private final Condition accesEcr;

    public LectRed_PrioRedac() {
        this.verrou = new ReentrantLock();
        this.accesLec = verrou.newCondition();
        this.accesEcr = verrou.newCondition();
    }

    public void demanderLecture() throws InterruptedException {
        this.verrou.lock();
        while (!(this.nbRedac == 0 && this.nbRedacAttente == 0)) {
            accesLec.await();
        }
        // { nbRedac == 0 && nbRedacAttente == 0 }
        this.nbLecteur++;
        this.accesLec.signal();
        this.verrou.unlock();
    }

    public void terminerLecture() throws InterruptedException {
        this.verrou.lock();
        this.nbLecteur--;
        if (this.nbLecteur == 0) {
            // { !nbRedacAttente > 0 }
            // { nbLecteur == 0 }
            this.accesEcr.signal();
        }
        this.verrou.unlock();
    }

    public void demanderEcriture() throws InterruptedException {
        this.verrou.lock();
        while (!(this.nbRedac == 0 && this.nbLecteur == 0)) {
            this.nbRedacAttente++;      // car on souhaite prioriser les rédacteurs
            this.accesEcr.await();
            this.nbRedacAttente--;
        }
        // { nbRedac == 0 && nbLecteur == 0 }
        this.nbRedac++;
        this.verrou.unlock();
    }

    public void terminerEcriture() throws InterruptedException {
        this.verrou.lock();
        this.nbRedac--;
        // { nbRedac == 0 && nbLecteur == 0 }
        if (this.nbRedacAttente > 0) {
            this.accesEcr.signal();
        } else {
            // { nbRedac == 0 && nbLecteur == 0 && nbRedacAttente == 0 }
            this.accesLec.signal();
        }
        this.verrou.unlock();
    }

    public String nomStrategie() {
        return "Stratégie: Priorité Rédacteurs.";
    }
}
