with Text_Io;              use Text_Io;
with Ada.Integer_Text_Io;  use Ada.Integer_Text_Io;
with Alea;

-- Auteur : POINTEAU Quentin

procedure Jeu_Devin_Exo1 is
    
        BORNE_INF : Constant Integer := 1;
        BORNE_SUP : Constant Integer := 999;

        package Mon_Alea is
                new Alea (BORNE_INF, BORNE_SUP);
        use Mon_Alea;


        Nb_Ordi          : Integer;     -- Nombre aléatoire choisi par l'ordinateur
        Nb_Essais        : Integer;     -- Nombre d'essais de l'utilisateur pour trouver Nb_Ordi
        Proposition      : Integer;     -- Proposition de l'utilisateur

begin
        -- Choisir un nombre aléatoirement entre BORNE_INF et BORNE_SUP
        Get_Random_Number(Nb_Ordi);
        Put("J'ai choisi un nombre entre" & Integer'image(BORNE_INF) & " et" & Integer'image(BORNE_SUP) & ".");
        New_Line;

        Nb_Essais := 0;

        loop
                -- Demander un nombre à l'utilisateur
                loop
                        Nb_Essais := Nb_Essais + 1;
                        Put("Proposition" & Integer'image(Nb_Essais) & " : ");
                        Get(Proposition);
                        Skip_Line;
                        
                        if Proposition < BORNE_INF or Proposition > BORNE_SUP then
                                Put("Erreur : vous n'avez pas saisi un nombre entre" & Integer'image(BORNE_INF) & " et" & Integer'image(BORNE_SUP) & ".");
                                New_Line;
                        else
                                null;
                        end if;
                exit when (Proposition >= BORNE_INF and Proposition <= BORNE_SUP);
                end loop;


                -- Donner une indication sur le nombre saisi par l'utilisateur
                if Proposition = Nb_Ordi then
                        Put("Trouvé.");
                        New_Line;
                elsif Proposition < Nb_Ordi then
                        Put("Trop petit.");
                        New_Line;
                else
                        Put("Trop grand.");
                        New_Line;
                end if;

        exit when Proposition = Nb_Ordi;
        end loop;


        -- Afficher le résultat de la partie
        Put("Bravo. Vous avez trouvé" & Integer'image(Nb_Ordi) & " en" & Integer'image(Nb_Essais));
        if Nb_Essais = 1 then
                Put(" essai.");
        else
                Put(" essais.");
        end if;

end Jeu_Devin_Exo1;
