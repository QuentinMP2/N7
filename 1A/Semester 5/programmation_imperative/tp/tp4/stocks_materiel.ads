-- Auteur: 
-- Gérer un stock de matériel informatique.

generic
    T_Num_Serie is private;

package Stocks_Materiel is


    CAPACITE_MAX_MAX : constant Integer := 10;      -- nombre maximum de matériels dans un stock

    type T_Nature is (UNITE_CENTRALE, DISQUE, ECRAN, CLAVIER, IMPRIMANTE);
    type T_Etat is (EN_SERVICE,HORS_SERVICE);

    type T_Materiel is private;
    type T_Stock is limited private;


    -- Créer un stock vide.
    --
    -- paramètres
    --     Stock : le stock à créer
    --
    -- Assure
    --     Nb_Materiels (Stock) = 0
    --
    procedure Creer (Stock : out T_Stock) with
        Post => Nb_Materiels (Stock) = 0;


    -- Obtenir le nombre de matériels dans le stock Stock.
    --
    -- Paramètres
    --    Stock : le stock dont ont veut obtenir la taille
    --
    -- Nécessite
    --     Vrai
    --
    -- Assure
    --     Résultat >= 0 Et Résultat <= CAPACITE_MAX
    --
    function Nb_Materiels (Stock: in T_Stock) return Integer with
        Post => Nb_Materiels'Result >= 0 and Nb_Materiels'Result <= CAPACITE_MAX;

    -- Enregistrer un nouveau métériel dans le stock.  Il est en
    -- fonctionnement.  Le stock ne doit pas être plein.
    -- 
    -- Paramètres
    --    Stock : le stock à compléter
    --    Numero_Serie : le numéro de série du nouveau matériel
    --    Nature       : la nature du nouveau matériel
    --    Annee_Achat  : l'année d'achat du nouveau matériel
    -- 
    -- Nécessite
    --    Nb_Materiels (Stock) < CAPACITE_MAX
    -- 
    -- Assure
    --    Nouveau matériel ajouté
    --    Nb_Materiels (Stock) = Nb_Materiels (Stock)'Avant + 1
    procedure Enregistrer (
            Stock        : in out T_Stock;
            Numero_Serie : in     Integer;
            Nature       : in     T_Nature;
            Annee_Achat  : in     Integer
        ) with
            Pre => Nb_Materiels (Stock) < CAPACITE_MAX,
            Post => Nb_Materiels (Stock) = Nb_Materiels (Stock)'Old + 1;

private
       
    type T_Tab_Materiel is array (1..CAPACITE_MAX) of T_Materiel;

    T_Materiel is record
        Num_Serie : T_Num_Serie;
        Nature : T_Nature;
        Annee : Integer;
        Etat : T_Etat;
    end record;

    type Stock is record
        Tab_Materiel : T_Tab_Materiel;
        Capacite : Integer;             -- Capacite <= CAPACITE_MAX
    end record;

end Stocks_Materiel;
