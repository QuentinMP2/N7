with Ada.Text_IO;                 use Ada.Text_IO;
with Ada.Integer_Text_IO;         use Ada.Integer_Text_IO;
with Ada.Float_Text_IO;           use Ada.Float_Text_IO;
with Ada.Unchecked_Deallocation;

package body Vecteurs_Creux is


    procedure Free is
        new Ada.Unchecked_Deallocation (T_Cellule, T_Vecteur_Creux);


    procedure Initialiser (V : out T_Vecteur_Creux) is
    begin
        V := NULL;
    end Initialiser;


    procedure Detruire (V: in out T_Vecteur_Creux) is
        Copie : T_Vecteur_Creux;
    begin
        while V /= NULL loop
            Copie := V.all.Suivant;
            Free(V);
            V := Copie;
        end loop;
    end Detruire;


    function Est_Nul (V : in T_Vecteur_Creux) return Boolean is 
    begin
        return V = NULL;
    end Est_Nul;


    function Composante_Recursif (V : in T_Vecteur_Creux ; Indice : in Integer) return Float is
    begin
        return 0.0;	-- TODO : à changer
    end Composante_Recursif;


    function Composante_Iteratif (V : in T_Vecteur_Creux ; Indice : in Integer) return Float is
    begin
        return 0.0;	-- TODO : à changer
    end Composante_Iteratif;


    procedure Modifier (V : in out T_Vecteur_Creux ; Indice : in Integer ; Valeur : in Float ) is
        Copie_Avant : T_Vecteur_Creux := V;
        Copie : T_Vecteur_Creux := V;
        Nouvelle_Cellule : T_Vecteur_Creux;
    begin

        -- On avance jusqu'à l'indice souhaité
        while Copie /= NULL and then Copie.all.Indice < Indice loop
            Copie_Avant := Copie;
            Copie := Copie.all.Suivant;
        end loop;

        if Copie /= NULL and then Copie.all.Indice = Indice then
            if Valeur = 0.0 then
                Copie_Avant.all.Suivant := Copie.all.Suivant;
                Free(Copie);
            else
                 
            end if;
        else
            Nouvelle_Cellule := new T_Cellule;
            Nouvelle_Cellule.all.Indice := Indice;
            Nouvelle_Cellule.all.Valeur := Valeur;
            Nouvelle_Cellule.all.Suivant := Copie;

            if Copie = NULL then
                Copie := Nouvelle_Cellule;
            else
                Copie_Avant.all.Suivant := Nouvelle_Cellule;
            end if;

        end if;
    end Modifier;


    function Sont_Egaux_Recursif (V1, V2 : in T_Vecteur_Creux) return Boolean is
    begin
        return False;	-- TODO : à changer
    end Sont_Egaux_Recursif;


    function Sont_Egaux_Iteratif (V1, V2 : in T_Vecteur_Creux) return Boolean is
    begin
        return False;	-- TODO : à changer
    end Sont_Egaux_Iteratif;


    procedure Additionner (V1 : in out T_Vecteur_Creux; V2 : in T_Vecteur_Creux) is
    begin
        Null;	-- TODO : à changer
    end Additionner;


    function Norme2 (V : in T_Vecteur_Creux) return Float is
    begin
        return 0.0;	-- TODO : à changer
    end Norme2;


    Function Produit_Scalaire (V1, V2: in T_Vecteur_Creux) return Float is
    begin
        return 0.0;	-- TODO : à changer
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
