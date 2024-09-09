package com.libgdx.roguelike;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.libgdx.roguelike.Portes.*;
import com.libgdx.roguelike.personnage.Joueur;
import com.libgdx.roguelike.personnage.Personnage;
import com.libgdx.roguelike.salles.Salle;
import com.libgdx.roguelike.salles.SalleEtageSuivant;
import com.libgdx.roguelike.salles.SalleMonstre;

public class JeuEcran extends InputAdapter implements Screen {

    /** Etage en cours. */
    private final Etage etage;

    /** Joueur. */
    private final Joueur joueur;

    /* Salle courante. */
    private final Salle salle;

    public JeuEcran(Etage etage, Joueur joueur) {
        this.etage = etage;
        this.joueur = joueur;
        this.salle = etage.getSalleDepart();
    }

    public JeuEcran(Etage etage, Joueur joueur, Salle salle) {
        this.etage = etage;
        this.joueur = joueur;
        this.salle = salle;
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

    // Creation d'un etage
    //Etage etageTest = new Etage(1);

    // Generer l'étage

    @Override
    public void show(){
        // Creation de l'arbre avec sa photo.
        rond = new Texture("rond.png");
        coffre = new Texture("coffre.png");
        tonneau1 = new Texture("tonneau1.png");
        tonneau2 = new Texture("tonneau2.png");
        monstre = new Texture("monstre.png");
        texturePorte = new Texture("porte.jpg");
        textureTeleporteurEtageSuivant = new Texture("salleDepart.jpg");
        fondEcran = new Texture("fondEcran.png");

        stage = new Stage();
        // Creation des rectangles autour des images.
        player_rect = new Rectangle(joueur.getX(), joueur.getY(), joueur.getTexture().getWidth(), joueur.getTexture().getHeight());
        rond_rect = new Rectangle(rondX, rondY, rond.getWidth(), rond.getHeight());
        coffre_rect = new Rectangle(coffreX, coffreY, coffre.getWidth(), coffre.getHeight());
        tonneau1_rect = new Rectangle(tonneau1X, tonneau1Y, tonneau1.getWidth(), tonneau1.getHeight());
        tonneau2_rect = new Rectangle(tonneau2X, tonneau2Y, tonneau2.getWidth(), tonneau2.getHeight());
        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
        
        monstre_rect = new Rectangle(salleMonstre.getMonstre().getX(), salleMonstre.getMonstre().getY(), salleMonstre.getMonstre().getTexture().getWidth(), salleMonstre.getMonstre().getTexture().getHeight());
            //monstre_rect = new Rectangle(monstreX, monstreY, monstre.getWidth(), monstre.getHeight());
        }


        Gdx.input.setInputProcessor(stage); // c'est une commande qui dit au framework LibGDX de
        // rediriger les événements d'entrée (clics de souris, touches du clavier ... ) vers le Stage,
        // qui les traitera ensuite et agira en conséquence sur les acteurs appropriés.
        xPrec = 0;
        yPrec = 0;
        batch = new SpriteBatch(); //Un SpriteBatch est un objet utilisé pour dessiner un grand nombre de sprites
        //en une seule opération de rendu. Plutôt que de dessiner chaque sprite individuellement, ce qui serait inefficace,
        // on peut utiliser un SpriteBatch pour regrouper les dessins et les envoyer à la carte graphique en une seule fois.
    }

    @Override
    public void render(float delta) {
        // Cette commande a pour but de créer un écran et de rendre son fond blanc.

        //Cette commande a pour but de créer un écran et de rendre son fond blanc.
        Gdx.gl.glClearColor(1,1,1,0);
        ScreenUtils.clear(1,1,1,0);
        batch.begin();
        stage.draw();
        // Commandes pour dessiner le joueur et l'arbre à l'écran.
        batch.draw(fondEcran, 0, 0);
        batch.draw(joueur.getTexture(), joueur.getX(), joueur.getY());
        batch.draw(rond, rondX, rondY);
        batch.draw(coffre, coffreX, coffreY);
        batch.draw(tonneau1, tonneau1X, tonneau1Y);
        batch.draw(tonneau2, tonneau2X, tonneau2Y);
        //batch.draw(monstre, monstreX, monstreY);

        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            batch.draw(salleMonstre.getMonstre().getTexture(), salleMonstre.getMonstre().getX(), salleMonstre.getMonstre().getY());
        }


        // Dessiner les portes
        for (com.libgdx.roguelike.Portes.Porte porte : this.salle.getPortes()) {
            Case coordonneesPorte = this.salle.getCoordonneesRelativesPorte(porte);
            batch.draw(texturePorte, coordonneesPorte.x, coordonneesPorte.y);
        }

        // Dessiner la zone de téléportation si on est dans la salle étage suivant
        if (this.salle instanceof SalleEtageSuivant){
            Case coordonneesPortail = salle.getCoordonneesPortailSalleSuivante();
            batch.draw(textureTeleporteurEtageSuivant, coordonneesPortail.x, coordonneesPortail.y);
        }

        // Transporter à l'étage suivant si on entre dans le portail et que l'on est
        // dans la salle "SalleEtageSuivant"
        if (this.salle instanceof SalleEtageSuivant) {
            Case coordonneesPortail = salle.getCoordonneesPortailSalleSuivante();
            if (Math.abs(joueur.getX() - coordonneesPortail.x) < CombatMonstre.DIST_MIN
                    && Math.abs(joueur.getY() - coordonneesPortail.y) < CombatMonstre.DIST_MIN){
                navigateToEtageSuivant();
            }
        }

        // S'il y a collision retour aux coordonnées avant collision.
        if (rond_rect.overlaps(player_rect)){
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }
        if (coffre_rect.overlaps(player_rect)){
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }
        if (tonneau1_rect.overlaps(player_rect)){
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }
        if (tonneau2_rect.overlaps(player_rect)){
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }
        if(joueur.getX() < 0 | joueur.getX() > width - joueur.getTexture().getWidth() | joueur.getY() < 0 | joueur.getY() > height - joueur.getTexture().getHeight()) {
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }

        if(joueur.getX() < 0 | joueur.getX() > width - joueur.getTexture().getWidth()| joueur.getY() < 0 | joueur.getY() > height - joueur.getTexture().getHeight()) {
            joueur.setX(xPrec);
            joueur.setY(yPrec);
        }


        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            if (Math.abs(joueur.getY() - salleMonstre.getMonstre().getY()) < CombatMonstre.DIST_MIN && Math.abs(joueur.getX() - salleMonstre.getMonstre().getX()) < CombatMonstre.DIST_MIN) {
            //if (Math.abs(joueur.getY() - monstreY) < CombatMonstre.DIST_MIN && Math.abs(joueur.getX() - monstreX) < CombatMonstre.DIST_MIN) {
            // Si la distance est inférieure au seuil, on va vers la nouvelle page
                navigateToCombatMonstre();
            }
        }

        for (Porte porte : this.salle.getPortes()){
            Case coordonneesPorte = this.salle.getCoordonneesRelativesPorte(porte);
            if (Math.abs(joueur.getX() - coordonneesPorte.x) < CombatMonstre.DIST_MIN
                    && Math.abs(joueur.getY() - coordonneesPorte.y) < CombatMonstre.DIST_MIN) {
                navigateToSalleSuivante(porte);
            }
        }

        // Ces 4 if définissent ce que doit faire le programme quand une touhce est cliquéee.**/
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            System.out.println("Z");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            //System.out.println(monstreX);
            //System.out.println(monstreY);
            yPrec = joueur.getY();
            joueur.setY(joueur.getY() + Gdx.graphics.getDeltaTime()*deplacement);
            joueur.setTexture(new Texture("haut.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            System.out.println("Q");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            //System.out.println(monstreX);
            //System.out.println(monstreY);
            xPrec = joueur.getX();
            joueur.setX(joueur.getX() - Gdx.graphics.getDeltaTime()*deplacement);
            joueur.setTexture(new Texture("gauche.png"));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            System.out.println("S");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            //System.out.println(monstreX);
            //System.out.println(monstreY);
            yPrec = joueur.getY();
            joueur.setY(joueur.getY() - Gdx.graphics.getDeltaTime()*deplacement);
            joueur.setTexture(new Texture("bas.png"));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            System.out.println("D");
            System.out.println(joueur.getX());
            System.out.println(joueur.getY());
            //System.out.println(monstreX);
            //System.out.println(monstreY);
            xPrec = joueur.getX();
            joueur.setX(joueur.getX() + Gdx.graphics.getDeltaTime()*deplacement);
            joueur.setTexture(new Texture("droite.png"));
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            joueur.setTexture(new Texture("still.png"));
        }

        // Ajouter comme fonctionnalité l'affichage d'une carte
        if (Gdx.input.isKeyPressed((Input.Keys.C))){
            navigateToMap();
        }

        player_rect = new Rectangle(joueur.getX(), joueur.getY(), joueur.getTexture().getWidth(), joueur.getTexture().getHeight());
        rond_rect = new Rectangle(rondX, rondY, rond.getWidth(), rond.getHeight());
        coffre_rect = new Rectangle(coffreX, coffreY, coffre.getWidth(), coffre.getHeight());
        tonneau1_rect = new Rectangle(tonneau1X, tonneau1Y, tonneau1.getWidth(), tonneau1.getHeight());
        tonneau2_rect = new Rectangle(tonneau2X, tonneau2Y, tonneau2.getWidth(), tonneau2.getHeight());
        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            
        monstre_rect = new Rectangle(salleMonstre.getMonstre().getX(), salleMonstre.getMonstre().getY(), salleMonstre.getMonstre().getTexture().getWidth(), salleMonstre.getMonstre().getTexture().getHeight());
        }

        batch.end();

    }

    private void navigateToCombatMonstre() {
        // Code pour naviguer vers la nouvelle page
        ((Game) Gdx.app.getApplicationListener()).setScreen(new CombatMonstre());
    }

    private void navigateToSalleSuivante(Porte porte_choisie){

        // Gérer l'affichage du joueur sur la salle suivante fonction de la porte qu'il emprunte
        if (porte_choisie instanceof PorteNord) {
            joueur.setX((float) width / 2);
            joueur.setY(3*CombatMonstre.DIST_MIN);
            ((Game) Gdx.app.getApplicationListener()).setScreen(new JeuEcran(this.etage, joueur, porte_choisie.getSalleSuivante()));
        } else if (porte_choisie instanceof PorteEst) {
            joueur.setX(3*CombatMonstre.DIST_MIN);
            joueur.setY((float) height / 2);
            ((Game) Gdx.app.getApplicationListener()).setScreen(new JeuEcran(this.etage, joueur, porte_choisie.getSalleSuivante()));
        }
        else if (porte_choisie instanceof PorteSud) {
            joueur.setX((float) width / 2);
            joueur.setY(height - 3 * CombatMonstre.DIST_MIN);
            ((Game) Gdx.app.getApplicationListener()).setScreen(new JeuEcran(this.etage, joueur, porte_choisie.getSalleSuivante()));
        }
        else if (porte_choisie instanceof PorteOuest) {
            joueur.setX(width - 3 * CombatMonstre.DIST_MIN);
            joueur.setY((float) height / 2);
            ((Game) Gdx.app.getApplicationListener()).setScreen(new JeuEcran(this.etage, joueur, porte_choisie.getSalleSuivante()));
        }
    }

    private void navigateToEtageSuivant(){
        int noEtage = this.etage.getNumero();
        Etage etage = new Etage(noEtage + 1);
        // Generer l'étage suivant
        etage.generer();
        ((Game) Gdx.app.getApplicationListener()).setScreen(new JeuEcran(etage, joueur));
    }

    private void navigateToMap(){
        ((Game) Gdx.app.getApplicationListener()).setScreen(new TestGenEcran(this, this.etage));
    }

    @Override
    public void resize(int width, int height) {
        Gdx.graphics.setWindowedMode(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
    @Override
    public void dispose() {
        batch.dispose();
        joueur.getTexture().dispose();
        rond.dispose();
        coffre.dispose();
        tonneau1.dispose();
        tonneau2.dispose();
        if (this.salle instanceof SalleMonstre) {
            SalleMonstre salleMonstre = (SalleMonstre) this.salle;
            salleMonstre.getMonstre().getTexture().dispose();
        }
        texturePorte.dispose();
        textureTeleporteurEtageSuivant.dispose();
    }


}
