// Time-stamp: <06 jui 2023 11:58 Philippe Queinnec>

import CSP.*;

/** Réalisation de la voie unique avec des canaux JCSP. */
/* Version par automate d'états */
public class VoieUniqueAutomate implements VoieUnique {

    enum ChannelId { EntrerNS, EntrerSN, Sortir };
    
    private Channel<ChannelId> entrerNS;
    private Channel<ChannelId> entrerSN;
    private Channel<ChannelId> sortir;
    
    public VoieUniqueAutomate() {
        this.entrerNS = new Channel<>(ChannelId.EntrerNS);
        this.entrerSN = new Channel<>(ChannelId.EntrerSN);
        this.sortir = new Channel<>(ChannelId.Sortir);
        (new Thread(new Scheduler())).start();
    }

    public void entrer(Sens sens) {
        System.out.println("In  entrer " + sens);
        switch (sens) {
          case NS:
            entrerNS.write(true);
            break;
          case SN:
            entrerSN.write(true);
            break;
        }
        System.out.println("Out entrer " + sens);
    }

    public void sortir(Sens sens) {
        System.out.println("In  sortir " + sens);
        sortir.write(true);
        System.out.println("Out sortir " + sens);
    }

    public String nomStrategie() {
        return "Automate";
    }

    /****************************************************************/

    enum Etat {LIBRE, OCCUPE_NS, OCCUPE_SN};
    class Scheduler implements Runnable {
        private Etat etat = Etat.LIBRE;
        private int nbTrains = 0;
        public void run() {
            var altLibre = new Alternative<>(entrerNS, entrerSN);
            var altAllerNS = new Alternative<>(entrerNS, sortir);
            var altAllerSN = new Alternative<>(entrerSN, sortir);

            while (true) {
                if (etat == Etat.LIBRE) {
                    switch (altLibre.select()) {
                        case EntrerNS:
                            entrerNS.read();
                            etat = Etat.OCCUPE_NS;
                            nbTrains = 1;
                            break;
                        case EntrerSN:
                            entrerSN.read();
                            etat = Etat.OCCUPE_SN;
                            nbTrains = 1;
                    }
                } else if (etat == Etat.OCCUPE_NS) {
                    switch (altAllerNS.select()) {
                        case EntrerNS:
                            entrerNS.read();
                            nbTrains++;
                            break;
                        case Sortir:
                            sortir.read();
                            nbTrains--;
                            if (nbTrains == 0)
                                etat = Etat.LIBRE;
                    }
                } else if (etat == Etat.OCCUPE_SN) {
                    switch (altAllerSN.select()) {
                        case EntrerSN:
                            entrerSN.read();
                            nbTrains++;
                            break;
                        case Sortir:
                            sortir.read();
                            nbTrains--;
                            if (nbTrains == 0)
                                etat = Etat.LIBRE;
                    }
                }
            }
        }
    } // class Scheduler
}

