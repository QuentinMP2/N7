// Time-stamp: <06 jui 2023 11:59 Philippe Queinnec>

import CSP.*;

/** Réalisation de la voie unique avec des canaux JCSP. */
/* Version avec condition d'acceptation */
public class VoieUniqueCondition implements VoieUnique {

    enum ChannelId { EntrerNS, EntrerSN, Sortir };
    
    private Channel<ChannelId> entrerNS;
    private Channel<ChannelId> entrerSN;
    private Channel<ChannelId> sortir;
    
    public VoieUniqueCondition() {
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

    public String nomStrategie () {
        return "Condition";
    }

    /****************************************************************/

    class Scheduler implements Runnable {
        private int nbTrainsNS = 0;
        private int nbTrainsSN = 0;
        public void run() {
            var gens = new GuardedChannel<>(entrerNS, () -> (nbTrainsSN == 0 && nbTrainsNS < 3));
            var gesn = new GuardedChannel<>(entrerSN, () -> (nbTrainsNS == 0 && nbTrainsSN < 3));
            var gs = new GuardedChannel<>(sortir, Predicate::True);
            var alt = new Alternative<>(gens, gesn, gs);
            
            while(true) {
                switch (alt.select()) {
                    case EntrerNS:
                        entrerNS.read();
                        nbTrainsNS++;
                        break;
                    case EntrerSN:
                        entrerSN.read();
                        nbTrainsSN++;
                        break;
                    case Sortir:
                        sortir.read();
                        if (nbTrainsNS > 0)
                            nbTrainsNS--;
                        else if (nbTrainsSN > 0)
                            nbTrainsSN--;
                        break;
                }
            }
        }
    } // class Scheduler
}

