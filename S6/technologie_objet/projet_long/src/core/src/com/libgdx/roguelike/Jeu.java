package com.libgdx.roguelike;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.libgdx.roguelike.personnage.Joueur;
import com.libgdx.roguelike.personnage.Personnage;

public class Jeu extends Game {
    public Screen JeuEcran;

    @Override
    public void create() {
        // Initialiser le premier étage du donjon
        Etage etage = new Etage(1);
        // générer les salles du premier étage du donjon
        etage.generer();
        // Initialiser le joueur
        Joueur joueur = new Joueur();

        // Lancer l'affichage de la fenêtre de jeu
        JeuEcran = new JeuEcran(etage, joueur);
        //JeuEcran = new TestGenEcran();
        setScreen(JeuEcran);
    }


}