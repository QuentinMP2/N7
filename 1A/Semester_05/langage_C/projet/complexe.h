#ifndef COMPLEX_H
#define COMPLEX_H

// Type utilisateur complexe_t
struct complexe {
    float partie_reelle;
    float partie_imaginaire;
};
typedef struct complexe complexe_t;

// Fonctions reelle et imaginaire
/**
 * Renvoie la partie réelle d'un complexe
 *
 * Paramètres :
 *      complexe_t c [in] : le nombre complexe dont on veut la partie réelle
 *
 * Retour :
 *      partie réelle de c
 * 
 * Pré-conditions :
 *      aucune
 *
 * Post-conditions :
 *      le nombre renvoyé est bien la partie réelle de c
 */
float reelle (complexe_t c);

/**
 * Renvoie la partie imaginaire d'un complexe
 *
 * Paramètres :
 *      complexe_t c [in] : le nombre complexe dont on veut la partie imaginaire
 *
 * Retour :
 *      partie imaginaire de c
 * 
 * Pré-conditions :
 *      aucune
 *
 * Post-conditions :
 *      le nombre renvoyé est bien la partie imaginaire de c
 */
float imaginaire (complexe_t c);

// Procédures set_reelle, set_imaginaire et init
/**
 * Modifie la partie réelle d'un complexe
 *
 * Paramètres :
 *      complexe_t p_c [in out] : le pointeur vers le nombre complexe dont on veut 
 *                       modifier la partie réelle
 *      reel [in] : nombre réel qui va remplacer la partie réelle de *c
 *
 * Retour :
 *      aucun
 * 
 * Pré-conditions :
 *      aucune
 *
 * Post-conditions :
 *      p_c -> partie_reelle == reel;
 */
void set_reelle (complexe_t* p_c, float reel);

/**
 * Modifie la partie imaginaire d'un complexe
 *
 * Paramètres :
 *      complexe_t p_c : le pointeur vers le nombre complexe dont on veut 
 *                       modifier la partie imaginaire
 *      imaginaire : nombre réel qui va remplacer la partie imaginaire de *c
 *
 * Retour :
 *      aucun
 * 
 * Pré-conditions :
 *      aucune
 *
 * Post-conditions :
 *      p_c -> partie_imagainaire == imaginaire;
 */
void set_imaginaire (complexe_t* p_c, float imaginaire);

/**
 * Initialise les parties réelle et imaginaire d'un nombre complexe
 *
 * Paramètres :
 *      complexe_t p_c : le pointeur vers le nombre complexe dont on veut 
 *                       modifier la partie imaginaire
 *      reel : nombre réel qui va initialiser la partie réelle de *c
 *      imaginaire : nombre réel qui va initialiser la partie imaginaire de *c
 *
 * Retour :
 *      aucun
 * 
 * Pré-conditions :
 *      aucune
 *
 * Post-conditions :
 *      p_c -> partie_reelle == reel
 *      p_c -> partie_imaginaire == imaginaire
 */
void init (complexe_t* p_c, float reel, float imaginaire);

// Procédure copie
/**
 * copie
 * Copie les composantes du complexe donné en second argument dans celles du premier
 * argument
 *
 * Paramètres :
 *   resultat       [out] Complexe dans lequel copier les composantes
 *   autre          [in]  Complexe à copier
 *
 * Pré-conditions : resultat non null
 * Post-conditions : resultat et autre ont les mêmes composantes
 */
void copie (complexe_t* resultat, complexe_t autre);

// Algèbre des nombres complexes
/**
 * conjugue
 * Calcule le conjugué du nombre complexe op et le stocke dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   op             [in]  Complexe dont on veut le conjugué
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : reelle(*resultat) = reelle(op), complexe(*resultat) = - complexe(op)
 */
void conjugue (complexe_t* resultat, complexe_t op);

/**
 * ajouter
 * Réalise l'addition des deux nombres complexes gauche et droite et stocke le résultat
 * dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   gauche         [in]  Opérande gauche
 *   droite         [in]  Opérande droite
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = gauche + droite
 */
void ajouter (complexe_t* resultat, complexe_t gauche, complexe_t droite);

/**
 * soustraire
 * Réalise la soustraction des deux nombres complexes gauche et droite et stocke le résultat
 * dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   gauche         [in]  Opérande gauche
 *   droite         [in]  Opérande droite
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = gauche - droite
 */
void soustraire (complexe_t* resultat, complexe_t gauche, complexe_t droite);

/**
 * multiplier
 * Réalise le produit des deux nombres complexes gauche et droite et stocke le résultat
 * dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   gauche         [in]  Opérande gauche
 *   droite         [in]  Opérande droite
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = gauche * droite
 */
void multiplier (complexe_t* resultat, complexe_t gauche, complexe_t droite);

/**
 * echelle
 * Calcule la mise à l'échelle d'un nombre complexe avec le nombre réel donné (multiplication
 * de op par le facteur réel facteur).
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   op             [in]  Complexe à mettre à l'échelle
 *   facteur        [in]  Nombre réel à multiplier
 *
 * Pré-conditions : resultat non-null
 * Post-conditions : *resultat = op * facteur
 */
void echelle (complexe_t* resultat, complexe_t op, double facteur);

/**
 * puissance
 * Calcule la puissance entière du complexe donné et stocke le résultat dans resultat.
 *
 * Paramètres :
 *   resultat       [out] Résultat de l'opération
 *   op             [in]  Complexe dont on veut la puissance
 *   exposant       [in]  Exposant de la puissance
 *
 * Pré-conditions : resultat non-null, exposant >= 0
 * Post-conditions : *resultat = op * op * ... * op
 *                                 { n fois }
 */
void puissance (complexe_t* resultat, complexe_t op, int exposant);

// Module et argument
/**
 * module carre
 * Calcule le module au carré du complexe donné et stocke le résultat dans resultat.
 *
 * Paramètres :
 *   op             [in]  Complexe dont on veut le module au carré
 *   
 *
 * Pré-conditions : aucune
 * Post-conditions : le résultat retourné est le module au carré de op
 */
float module_carre (complexe_t op);

/**
 * module
 *
 * CONTRAT À COMPLETER
 */
/** FONCTION À DÉCLARER **/

/**
 * argument
 *
 * CONTRAT À COMPLETER
 */
/** FONCTION À DÉCLARER **/


#endif // COMPLEXE_H



