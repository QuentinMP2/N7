with Text_Io;              use Text_Io;
with Ada.Integer_Text_Io;  use Ada.Integer_Text_Io;
with Alea;
with Jeu_Devin_Exo1;
with Jeu_Devin_Exo2;

-- Auteur : POINTEAU Quentin

procedure Jeu_Devin_Exo3 is
        
        BORNE_INF : Constant Integer := 1;      -- Borne inférieur de l'intervalle de jeu
        BORNE_SUP : Constant Integer := 999;    -- Borne supérieur de l'intervalle de jeu

        package Mon_Alea is
                new Alea (BORNE_INF, BORNE_SUP);
        use Mon_Alea;

        Mode_Jeu : Integer;     -- Mode de jeu saisi par l'utilisateur

begin
        -- Démarrer une partie du jeu du devin
        loop
                -- Demander le mode de jeu à l'utilisateur
                New_Line;
                New_Line;
                Put("1- L'ordinateur choisit un nombre et vous le devinez");
                New_Line;
                Put("2- Vous choisissez un nombre et l'ordinateur le devine");
                New_Line;
                Put("0- Quitter le programme");
                New_Line;
                Put("Votre choix : ");
                Get(Mode_Jeu);
                Skip_Line;

                -- Traiter le choix de l'utilisateur
                case Mode_Jeu is
                        when 0 =>
                                Put("Au revoir...");
                        when 1 =>
                                Jeu_Devin_Exo1;
                        when 2 =>
                                Jeu_Devin_Exo2;
                        when others =>
                                Put("Erreur : veuillez choisir un nombre entre 0 et 2.");
                                New_Line;
                end case;

        exit when Mode_Jeu = 0;
        end loop;

end Jeu_Devin_Exo3;
