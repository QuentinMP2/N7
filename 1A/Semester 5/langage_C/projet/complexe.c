#include "complexe.h"
#include <math.h>           // Pour certaines fonctions trigo notamment

// Implantations de reelle et imaginaire
/** FONCTION reelle À IMPLANTER **/
float reelle (complexe_t c) {
    return c.partie_reelle;
}
/** FONCTION imaginaire À IMPLANTER **/
float imaginaire (complexe_t c) {
    return c.partie_imaginaire;
}

// Implantations de set_reelle et set_imaginaire
/** PROCÉDURE set_reelle À IMPLANTER **/
void set_reelle (complexe_t* p_c, float reel) {
    p_c -> partie_reelle = reel;
}
/** PROCÉDURE set_imaginaire À IMPLANTER **/
void set_imaginaire (complexe_t* p_c, float imaginaire) {
    p_c -> partie_imaginaire = imaginaire;
}
/** PROCÉDURE init À IMPLANTER **/
void init (complexe_t* p_c, float reel, float imaginaire) {
    p_c -> partie_reelle = reel;
    p_c -> partie_imaginaire = imaginaire;
}

// Implantation de copie
/** PROCÉDURE copie À IMPLANTER */
void copie (complexe_t* resultat, complexe_t autre) {
    resultat -> partie_reelle = autre.partie_reelle;
    resultat -> partie_imaginaire = autre.partie_imaginaire;
}

// Implantations des fonctions algébriques sur les complexes
/** PROCÉDURE conjugue À IMPLANTER **/
void conjugue (complexe_t* resultat, complexe_t op) {
    resultat -> partie_reelle = op.partie_reelle;
    resultat -> partie_imaginaire = -op.partie_imaginaire;
}

/** PROCÉDURE ajouter À IMPLANTER **/
void ajouter (complexe_t* resultat, complexe_t gauche, complexe_t droite) {
    resultat -> partie_reelle = gauche.partie_reelle + droite.partie_reelle;
    resultat -> partie_imaginaire = gauche.partie_imaginaire + droite.partie_imaginaire;
}

/** PROCÉDURE soustraire À IMPLANTER **/
void soustraire (complexe_t* resultat, complexe_t gauche, complexe_t droite) {
    resultat -> partie_reelle = gauche.partie_reelle - droite.partie_reelle;
    resultat -> partie_imaginaire = gauche.partie_imaginaire - droite.partie_imaginaire;
}

/** PROCÉDURE multiplier À IMPLANTER **/
void multiplier (complexe_t* resultat, complexe_t gauche, complexe_t droite) {
    resultat -> partie_reelle = gauche.partie_reelle*droite.partie_reelle - gauche.partie_imaginaire*droite.partie_imaginaire;
    resultat -> partie_imaginaire = gauche.partie_reelle*droite.partie_imaginaire + gauche.partie_imaginaire*droite.partie_reelle;
}

/** PROCÉDURE echelle À IMPLANTER **/
void echelle (complexe_t* resultat, complexe_t op, double facteur) {
    resultat -> partie_reelle = op.partie_imaginaire*facteur;
    resultat -> partie_imaginaire = op.partie_imaginaire*facteur;
}

/** PROCÉDURE puissance À IMPLANTER **/
void puissance (complexe_t* resultat, complexe_t op, int exposant) {
    if (exposant == 0) {
        resultat -> partie_reelle = 1;
        resultat -> partie_imaginaire = 0;
    } 
    else {
        resultat -> partie_reelle = op.partie_reelle;
        resultat -> partie_imaginaire = op.partie_imaginaire;
        for (int i = 1; i < exposant; i++) {
            multiplier(resultat, op, *resultat);
        } 
    }  
}

// Implantations du module et de l'argument
/** FONCTION module_carre À IMPLANTER **/
float module_carre (complexe_t op) {
    return sqrt(op.partie_reelle*op.partie_reelle + op.partie_imaginaire*op.partie_imaginaire);
} 

/** FONCTION module À IMPLANTER **/
/** FONCTION argument À IMPLANTER **/


