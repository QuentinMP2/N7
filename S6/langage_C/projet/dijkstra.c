#include "dijkstra.h"
#include <stdlib.h>

/**
 * construire_chemin_vers - Construit le chemin depuis le noeud de départ donné vers le
 * noeud donné. On passe un chemin en entrée-sortie de la fonction, qui est mis à jour
 * par celle-ci.
 *
 * Le noeud de départ est caractérisé par un prédécesseur qui vaut `NO_ID`.
 *
 * Ce sous-programme fonctionne récursivement :
 *  1. Si le noeud a pour précédent `NO_ID`, on a fini (c'est le noeud de départ, le chemin de
 *     départ à départ se compose du simple noeud départ)
 *  2. Sinon, on construit le chemin du départ au noeud précédent (appel récursif)
 *  3. Dans tous les cas, on ajoute le noeud au chemin, avec les caractéristiques associées dans visites
 *
 * @param chemin [in/out] chemin dans lequel enregistrer les étapes depuis le départ vers noeud
 * @param visites [in] liste des noeuds visités créée par l'algorithme de Dijkstra
 * @param noeud noeud vers lequel on veut construire le chemin depuis le départ
 */
void construire_chemin_vers (
    liste_noeud_t** chemin, 
    liste_noeud_t* visites, 
    noeud_id_t noeud) {

    noeud_id_t noeudPrecedent = precedent_noeud_liste(visites, noeud);

    if (noeudPrecedent != NO_ID) {
        construire_chemin_vers(chemin, visites, noeudPrecedent);
    } 

    inserer_noeud_liste(
        *chemin, 
        noeud, 
        noeudPrecedent,
        distance_noeud_liste(visites, noeud)
    );
} 


float dijkstra (
    const struct graphe_t* graphe, 
    noeud_id_t source, noeud_id_t destination, 
    liste_noeud_t** chemin) {
    
    // Création des collections aVisiter et visites
    liste_noeud_t* aVisiter = creer_liste();
    liste_noeud_t* visites = creer_liste();

    inserer_noeud_liste(aVisiter, source, NO_ID, 0.0);
    
    while (!est_vide_liste(aVisiter)) {
        noeud_id_t noeudCourant = min_noeud_liste(aVisiter);
        inserer_noeud_liste(
            visites, 
            noeudCourant, 
            precedent_noeud_liste(aVisiter, noeudCourant),
            distance_noeud_liste(aVisiter, noeudCourant)
        );

        supprimer_noeud_liste(aVisiter, noeudCourant);
        
        // Récupérer les voisins du noeud courant
        size_t nbVoisins = nombre_voisins(graphe, noeudCourant);
        noeud_id_t* voisins = (noeud_id_t*)malloc(nbVoisins*sizeof(noeud_id_t));
        noeuds_voisins(graphe, noeudCourant, voisins);

        for (int i = 0; i < (int)nbVoisins; i++) {
            noeud_id_t noeudVoisin = voisins[i];

            if (!contient_noeud_liste(visites, noeudVoisin)) {
                float distanceTotale = distance_noeud_liste(visites, noeudCourant) 
                    + noeud_distance(graphe, noeudCourant, noeudVoisin);  
                float distanceDepartVoisin = distance_noeud_liste(aVisiter, noeudVoisin);

                if (distanceTotale < distanceDepartVoisin) {
                    changer_noeud_liste(aVisiter, noeudVoisin, noeudCourant, distanceTotale);
                }
            } 
        }  

        // Libérer la mémoire utilisée par le tableau des voisins
        free(voisins);
    }

    // Construire le chemin du noeud source vers le noeud destination
    if (chemin != NULL) {
        *chemin = creer_liste();
        construire_chemin_vers(chemin, visites, destination);
    } 

    // Calculer la distance minimale entre le noeud source et le noeud destination
    float distanceMinimaleSD = distance_noeud_liste(visites, destination);

    // Libérer la mémoire utilisées par les listes de noeuds
    detruire_liste(&aVisiter);
    detruire_liste(&visites); 
    
    return distanceMinimaleSD;
}
