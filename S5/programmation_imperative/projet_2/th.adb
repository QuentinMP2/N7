with Ada.Text_IO;            use Ada.Text_IO;
with Ada.Integer_Text_IO;    use Ada.Integer_Text_IO;
with SDA_Exceptions;         use SDA_Exceptions;
with Ada.Unchecked_Deallocation;
with LCA;

package body TH is


    procedure Initialiser (Sda : out T_TH) is
    begin
        Sda.Nb_Elem := 0;
    end Initialiser;


    procedure Detruire (Sda : in out T_TH) is
    begin
        for i in 1..CAPACITE loop
            LCA_TH.Detruire(Sda.Tab(i));
        end loop;

        Sda.Nb_Elem := 0;
    end Detruire;


    procedure Afficher_Debug (Sda : in T_TH) is
        procedure Afficher_LCA is
            new LCA_TH.Afficher_Debug(Afficher_Cle => Afficher_Cle, Afficher_Donnee => Afficher_Donnee);
    begin
        for i in 1..CAPACITE loop
            Put(i,1);
            Put(' ');
            Afficher_LCA(Sda.Tab(i));
            New_Line;
        end loop;
    end Afficher_Debug;


    function Est_Vide (Sda : T_TH) return Boolean is
    begin
        return Sda.Nb_Elem = 0;
    end;


    function Taille (Sda : in T_TH) return Integer is
    begin
        return Sda.Nb_Elem;
    end Taille;


    procedure Enregistrer (Sda : in out T_TH ; Cle : in T_Cle ; Valeur : in T_Valeur) is
    begin
        if not(LCA_TH.Cle_Presente(Sda.Tab(Fonction_Hachage(Cle) mod CAPACITE + 1), Cle)) then
            Sda.Nb_Elem := Sda.Nb_Elem + 1;
        end if;

        LCA_TH.Enregistrer(Sda.Tab(Fonction_Hachage(Cle) mod CAPACITE + 1), Cle, Valeur);
    end Enregistrer;


    function Cle_Presente (Sda : in T_TH ; Cle : in T_Cle) return Boolean is
    begin
        return LCA_TH.Cle_Presente(Sda.Tab(Fonction_Hachage(Cle) mod CAPACITE + 1), Cle);
    end;


    function La_Valeur (Sda : in T_TH ; Cle : in T_Cle) return T_Valeur is
    begin
        return LCA_TH.La_Valeur(Sda.Tab(Fonction_Hachage(Cle) mod CAPACITE + 1), Cle);
    end La_Valeur;


    procedure Supprimer (Sda : in out T_TH ; Cle : in T_Cle) is
    begin
        LCA_TH.Supprimer(Sda.Tab(Fonction_Hachage(Cle) mod CAPACITE + 1), Cle);

        Sda.Nb_Elem := Sda.Nb_Elem - 1;
    end Supprimer;


    procedure Pour_Chaque (Sda : in T_TH) is
        procedure Pour_Chaque_LCA is
            new LCA_TH.Pour_Chaque(Traiter => Traiter);
    begin
        for i in 1..CAPACITE loop
            Pour_Chaque_LCA(Sda.Tab(i));
        end loop;
    end Pour_Chaque;


end TH;