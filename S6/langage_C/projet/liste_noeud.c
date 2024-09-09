#define _GNU_SOURCE
#include "liste_noeud.h"
#include <stdlib.h>
#include <math.h>

struct cellule_t {
    /** Noeud courant de la liste. */
    noeud_id_t noeud;

    /** Noeud précédent (contient NO_ID si
    le noeud ne possède pas de précédent). */
    noeud_id_t noeud_prec;

    /** Distance au noeud précédent.
    Contient la valeur spéciale INFINITY 
    si le noeud n'a pas de précédent. */
    double distance;

    /** Cellule suivante. */
    struct cellule_t* cellule_suivante;
};

typedef struct cellule_t cellule_t;

struct liste_noeud_t {
    /** Pointeur vers la première cellule de la liste.*/
    cellule_t* premiere_cellule;

    /** Pointeur vers la dernière cellule de la liste.*/
    cellule_t* derniere_cellule;
};

liste_noeud_t* creer_liste() {
    liste_noeud_t* liste_ptr = (liste_noeud_t*)malloc(sizeof(liste_noeud_t));
    liste_ptr -> premiere_cellule = NULL;
    liste_ptr -> derniere_cellule = NULL;
    return liste_ptr;
}

// Implantation récursive de la procédure detruire_liste
void detruire_liste(liste_noeud_t** liste_ptr) {

    if (liste_ptr != NULL) {
        cellule_t* curseur = (*liste_ptr) -> premiere_cellule;
        cellule_t* a_detruire;

        while (curseur != NULL) {
            a_detruire = curseur;
            curseur = curseur -> cellule_suivante;
            free(a_detruire);
        }

        free(*liste_ptr);
        *liste_ptr = NULL;
    }
}

bool est_vide_liste(const liste_noeud_t* liste) {
    if (liste == NULL) {
        printf("inserer_noeud_liste Erreur : le pointeur sur la liste est NULL\n");
    }

    return liste -> premiere_cellule == NULL && liste -> derniere_cellule == NULL;
}

bool contient_noeud_liste(const liste_noeud_t* liste, noeud_id_t noeud) {
    if (est_vide_liste(liste)) {
        return false;
    } 

    cellule_t* curseur = liste -> premiere_cellule;
    bool resultat = false;
    
    bool fini = false;
    while (curseur != NULL && !fini) {
        if (curseur -> noeud == noeud) {
            resultat = true;
            fini = true;
        } else {
            curseur = curseur -> cellule_suivante;
        } 
    }

    return resultat; 
} 

bool contient_arrete_liste(
    const liste_noeud_t* liste, 
    noeud_id_t source, 
    noeud_id_t destination) {

    if (est_vide_liste(liste)) {
        return false;
    }

    bool resultat = false;
    cellule_t* curseur = liste -> premiere_cellule;

    bool fini = false;
    while (curseur != NULL && !fini){
        if (curseur -> noeud == destination) {
            resultat = (curseur -> noeud_prec == source);
            fini = true;
        }

        curseur = curseur -> cellule_suivante;  
    }

    return resultat;
} 

float distance_noeud_liste(const liste_noeud_t* liste, noeud_id_t noeud) {
    if (est_vide_liste(liste)) {
        return INFINITY;
    }

    double distance = INFINITY;
    cellule_t* curseur = liste -> premiere_cellule;

    bool fini = false;
    while (curseur != NULL && !fini) {
        if (curseur -> noeud == noeud) {
            distance = curseur -> distance;
            fini = true;
        } 

        curseur = curseur -> cellule_suivante;
    } 

    return distance;
}

noeud_id_t precedent_noeud_liste(const liste_noeud_t* liste, noeud_id_t noeud) {
    if (est_vide_liste(liste)) {
        return NO_ID;
    }

    noeud_id_t precedent = NO_ID;
    cellule_t* curseur = liste -> premiere_cellule;

    bool fini = false;
    while (curseur != NULL && !fini) {
        if (curseur -> noeud == noeud) {
            precedent = curseur -> noeud_prec;
            fini = true;
        }

        curseur = curseur -> cellule_suivante;
    }

    return precedent;
} 

noeud_id_t min_noeud_liste(const liste_noeud_t* liste) {
    if (est_vide_liste(liste)) {
        return NO_ID;
    }

    noeud_id_t min_noeud = NO_ID;
    cellule_t* curseur = liste -> premiere_cellule;
    double dist_min = INFINITY;

    while (curseur != NULL) {
        if (curseur -> distance < dist_min) {
            dist_min = curseur -> distance;
            min_noeud = curseur -> noeud;
        } 
        curseur = curseur -> cellule_suivante;
    }

    return min_noeud;
} 

void inserer_noeud_liste(
    liste_noeud_t* liste, 
    noeud_id_t noeud, 
    noeud_id_t precedent, 
    double distance) {
    
    if (liste == NULL){
        printf("inserer_noeud_liste Erreur : le pointeur sur la liste est NULL\n");
    } else {
        cellule_t* nouvelle_cellule = (cellule_t*)malloc(sizeof(cellule_t));
        nouvelle_cellule -> noeud = noeud;
        nouvelle_cellule -> noeud_prec = precedent;
        nouvelle_cellule -> distance = distance;
        nouvelle_cellule -> cellule_suivante = NULL;
        
        if (est_vide_liste(liste)) {
            liste -> premiere_cellule = nouvelle_cellule;
            liste -> derniere_cellule = nouvelle_cellule;
        } else {
            liste -> derniere_cellule -> cellule_suivante = nouvelle_cellule;
            liste -> derniere_cellule = nouvelle_cellule;
        }
    } 
} 

void changer_noeud_liste(
    liste_noeud_t* liste, 
    noeud_id_t noeud, 
    noeud_id_t precedent, 
    double distance) {
    
    if (est_vide_liste(liste)) {
        inserer_noeud_liste(liste, noeud, precedent, distance);
    } else {
        cellule_t* curseur = liste -> premiere_cellule;

        bool fini = false;
        while (curseur != NULL && !fini) {
            if (curseur -> noeud == noeud) {
                curseur -> noeud_prec = precedent;
                curseur -> distance = distance;
            }

            curseur = curseur -> cellule_suivante;
        }

        // On a parcouru toute la liste sans trouver le noeud
        if (curseur == NULL) {
            inserer_noeud_liste(liste, noeud, precedent, distance);
        }
    }
}

void supprimer_noeud_liste(liste_noeud_t* liste, noeud_id_t noeud) {
    cellule_t* curseur = liste -> premiere_cellule;
    cellule_t* curseur_prec = liste -> premiere_cellule;
    cellule_t* a_detruire;

    if (!est_vide_liste(liste)) {
        if (liste -> premiere_cellule == liste -> derniere_cellule 
            && liste -> premiere_cellule -> noeud == noeud) {
            // Traiter le cas où la cellule à supprimer est la première et la dernière
            // C'est-à-dire qu'il ne reste plus qu'un seul noeud
            a_detruire = liste -> premiere_cellule;
            liste -> premiere_cellule = NULL;
            liste -> derniere_cellule = NULL;
            free(a_detruire);
        } else if (liste -> premiere_cellule -> noeud == noeud) {
            // Traiter le cas où la cellule à supprimer est la première
            a_detruire = liste -> premiere_cellule;
            liste -> premiere_cellule = a_detruire -> cellule_suivante;
            free(a_detruire);

        } else if (liste -> derniere_cellule -> noeud == noeud) {
            // Traiter le cas où la cellule à supprimer est la dernière
            while (curseur != liste -> derniere_cellule) {
                curseur_prec = curseur;
                curseur = curseur -> cellule_suivante;
            }
            a_detruire = liste -> derniere_cellule;
            curseur_prec -> cellule_suivante = NULL;
            liste -> derniere_cellule = curseur_prec;
            free(a_detruire);
            
        } else {
            // Cas général
            bool fini = false;
            while (curseur != NULL && !fini) {
                if (curseur -> noeud == noeud) {
                    a_detruire = curseur;
                    curseur = curseur -> cellule_suivante;
                    curseur_prec -> cellule_suivante = curseur;
                    free(a_detruire);
                    fini = true;
                }

                curseur_prec = curseur;
                curseur = curseur -> cellule_suivante;
            }
        } 
    }
} 