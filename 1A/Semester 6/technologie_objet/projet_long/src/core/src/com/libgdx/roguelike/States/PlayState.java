package com.libgdx.roguelike.States;

import com.libgdx.roguelike.CombatMonstre;
import com.libgdx.roguelike.Etage;
import com.libgdx.roguelike.Case;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.libgdx.roguelike.Portes.*;
import com.libgdx.roguelike.personnage.Joueur;
import com.libgdx.roguelike.salles.Salle;
import com.libgdx.roguelike.salles.SalleEtageSuivant;
import com.libgdx.roguelike.salles.SalleMonstre;
import com.libgdx.roguelike.States.GameState;
import com.libgdx.roguelike.managers.GameStateManager;
import com.badlogic.gdx.math.Rectangle;

public class PlayState extends GameState {

    /** Etage en cours. */
    private final Etage etage;

    /** Joueur. */
    private final Joueur joueur;

    /* Salle courante. */
    private final Salle salle;

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
        // Initialize variables and textures
        rond = new Texture("rond.png");
        coffre = new Texture("coffre.png");
        tonneau1 = new Texture("tonneau1.png");
        tonneau2 = new Texture("tonneau2.png");
        monstre = new Texture("monstre.png");
        texturePorte = new Texture("porte.jpg");
        textureTeleporteurEtageSuivant = new Texture("salleDepart.jpg");
        fondEcran = new Texture("fondEcran.png");

        stage = new Stage();
        // Create rectangles for collision detection
        player_rect = new Rectangle(joueur.getX(), joueur.getY(), joueur.getTexture().getWidth(), joueur.getTexture().getHeight());
        rond_rect = new Rectangle(rondX, rondY, rond.getWidth(), rond.getHeight());
        coffre_rect = new Rectangle(coffreX, coffreY, coffre.getWidth(), coffre.getHeight());
        tonneau1_rect = new Rectangle(tonneau1X, tonneau1Y, tonneau1.getWidth(), tonneau1.getHeight());
        tonneau2_rect = new Rectangle(tonneau2X, tonneau2Y, tonneau2.getWidth(), tonneau2.getHeight());

        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            monstre_rect = new Rectangle(salleMonstre.getMonstre().getX(), salleMonstre.getMonstre().getY(), salleMonstre.getMonstre().getTexture().getWidth(), salleMonstre.getMonstre().getTexture().getHeight());
        }

        Gdx.input.setInputProcessor(stage);
        xPrec = 0;
        yPrec = 0;
        batch = new SpriteBatch();
    }

    float monstreX = 1600;
    float monstreY = 750;
    float rondX = 500;
    float rondY = 550;

    float coffreX = 1500;
    float coffreY = 200;
    float tonneau1X = 100;
    float tonneau1Y = 100;
    float tonneau2X = 200;
    float tonneau2Y = 200;

    /** dimension de la fenetre */
    int width = 1920;
    int height = 1080;

    /** Le nombre de pixels qui va changer lors d'un déplacement.
     *  Permet de specifier la vitesse du joueur.
     */
    float deplacement = 400.0f;
    Stage stage;
    SpriteBatch batch;

    /** Les obstacles comme l'arbre par exemple. */
    Texture rond;
    Texture coffre;
    Texture tonneau1;
    Texture tonneau2;

    Texture monstre;
    Texture texturePorte;
    Texture textureTeleporteurEtageSuivant;
    Texture fondEcran;

    /** Ces deux commandes definissent un rectangle autour de l'image pour gérer les collisions. */
    Rectangle player_rect;
    Rectangle rond_rect;
    Rectangle coffre_rect;
    Rectangle tonneau1_rect;
    Rectangle tonneau2_rect;
    Rectangle monstre_rect;

    /** Les coordonnées du joueur qui seront stockées pour gérer la collision. */
    float xPrec;
    float yPrec;

    @Override
    public void handleInput() {
        // Handle keyboard input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yPrec = joueur.getY();
            joueur.setY(joueur.getY() + Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("haut.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xPrec = joueur.getX();
            joueur.setX(joueur.getX() - Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("gauche.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yPrec = joueur.getY();
            joueur.setY(joueur.getY() - Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("bas.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xPrec = joueur.getX();
            joueur.setX(joueur.getX() + Gdx.graphics.getDeltaTime() * deplacement);
            joueur.setTexture(new Texture("droite.png"));
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            joueur.setTexture(new Texture("still.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            navigateToMap();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P)){
            gsm.push(new MenuPause(gsm));
        };
    }

    @Override
    public void update(float delta) {
        handleInput();
        player_rect.setPosition(joueur.getX(), joueur.getY());

        // Collision detection and handling
        if (rond_rect.overlaps(player_rect) || coffre_rect.overlaps(player_rect) || tonneau1_rect.overlaps(player_rect) || tonneau2_rect.overlaps(player_rect)) {
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }

        if (joueur.getX() < 0 || joueur.getX() > width - joueur.getTexture().getWidth() || joueur.getY() < 0 || joueur.getY() > height - joueur.getTexture().getHeight()) {
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }

        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            if (Math.abs(joueur.getY() - salleMonstre.getMonstre().getY()) < Fight.DIST_MIN && Math.abs(joueur.getX() - salleMonstre.getMonstre().getX()) < Fight.DIST_MIN) {
                navigateToFight();
            }
        }
        for (Porte porte : this.salle.getPortes()) {
            Case coordonneesPorte = this.salle.getCoordonneesRelativesPorte(porte);
            if (Math.abs(joueur.getX() - coordonneesPorte.x) < Fight.DIST_MIN && Math.abs(joueur.getY() - coordonneesPorte.y) < Fight.DIST_MIN) {
                // Changer les coordonnées du joueur afin qu'elles soient cohérente
                // lors de l'arrivée dans la nouvelle salle
                if (porte instanceof PorteNord) {
                    //joueur.setY(joueur.getY() - (Salle.ORDONNEE_MAX + 10*Fight.DIST_MIN));
                    joueur.setY(1*Fight.DIST_MIN);
                } else if (porte instanceof PorteEst) {
                    //joueur.setX(joueur.getX() - (Salle.ABSCISSE_MAX + 7*Fight.DIST_MIN));
                    joueur.setX(1*Fight.DIST_MIN);
                } else if (porte instanceof PorteSud) {
                    //joueur.setY(joueur.getY() + (Salle.ORDONNEE_MAX - 5*Fight.DIST_MIN));
                    joueur.setY(Salle.ORDONNEE_MAX - 3*Fight.DIST_MIN);
                } else if (porte instanceof PorteOuest) {
                    joueur.setX(joueur.getX() + (Salle.ABSCISSE_MAX - 4*Fight.DIST_MIN));
                }
                navigateToSalleSuivante(porte);
            }
        }

        // Transporter à l'étage suivant si on entre dans le portail et que l'on est
        // dans la salle "SalleEtageSuivant"
        if (this.salle instanceof SalleEtageSuivant) {
            Case coordonneesPortail = salle.getCoordonneesPortailSalleSuivante();
            if (Math.abs(joueur.getX() - coordonneesPortail.x) < Fight.DIST_MIN
                    && Math.abs(joueur.getY() - coordonneesPortail.y) < Fight.DIST_MIN) {
                navigateToEtageSuivant();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1, 1, 1, 0);
        sb.begin();
        sb.draw(fondEcran, 0, 0);
        sb.draw(joueur.getTexture(), joueur.getX(), joueur.getY());
        sb.draw(rond, rondX, rondY);
        sb.draw(coffre, coffreX, coffreY);
        sb.draw(tonneau1, tonneau1X, tonneau1Y);
        sb.draw(tonneau2, tonneau2X, tonneau2Y);

        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            sb.draw(salleMonstre.getMonstre().getTexture(), salleMonstre.getMonstre().getX(), salleMonstre.getMonstre().getY());
        }

        for (Porte porte : this.salle.getPortes()) {
            Case coordonneesPorte = this.salle.getCoordonneesRelativesPorte(porte);
            sb.draw(texturePorte, coordonneesPorte.x, coordonneesPorte.y);
        }
        if (this.salle instanceof SalleEtageSuivant) {
            Case coordonneesPortail = salle.getCoordonneesPortailSalleSuivante();
            sb.draw(textureTeleporteurEtageSuivant, coordonneesPortail.x, coordonneesPortail.y);
        }

        // Dessiner la zone de téléportation si on est dans la salle étage suivant
        if (this.salle instanceof SalleEtageSuivant){
            Case coordonneesPortail = salle.getCoordonneesPortailSalleSuivante();
            sb.draw(textureTeleporteurEtageSuivant, coordonneesPortail.x, coordonneesPortail.y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        joueur.getTexture().dispose();
        rond.dispose();
        coffre.dispose();
        tonneau1.dispose();
        tonneau2.dispose();
        monstre.dispose();
        texturePorte.dispose();
        textureTeleporteurEtageSuivant.dispose();
        fondEcran.dispose();
    }

    private void navigateToFight() {
        SalleMonstre salleMonstre = (SalleMonstre) this.salle;
        gsm.push(new Fight(gsm));
    }

    private void navigateToSalleSuivante(Porte porte) {
        gsm.push(new PlayState(gsm, etage, joueur, porte.getSalleSuivante()));
    }

    private void navigateToEtageSuivant() {
        int noEtage = this.etage.getNumero();
        Etage etage = new Etage(noEtage + 1);
        // Generer l'étage suivant
        etage.generer();
        gsm.push(new PlayState(gsm, etage, joueur));
    }

    private void navigateToMap() {

        gsm.push(new TestGenEcranState(gsm, this.etage));
    }
}
