package com.libgdx.roguelike;

import com.libgdx.roguelike.salles.*;

import java.util.*;

public class Etage {

    /** Nombre de directions : EST, OUEST, NORD, SUD. */
    public static final int NB_DIRECTIONS = 4;

    /** Bornes pour le nombre de salles dans l'étage. */
    public static final int BORNE_MIN = 6, BORNE_MAX = 8;

    /** Générateur aléatoire. */
    private static final Random GENERATEUR = new Random();

    /** Liste des salles présentes dans l'étage. */
    private final ArrayList<Salle> sallesPresentes;

    /** Liste des salles disponibles dans l'étage.
     * <p>Clé : le nom de la salle</p>
     * <p>Valeur : le nombre restant maximal de placement pour la salle</p>
     */
    private final HashMap<String, Integer> sallesDisponibles;

    /** Liste des noms des salles présentes dans l'étage. */
    private final ArrayList<String> nomSalles;

    /** Numéro de l'étage. */
    private final int numero;


    /** Construire un étage à partir d'un numéro d'étage.
     * @param numero numéro de l'étage
     */
    public Etage(int numero) {
        this.sallesDisponibles = new HashMap<>();
        sallesDisponibles.put("MONSTRE", 15);

        this.nomSalles = new ArrayList<>(sallesDisponibles.keySet());

        this.sallesPresentes = new ArrayList<>();
        this.numero = numero;
    }

    public ArrayList<Salle> getSallesPresentes() {
        return this.sallesPresentes;
    }

    public Salle getSalleDepart() {return this.sallesPresentes.get(0);}

    public int getNumero() {
        return this.numero;
    }

    /** Tirer une salle aléatoirement.
     * @return une salle parmis toutes celles existantes
     */
    private Salle tirerSalle(Position position, Salle sallePrec) {
        int nbAleatoire;
        String nomSalleTiree;
        Salle salleTiree = null;
        boolean salleEstTiree = false;

        while (!salleEstTiree) {
            nbAleatoire = GENERATEUR.nextInt(this.nomSalles.size());
            nomSalleTiree = this.nomSalles.get(nbAleatoire);

            if (this.estSalleDisponible(nomSalleTiree)) {
                switch (nomSalleTiree) {
                    case "MONSTRE":
                        salleTiree = new SalleMonstre(position, sallePrec);
                        this.actualiserDisponibilite(nomSalleTiree);
                        break;

                    default:
                        throw new RuntimeException("Etage erreur : salle inexistante");
                }
                salleEstTiree = true;
            }
        }
        return salleTiree;
    }

    /** Vérifie la disponibilité d'une salle.
     * @param salle la salle dont la disponibilite est à vérifier
     * @return Vrai si la salle est disponible, Faux sinon
     */
    private boolean estSalleDisponible(String salle) {
        return this.sallesDisponibles.get(salle) > 0;
    }

    /** Actualiser la disponibilité d'une salle.
     * @param salle la salle
     */
    private void actualiserDisponibilite(String salle) {
        int ancienNbDispo = this.sallesDisponibles.get(salle);
        this.sallesDisponibles.put(salle, ancienNbDispo - 1);
    }

    /** Tirer une case aléatoirement
     * @return la case tirée aléatoirement
     */
    private Case tirerCase() {
        int abscisse = GENERATEUR.nextInt(Case.MAX_X + 1);
        int ordonnee = GENERATEUR.nextInt(Case.MAX_Y + 1);
        return new Case(abscisse, ordonnee);
    }

    /**
     * Sélectionner une case adjacente à une case renseignée à partir d'un numéro de direction indiqué.
     * @param caseOrigine la case source à partir de laquelle on souhaite obtenir une case adjacente.
     * @param direction direction dans laquelle on souhaite obtenir la case adjacente
     *                  (0 : est, 1 : ouest, 2 : nord, 3 : sud)
     * @return une case adjacente à la case fournie et dans la direction indiquée.
     */
    private Case selectionnerCaseAdjacente(Case caseOrigine, int direction) {
        Case caseAdjacente = null;
        switch (direction) {
            case 0 :
                // EST
                caseAdjacente = new Case(caseOrigine.x + Salle.LONGUEUR, caseOrigine.y);
                break;
            case 1 :
                // OUEST
                caseAdjacente = new Case(caseOrigine.x - Salle.LONGUEUR, caseOrigine.y);
                break;
            case 2 :
                // NORD
                caseAdjacente = new Case(caseOrigine.x, caseOrigine.y + Salle.HAUTEUR);
                break;
            case 3 :
                // SUD
                caseAdjacente = new Case(caseOrigine.x, caseOrigine.y - Salle.HAUTEUR);
                break;
        }
        return caseAdjacente;
    }

    /**
     * Obtenir la salle source d'une position
     * @param position position dont on veut obtenir la salle source correspondant
     *                 à la case source.
     * @return la salle correspondant à la case source de position.
     */
    private Salle getSalleSource(Position position) {
        Iterator<Salle> iterator = sallesPresentes.iterator();
        Salle salleSource = null;
        boolean salleSourceTrouvee = false;
        while (iterator.hasNext() && !salleSourceTrouvee) {
            Salle salleCourante = iterator.next();
            if (salleCourante.getCase().isEqual(position.getCaseSource())) {
                salleSource = salleCourante;
                salleSourceTrouvee = true;
            }
        }
        return salleSource;
    }

    /** Génère l'étage de manière procédurale. */
    public void generer() {
        int nbSallesMax = BORNE_MIN + GENERATEUR.nextInt(BORNE_MAX - BORNE_MIN + 1);
        //int nbSallesMax = 18; Remarque expérimentale taille maximale par rapport à la dimension des salles
        int entierAleatoire;

        List<Position> listePositions = new ArrayList<>(); 

        // Placer la première Salle

        // Choisir une case de départ
        Case caseDepart = tirerCase();
        //System.out.println("Case de départ aux coordonnées : " + caseDepart);

        // Construire la salle de départ
        this.sallesPresentes.add(new SalleDepart(caseDepart));

        // Choisir la case suivante
        Case caseSuivante = null;
        boolean choixCaseSuivante = false;
        while (!choixCaseSuivante) {
            entierAleatoire = GENERATEUR.nextInt(NB_DIRECTIONS);
            caseSuivante = selectionnerCaseAdjacente(caseDepart, entierAleatoire);
            if (caseSuivante.estValide(this.sallesPresentes)) {
                choixCaseSuivante = true;
            }
        }

        // Initialiser la première position
        listePositions.add(new Position(caseSuivante, caseDepart));

        Position positionTiree;
        Salle salleTiree;
        Salle salleSource;
        while (this.sallesPresentes.size() < (nbSallesMax - 1)) {
            // Tirer position dans la SD (Structure de Donnée)
            entierAleatoire = GENERATEUR.nextInt(listePositions.size());
            positionTiree = listePositions.get(entierAleatoire);
            listePositions.remove(entierAleatoire);

            salleSource = getSalleSource(positionTiree);

            // Choisir une nouvelle salle
            salleTiree = tirerSalle(positionTiree, salleSource);


            // Ajouter salleTiree à l'étage
            this.sallesPresentes.add(salleTiree);

            // Relier la salle tirée à la case source :
            // La salle tirée est directement reliée à la case source
            // grâce à son constructeur

            // Relier la salle source à la salle tirée
            salleSource.ajouterPorte(positionTiree.getCaseLibre(), salleTiree);


            // Choisir des nouvelles positions
            Position nouvellePosition;
            Case caseCourante = salleTiree.getCase();
            for (int i = 0; i < Salle.NB_PORTES; i++) {
                caseSuivante = selectionnerCaseAdjacente(caseCourante, i);
                if (caseSuivante.estValide(this.sallesPresentes, listePositions)) { // REVOIR CE CRITERE ET AUSSI PRENDRE EN COMPTE
                    // LES POSITIONS DEJA PRESENTE DANS LA LISTE
                    nouvellePosition = new Position(caseSuivante, salleTiree.getCase());
                    listePositions.add(nouvellePosition);
                }
            }
        }

        // Affichage pour les tests
        /**
        for (Salle salle : sallesPresentes) {
            System.out.println("Coordoonées salle : " + salle.getCase());
            System.out.println("Nombre de porte(s) : " + salle.getPortes().size());
            System.out.println("Coordonnées des portes sur la carte :");
            for (Porte porte : salle.getPortes()) {
                System.out.println(salle.getCoordonneesPorte(porte));
            }
            System.out.println("\n");
        }
         */
        // Placer la salle étage suivant

        // Tirer une position dans la SD
        entierAleatoire = GENERATEUR.nextInt(listePositions.size());
        positionTiree = listePositions.get(entierAleatoire);
        listePositions.remove(entierAleatoire);

        salleSource = getSalleSource(positionTiree);
        Salle salleEtageSuivant = new SalleEtageSuivant(positionTiree, salleSource);

        this.sallesPresentes.add(salleEtageSuivant);

        // Relier la salle source à la salle étage suivant
        salleSource.ajouterPorte(positionTiree.getCaseLibre(), salleEtageSuivant);
    }
}
