with LCA;
-- Définition de structures de données associatives sous forme d'une table
-- de hachage (TH).
generic
	type T_Cle is private;
	type T_Valeur is private;
    CAPACITE : Integer;         -- Capacité maximale du tableau
	with function Fonction_Hachage (Cle : T_Cle) return Integer;
    
package TH is

	type T_TH is limited private;


	-- Initialiser une Sda.  La Sda est vide.
	procedure Initialiser (Sda : out T_TH) with
		Post => Est_Vide (Sda);


	-- Détruire une Sda.  Elle ne devra plus être utilisée.
	procedure Detruire (Sda : in out T_TH);


	-- Est-ce qu'une Sda est vide ?
	function Est_Vide (Sda : T_TH) return Boolean;


	-- Obtenir le nombre d'éléments d'une Sda. 
	function Taille (Sda : in T_TH) return Integer with
		Post => Taille'Result >= 0
			and (Taille'Result = 0) = Est_Vide (Sda);


	-- Enregistrer une valeur associée à une Clé dans une Sda.
	-- Si la clé est déjà présente dans la Sda, sa valeur est changée.
	procedure Enregistrer (Sda : in out T_TH ; Cle : in T_Cle ; Valeur : in T_Valeur) with
		Post => Cle_Presente (Sda, Cle) and (La_Valeur (Sda, Cle) = Valeur)   -- valeur insérée
				and (not (Cle_Presente (Sda, Cle)'Old) or Taille (Sda) = Taille (Sda)'Old)
				and (Cle_Presente (Sda, Cle)'Old or Taille (Sda) = Taille (Sda)'Old + 1);

	-- Supprimer la valeur associée à une Clé dans une Sda.
	-- Exception : Cle_Absente_Exception si Clé n'est pas utilisée dans la Sda
	procedure Supprimer (Sda : in out T_TH ; Cle : in T_Cle) with
		Post =>  Taille (Sda) = Taille (Sda)'Old - 1 -- un élément de moins
			and not Cle_Presente (Sda, Cle);         -- la clé a été supprimée


	-- Savoir si une Clé est présente dans une Sda.
	function Cle_Presente (Sda : in T_TH ; Cle : in T_Cle) return Boolean;


	-- Obtenir la valeur associée à une Cle dans la Sda.
	-- Exception : Cle_Absente_Exception si Clé n'est pas utilisée dans l'Sda
	function La_Valeur (Sda : in T_TH ; Cle : in T_Cle) return T_Valeur;


	-- Appliquer un traitement (Traiter) pour chaque couple d'une Sda.
	generic
		with procedure Traiter (Cle : in T_Cle; Valeur: in T_Valeur);
	procedure Pour_Chaque (Sda : in T_TH);


	--  -- Afficher la Sda en révélant sa structure interne.
	--  -- Voici un exemple d'affichage.
	--  1 -->["vingt-et-un" : 21]--E
    --  2 --E
    --  3 -->["un" : 1]--E
    --  4 --E
    --  5 -->["deux" : 2]-->["cinq" : 5]--E
    --  6 -->["trois" : 3]--E
    --  7 -->["quatre" : 4]--E
    --  8 --E
    --  9 --E
    --  10 --E
    --  11 -->["quatre-vingt-dix-neuf" : 99]--E
	generic
		with procedure Afficher_Cle (Cle : in T_Cle);
		with procedure Afficher_Donnee (Valeur : in T_Valeur);
	procedure Afficher_Debug (Sda : in T_TH);


private
    package LCA_TH is
        new LCA(T_Cle => T_Cle, T_Valeur => T_Valeur);
    use LCA_TH;

	type T_Tableau is array (1..CAPACITE) of T_LCA;

    type T_TH is record
        Tab : T_Tableau;        -- Tableau de T_LCA
        Nb_Elem : Integer;   	-- Taille du tableau
    end record;

end TH;