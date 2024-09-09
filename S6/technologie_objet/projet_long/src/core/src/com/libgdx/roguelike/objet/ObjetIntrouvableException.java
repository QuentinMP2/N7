package com.libgdx.roguelike.objet;

/** Exception lorsqu'un objet est introuvable. */
public class ObjetIntrouvableException extends Exception {

    /** Construire une ObjetIntrouvableException.
     *
     * @param message message de l'exception
     */
    public ObjetIntrouvableException(String message) {
        super(message);
    }

    /** Construie une ObjetIntrouvableException. */
    public ObjetIntrouvableException() {
        this("Objet introuvable !");
    }
}
