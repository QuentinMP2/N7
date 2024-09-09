with Ada.Text_IO;                 use Ada.Text_IO;
with Ada.Integer_Text_IO;         use Ada.Integer_Text_IO;
with Ada.Float_Text_IO;           use Ada.Float_Text_IO;
with Ada.Unchecked_Deallocation;

-- 1) Certaines opérations sont en temps linéaires car il suffit de parcourir le vecteur cellule après cellule,
-- elles sont rangées dans l'ordre croissant.

package body Vecteurs_Creux is


	procedure Free is
		new Ada.Unchecked_Deallocation (T_Cellule, T_Vecteur_Creux);


	procedure Initialiser (V : out T_Vecteur_Creux) is
	begin
	   V := null;
	end Initialiser;


	procedure Detruire (V: in out T_Vecteur_Creux) is
	begin
	   if V /= Null then
	      Detruire(V.all.Suivant);
	      Free(V);
	   else
	      Free(V);
	   end if;
	end Detruire;


	function Est_Nul (V : in T_Vecteur_Creux) return Boolean is
	begin
		return V = Null;
	end Est_Nul;


	function Composante_Recursif (V : in T_Vecteur_Creux ; Indice : in Integer) return Float is
	begin
	   if V = Null then
	      return 0.0;
	   elsif V.all.Indice = Indice then
	      return V.all.Valeur;
	   else return Composante_Recursif(V.all.Suivant, Indice);
	   end if;
	end Composante_Recursif;


	function Composante_Iteratif (V : in T_Vecteur_Creux ; Indice : in Integer) return Float is
	   V2 : T_Vecteur_Creux;
	begin
	   V2 := V;
	   while (V2 /= null and then V2.all.Indice /= Indice) loop
	      V2 := V2.all.Suivant;
	   end loop;
	   if V2 = null then
	      return 0.0;
	   else
	      return V2.all.Valeur;
	   end if;
	end Composante_Iteratif;


	procedure Modifier (V : in out T_Vecteur_Creux ;
				       Indice : in Integer ;
				       Valeur : in Float ) is
	   Cell : T_Vecteur_Creux;
	begin
	   Cell := new T_Cellule;
	   Cell.all.Valeur := Valeur;
	   Cell.all.Indice := Indice;
	   if V = Null then
	      Cell.all.Suivant := null;
	      V := Cell;
	   elsif V.all.Indice = Indice then
	      V.all.Valeur := Valeur;
	      Free(Cell);
	   elsif V.all.Indice > Indice then
	      Cell.all.Suivant := V;
	      V := Cell;
	   else
	      while (V.all.Suivant /= null and then V.all.Suivant.all.Indice < Indice) loop
		 V := V.all.Suivant;
	      end loop;
	      if V.all.Suivant /= null and then V.all.Suivant.all.Indice = Indice then
		 V.all.Suivant.all.Valeur := Valeur;
		 Free(Cell);
	      else
		 Cell.all.Suivant := V.all.Suivant;
		 V.all.Suivant := Cell;
	      end if;
	   end if;
	end Modifier;


	function Sont_Egaux_Recursif (V1, V2 : in T_Vecteur_Creux) return Boolean is
	begin
	   if V1 = null and V2 = null then
	      return True;
	   elsif V1 = null or V2 = null then
	      return False;
	   elsif V1.all.Indice = V2.all.Indice
	     and V1.all.Valeur = V2.all.Valeur then
	      return Sont_Egaux_Recursif(V1.all.Suivant, V2.all.Suivant);
	   else
	      return False;
	   end if;
	end Sont_Egaux_Recursif;


	function Sont_Egaux_Iteratif (V1, V2 : in T_Vecteur_Creux) return Boolean is
	   V12 : T_Vecteur_Creux;
	   V22 : T_Vecteur_Creux;
	begin
	   while (V12 /= null and V22 /= null) and then (V12.all.Indice = V22.all.Indice
						   and V12.all.Valeur = V22.all.Valeur) loop
	      V12 := V12.all.Suivant;
	      V22 := V22.all.Suivant;
	   end loop;
	   if V12 = null and V22 = null then
	      return True;
	   else
	      return False;
	   end if;
	end Sont_Egaux_Iteratif;

	
	procedure Ajouter (V : in out T_Vecteur_Creux ;
				       Indice : in Integer ;
				       Valeur : in Float ) is
	   Cell : T_Vecteur_Creux;
	begin
	   Cell := new T_Cellule;
	   Cell.all.Valeur := Valeur;
	   Cell.all.Indice := Indice;
	   if V = Null then
	      Cell.all.Suivant := null;
	      V := Cell;
	   elsif V.all.Indice = Indice then
	      V.all.Valeur := V.all.Valeur + Valeur;
	      Free(Cell);
	   elsif V.all.Indice > Indice then
	      Cell.all.Suivant := V;
	      V := Cell;
	   else
	      while (V.all.Suivant /= null and then V.all.Suivant.all.Indice < Indice) loop
		 V := V.all.Suivant;
	      end loop;
	      if V.all.Suivant /= null and then V.all.Suivant.all.Indice = Indice then
		 V.all.Suivant.all.Valeur := V.all.Suivant.all.Valeur + Valeur;
		 Free(Cell);
	      else
		 Cell.all.Suivant := V.all.Suivant;
		 V.all.Suivant := Cell;
	      end if;
	   end if;
	end Ajouter;
	
	
	procedure Additionner (V1 : in out T_Vecteur_Creux; V2 : in T_Vecteur_Creux) is
	begin
	   if V2 = null then null;
	   else
	      Ajouter(V1, V2.all.Indice, V2.all.Valeur);
	      Additionner(V1, V2.all.Suivant);
	   end if;
	end Additionner;


	function Norme2 (V : in T_Vecteur_Creux) return Float is
	begin
	   if V = null then
	      return 0.0;
	   else
	      return (V.all.Valeur * V.all.Valeur) + Norme2(V.all.Suivant);
	   end if;
	end Norme2;


	Function Produit_Scalaire (V1, V2: in T_Vecteur_Creux) return Float is
	begin
	   if V1 = null or V2 = null then
	      return 0.0;
	   elsif V1.all.Indice = V2.all.Indice then
	      return (V1.all.Valeur * V2.all.Valeur) + Produit_Scalaire(V1, V2);
	   elsif V1.all.Indice < V2.all.Indice then
	      return Produit_Scalaire(V1.all.Suivant, V2);
	   else
	      return Produit_Scalaire(V1, V2.all.Suivant);
	   end if;
	end Produit_Scalaire;


	procedure Afficher (V : T_Vecteur_Creux) is
	begin
		if V = Null then
			Put ("--E");
		else
			-- Afficher la composante V.all
			Put ("-->[ ");
			Put (V.all.Indice, 0);
			Put (" | ");
			Put (V.all.Valeur, Fore => 0, Aft => 1, Exp => 0);
			Put (" ]");

			-- Afficher les autres composantes
			Afficher (V.all.Suivant);
		end if;
	end Afficher;


	function Nombre_Composantes_Non_Nulles (V: in T_Vecteur_Creux) return Integer is
	begin
		if V = Null then
			return 0;
		else
			return 1 + Nombre_Composantes_Non_Nulles (V.all.Suivant);
		end if;
	end Nombre_Composantes_Non_Nulles;


end Vecteurs_Creux;
