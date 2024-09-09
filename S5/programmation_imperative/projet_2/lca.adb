with Ada.Text_IO;            use Ada.Text_IO;
with SDA_Exceptions;         use SDA_Exceptions;
with Ada.Unchecked_Deallocation;

package body LCA is

    procedure Free is
        new Ada.Unchecked_Deallocation (Object => T_Cellule, Name => T_LCA);


    procedure Initialiser(Sda: out T_LCA) is
    begin
        Sda := NULL;
    end Initialiser;


    procedure Detruire (Sda : in out T_LCA) is
        Copie_Courante : T_LCA := Sda;      -- Copie pour parcourir la liste
        Copie_Precedente : T_LCA := Sda;    -- Copie qui permettra de supprimer
        -- les cellules
    begin
        while Copie_Courante /= NULL loop
            Copie_Precedente := Copie_Courante;
            Copie_Courante := Copie_Courante.all.Suivant;
            Free(Copie_Precedente);
        end loop;
    end Detruire;


    procedure Afficher_Debug (Sda : in T_LCA) is
    begin
        if Sda = NULL then
            Put("--E");
        else
            Put("-->[");
            Afficher_Cle(Sda.all.Cle);
            Put(" : ");
            Afficher_Donnee(Sda.all.Valeur);
            Put(']');

            Afficher_Debug(Sda.all.Suivant);
        end if;
    end Afficher_Debug;


    function Est_Vide (Sda : T_LCA) return Boolean is
    begin
        return Sda = NULL;
    end;


    function Taille (Sda : in T_LCA) return Integer is
        Resultat : Integer := 0;
        Copie : T_LCA := Sda;
    begin
        while Copie /= NULL loop
            Resultat := Resultat + 1;
            Copie := Copie.all.Suivant;
        end loop;

        return Resultat;
    end Taille;


    procedure Enregistrer (Sda : in out T_LCA ; Cle : in T_Cle ; Valeur : in T_Valeur) is
        New_Cell : T_LCA;
    begin
        if Sda = NULL then
            -- On crée la nouvelle cellule
            New_Cell := new T_Cellule;
            New_Cell.Cle := Cle;
            New_Cell.Valeur := Valeur;
            New_Cell.Suivant := NULL;

            Sda := New_Cell;
        elsif Sda.all.Cle = Cle then
            Sda.all.Valeur := Valeur;
        else
            Enregistrer(Sda.all.Suivant, Cle, Valeur);
        end if;
    end Enregistrer;


    function Cle_Presente (Sda : in T_LCA ; Cle : in T_Cle) return Boolean is
        Copie : T_LCA := Sda;
        Continuer : Boolean := True;	-- Tant que l'on a pas trouvé la clé, 
                                        -- on continue
    begin
        while Continuer and Copie /= NULL loop 
            if Copie.all.Cle = Cle then
                Continuer := False;
            else
                Copie := Copie.all.Suivant;
            end if;
        end loop;

        return not(Continuer);
    end;


    function La_Valeur (Sda : in T_LCA ; Cle : in T_Cle) return T_Valeur is
    begin
        if Sda = NULL then
            raise Cle_Absente_Exception;
        elsif Sda.all.Cle = Cle then
            return Sda.all.Valeur;
        else
            return La_Valeur(Sda.all.Suivant, Cle);
        end if;
    end La_Valeur;


    procedure Supprimer (Sda : in out T_LCA ; Cle : in T_Cle) is
        Copie : T_LCA;
    begin
        if Sda = NULL then
            raise Cle_Absente_Exception;
        elsif Sda.all.Cle = Cle then
            Copie := Sda;
            Sda := Sda.all.Suivant;
            Free(Copie);
        else
            Supprimer(Sda.all.Suivant, Cle);
        end if;
    end Supprimer;


    procedure Pour_Chaque (Sda : in T_LCA) is
        Copie : T_LCA := Sda;
    begin
        while Copie /= NULL loop
            begin
                Traiter(Copie.all.Cle, Copie.all.Valeur);
                Copie := Copie.all.Suivant;
            exception
                when others => Copie := Copie.all.Suivant;
            end;
        end loop;

    end Pour_Chaque;


end LCA;
