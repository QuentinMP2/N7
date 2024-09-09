import com.libgdx.roguelike.Case;
import com.libgdx.roguelike.Etage;
import com.libgdx.roguelike.Portes.Porte;
import com.libgdx.roguelike.salles.Salle;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class GenerationProceduraleTest {

    // Nombre d'étages sur lesquelles on va appliquer les tests
    private final static int NOMBRE_ETAGES_TEST = 100000;

    // Liste d'étages créés par génération procédurale
    ArrayList<Etage> listeEtages = new ArrayList<>();


    @Before public void setUp() {
        // Initialiser et générer les étages
        for (int i = 1; i <= NOMBRE_ETAGES_TEST; i++) {
            Etage etageCourant = new Etage(i);
            etageCourant.generer();
            listeEtages.add(etageCourant);
        }
    }

    /**
     * Tester l'unicité des coordonnées pour chaque salle et par étage.
     */
    @Test public void testerUniciteCases() {
        String messageErreur = "Erreur : Deux salles ou plus on la même case";
        boolean uniciteCase = true;
        for (Etage etage : listeEtages) {

            for (int i = 0; i < etage.getSallesPresentes().size(); i++) {
                Case caseCourante = etage.getSallesPresentes().get(i).getCase();

                for (int j = 0; j < etage.getSallesPresentes().size(); j++) {
                    Case caseATester = etage.getSallesPresentes().get(j).getCase();

                    if (i != j && caseCourante.isEqual(caseATester)) {
                        uniciteCase = false;
                    }
                }
            }
        }
        assertTrue(messageErreur, uniciteCase);
    }

    /**
     * Tester pour chaque porte permettant de relier deux salles,
     * si elle est présente à la fois dans la liste de portes de l'une et dans la liste de portes de l'autre salle.
     * Cela revient à vérifier qu'il existe exactement une paire de "la même porte" dans toutes les portes
     * de toutes les salles de l'étage.
     */
    @Test public void testerSymetriePortes() {
        for (Etage etage : listeEtages) {
            ArrayList<Case> listeCasesPortes = new ArrayList<>();
            // Remplir la liste avec toutes les portes de chaque salle
            // On utilisera ici les coordonnées de chaque porte
            for (Salle salle : etage.getSallesPresentes()) {
                for (Porte porte : salle.getPortes()) {
                    listeCasesPortes.add(salle.getCoordonneesPorte(porte));
                    //System.out.println(salle.getCoordonneesPorte(porte).toString());
                }
            }

            //System.out.print("\n");

            // S'assurer que le nombre de portes dans listeCasesPortes est pair
            int nbTestPair = listeCasesPortes.size() % 2;
            assertEquals("Erreur Symetrie Porte : nombre de portes totale de chaque salle non pair", 0, nbTestPair);

            // Compter le nombre de paires et s'assurer qu'il correspond à la taille
            // de listeCasesPortes divisé par deux
            int nbPairesPortes = 0;
            for (int i = 0; i < listeCasesPortes.size(); i++) {
                Case caseCourante = listeCasesPortes.get(i);
                int compteurCaseCourante = 1;

                // On commence à l'indice i pour ne pas compter plusieurs fois les cases courantes déjà traitées
                for (int j = i+1; j < listeCasesPortes.size(); j++) {
                    Case caseATester = listeCasesPortes.get(j);
                    if (caseCourante.isEqual(caseATester)) {
                        compteurCaseCourante++;
                    }
                }

                if (compteurCaseCourante == 2) {
                    nbPairesPortes++;
                }
            }

            // S'assurer que le nombre de paires de portes correspond au nombre total de portes de chaque
            // salle divisé par deux
            String messageErreur = "Erreur : relation de symétrie pour au moins un couple de porte à la même case";
            int nbSalles = listeCasesPortes.size()/2;
            assertEquals(messageErreur, nbSalles, nbPairesPortes);
        }
    }

    /**
     * Tester que pour chaque porte de chaque salle que la salle suivante stockée correspond à la salle suivante
     * annoncée (i.e. il a égalité entre caseSalleSuivante et salleSuivante.getCase())
     */
    @Test public void testerCoherenceCaseSuivanteSalleSuivante() {
        String messageErreur = "Erreur : Incohérence dans la construction des portes entre caseSuivante et SalleSuivante";
        for (Etage etage : listeEtages) {

            for (Salle salle: etage.getSallesPresentes()) {

                for (Porte porte : salle.getPortes()) {
                    boolean coherenceCoordonnees = porte.getCaseSalleSuivante().isEqual(porte.getSalleSuivante().getCase());

                    assertTrue(messageErreur, coherenceCoordonnees);
                }
            }
        }
    }

}
