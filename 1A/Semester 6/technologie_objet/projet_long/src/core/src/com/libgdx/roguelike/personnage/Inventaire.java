package com.libgdx.roguelike.personnage;

import com.libgdx.roguelike.objet.Objet;
import com.libgdx.roguelike.objet.ObjetIntrouvableException;

import java.util.HashMap;

/** Classe représentant l'inventaire d'un joueur. */
public class Inventaire {

    /** Inventaire du joueur. */
    private HashMap<Objet, Integer> inventaire;

    /** Construire un inventaire. */
    public Inventaire() {
        this.inventaire = new HashMap<>();
    }

    /** Ajouter un objet à l'inventaire.
     *
     * @param objet objet à ajouter
     */
    public void add(Objet objet) {
        if (inventaire.containsKey(objet)) {
            inventaire.replace(objet, inventaire.get(objet) + 1);
        } else {
            inventaire.put(objet, 1);
        }
    }

    /** Retirer un objet de l'inventaire.
     *
     * @param objet objet à retirer
     * @throws ObjetIntrouvableException quand un objet est introuvable
     */
    public void remove(Objet objet) throws ObjetIntrouvableException {
        if (!inventaire.containsKey(objet)) {
            throw new ObjetIntrouvableException();
        }

        inventaire.remove(objet, 1);
    }

}
