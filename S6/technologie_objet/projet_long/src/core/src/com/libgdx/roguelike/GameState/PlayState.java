package com.libgdx.roguelike;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.roguelike.Portes.Porte;
import com.libgdx.roguelike.Portes.PorteEst;
import com.libgdx.roguelike.Portes.PorteNord;
import com.libgdx.roguelike.Portes.PorteOuest;
import com.libgdx.roguelike.Portes.PorteSud;
import com.libgdx.roguelike.States.GameState;
import com.libgdx.roguelike.managers.GameStateManager;
import com.libgdx.roguelike.personnage.Joueur;
import com.libgdx.roguelike.salles.Salle;
import com.libgdx.roguelike.salles.SalleEtageSuivant;
import com.libgdx.roguelike.salles.SalleMonstre;

public class PlayState extends GameState {

    /** Etage en cours. */
    private final Etage etage;

    /** Joueur. */
    private final Joueur joueur;

    /* Salle courante. */
    private final Salle salle;

    float arbreX = 300;
    float arbreY = 300;

    /** dimension de la fenetre */
    int width = 1920;
    int height = 1080;

    /** Le nombre de pixels qui va changer lors d'un déplacement.
     *  Permet de specifier la vitesse du joueur.
     */
    float deplacement = 400.0f;
    SpriteBatch batch;

    /** Les obstacles comme l'arbre par exemple. */
    Texture arbre;
    Texture texturePorte;
    Texture textureTeleporteurEtageSuivant;

    /** Ces deux commandes definissent un rectangle autour de l'image pour gérer les collisions. */
    Rectangle player_rect;
    Rectangle arbre_rect;
    Rectangle monstre_rect;

    /** Les coordonnées du joueur qui seront stockées pour gérer la collision. */
    float xPrec;
    float yPrec;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public PlayState(GameStateManager gsm, Etage etage, Joueur joueur) {
        super(gsm);
        this.etage = etage;
        this.joueur = joueur;
        this.salle = etage.getSalleDepart();
        init();
    }

    public PlayState(GameStateManager gsm, Etage etage, Joueur joueur, Salle salle) {
        super(gsm);
        this.etage = etage;
        this.joueur = joueur;
        this.salle = salle;
        init();
    }

    private void init() {
        // Creation de l'arbre avec sa photo.
        arbre = new Texture("arbre.png");
        texturePorte = new Texture("porte.jpg");
        textureTeleporteurEtageSuivant = new Texture("salleDepart.jpg");

        // Creation des rectangles autour des images.
        player_rect = new Rectangle(joueur.getX(), joueur.getY(), joueur.getTexture().getWidth(), joueur.getTexture().getHeight());
        arbre_rect = new Rectangle(arbreX, arbreY, arbre.getWidth(), arbre.getHeight());
        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            monstre_rect = new Rectangle(salleMonstre.getMonstre().getX(), salleMonstre.getMonstre().getY(), salleMonstre.getMonstre().getTexture().getWidth(), salleMonstre.getMonstre().getTexture().getHeight());
        }

        xPrec = 0;
        yPrec = 0;
        batch = new SpriteBatch(); // Un SpriteBatch est un objet utilisé pour dessiner un grand nombre de sprites
        // en une seule opération de rendu. Plutôt que de dessiner chaque sprite individuellement, ce qui serait inefficace,
        // on peut utiliser un SpriteBatch pour regrouper les dessins et les envoyer à la carte graphique en une seule fois.
    }

    @Override
    public void handleInput() {
        // Ces 4 if définissent ce que doit faire le programme quand une touhce est cliquéee.
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            System.out.println("Z");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            yPrec = joueur.getY();
            joueur.setY(joueur.getY() + Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("haut.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            System.out.println("Q");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            xPrec = joueur.getX();
            joueur.setX(joueur.getX() - Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("gauche.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            System.out.println("S");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            yPrec = joueur.getY();
            joueur.setY(joueur.getY() - Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("bas.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            System.out.println("D");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            xPrec = joueur.getX();
            joueur.setX(joueur.getX() + Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("droite.png"));
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            joueur.setTexture(new Texture("still.png"));
        }

        // Ajouter comme fonctionnalité l'affichage d'une carte
        //if (Gdx.input.isKeyPressed(Input.Keys.C)) {
          //  navigateToMap();
        //}
    }

    @Override
    public void update(float delta) {
        handleInput();
        checkCollisions();
    }

    @Override
    public void render(SpriteBatch sb) {
        // Cette commande a pour but de créer un écran et de rendre son fond blanc.
        ScreenUtils.clear(1, 1, 1, 0);
        sb.begin();
        // Commandes pour dessiner le joueur et l'arbre à l'écran.
        sb.draw(joueur.getTexture(), joueur.getX(), joueur.getY());
        sb.draw(arbre, arbreX, arbreY);

        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            sb.draw(salleMonstre.getMonstre().getTexture(), salleMonstre.getMonstre().getX(), salleMonstre.getMonstre().getY());
        }

        // Dessiner les portes
        for (Porte porte : this.salle.getPortes()) {
            Case coordonneesPorte = this.salle.getCoordonneesRelativesPorte(porte);
            sb.draw(texturePorte, coordonneesPorte.x, coordonneesPorte.y);
        }

        // Dessiner la zone de téléportation si on est dans la salle étage suivant
        if (this.salle instanceof SalleEtageSuivant) {
            Case coordonneesPortail = salle.getCoordonneesPortailSalleSuivante();
            sb.draw(textureTeleporteurEtageSuivant, coordonneesPortail.x, coordonneesPortail.y);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        joueur.getTexture().dispose();
        arbre.dispose();
        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            salleMonstre.getMonstre().getTexture().dispose();
        }
        texturePorte.dispose();
        textureTeleporteurEtageSuivant.dispose();
    }

    private void checkCollisions() {
        // S'il y a collision retour aux coordonnées avant collision.
        player_rect.setPosition(joueur.getX(), joueur.getY());
        arbre_rect.setPosition(arbreX, arbreY);
        if (arbre_rect.overlaps(player_rect) || isOutOfBounds()) {
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }

        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            if (Math.abs(joueur.getY() - salleMonstre.getMonstre().getY()) < Fight.DIST_MIN && Math.abs(joueur.getX() - salleMonstre.getMonstre().getX()) < Fight.DIST_MIN) {
                // Si la distance est inférieure au seuil, on va vers la nouvelle page
                navigateToFight();
            }
        }

        for (Porte porte : this.salle.getPortes()) {
            Case coordonneesPorte = this.salle.getCoordonneesRelativesPorte(porte);
            if (Math.abs(joueur.getX() - coordonneesPorte.x) < Fight.DIST_MIN && Math.abs(joueur.getY() - coordonneesPorte.y) < Fight.DIST_MIN) {
                navigateToSalleSuivante(porte);
            }
        }

        if (this.salle instanceof SalleEtageSuivant) {
            Case coordonneesPortail = salle.getCoordonneesPortailSalleSuivante();
            if (Math.abs(joueur.getX() - coordonneesPortail.x) < Fight.DIST_MIN && Math.abs(joueur.getY() - coordonneesPortail.y) < Fight.DIST_MIN) {
                navigateToEtageSuivant();
            }
        }
    }

    private boolean isOutOfBounds() {
        return joueur.getX() < 0 || joueur.getX() > width - joueur.getTexture().getWidth() || joueur.getY() < 0 || joueur.getY() > height - joueur.getTexture().getHeight();
    }

    private void navigateToFight() {
        gsm.set(new Fight(gsm));
    }

    private void navigateToSalleSuivante(Porte porte_choisie) {
        // Gérer l'affichage du joueur sur la salle suivante fonction de la porte qu'il emprunte
        if (porte_choisie instanceof PorteNord) {
            joueur.setX((float) width / 2);
            joueur.setY(3 * Fight.DIST_MIN);
        } else if (porte_choisie instanceof PorteEst) {
            joueur.setX(3 * Fight.DIST_MIN);
            joueur.setY((float) height / 2);
        } else if (porte_choisie instanceof PorteSud) {
            joueur.setX((float) width / 2);
            joueur.setY(height - 3 * Fight.DIST_MIN);
        } else if (porte_choisie instanceof PorteOuest) {
            joueur.setX(width - 3 * Fight.DIST_MIN);
            joueur.setY((float) height / 2);
        }
        gsm.set(new PlayState(gsm, this.etage, joueur, porte_choisie.getSalleSuivante()));
    }

    private void navigateToEtageSuivant() {
        int noEtage = this.etage.getNumero();
        Etage etage = new Etage(noEtage + 1);
        etage.generer();
        gsm.set(new PlayState(gsm, etage, joueur));
    }

   // private void navigateToMap() {
     //   gsm.set(new TestGenEcran(gsm, etage));
    //}
}
