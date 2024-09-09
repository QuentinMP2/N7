with Text_Io;              use Text_Io;
with Ada.Integer_Text_Io;  use Ada.Integer_Text_Io;
with Alea;

-- Auteur : BARTHES Alexandre

procedure Jeu_Devin_Exo2 is
    Triche : Boolean;					-- Devient vrai lorsque le joueur triche
    Correct : Boolean;					-- Devient vrai lorsque le nombre est trouvé
    BorneSup : Integer;
    BorneInf : Integer;
    NbEssais : Integer;
    NombrePropose : Integer;			-- Le nombre que l'on proposera au joueur
    JoueurPret : Character;				-- Permet de savoir quand le joueur a choisi son nombre
    Indication : Character;				-- L'indication fourni par le joueur pour deviner le nombre correct
    
begin
    -- Deviner le nombre choisi par l'utilisateur

    -- Initialiser les paramètres
    Triche := False;
    Correct := False;
    BorneInf := 1;
    BorneSup := 1000;
    NombrePropose := (BorneInf + BorneSup)/2;
    NbEssais := 0;
    
    -- Demander à l'utilisateur de choisir un nombre
    New_Line;
    Put ("Avez-vous choisi un nombre entre 1 et 999 (o/n) ?");
    New_Line;
    Get (JoueurPret);
    
    while JoueurPret /= 'o' loop
        New_Line;
        Put ("J'attends...");
        New_Line;
        Put ("Avez-vous choisi un nombre entre 1 et 999 (o/n) ?");
        New_Line;
        Get (JoueurPret);
        Skip_Line;
    end loop;
    
    loop
        -- Faire une proposition
        
        NbEssais := NbEssais + 1;
        
        New_Line;
        Put ("Proposition" & integer'image(NbEssais) & " :" & integer'image(NombrePropose));
        New_Line;

        -- Demander un indice
        Put ("Trop (g)rand, trop (p)etit ou (t)rouvé ?");
        New_Line;
        Get (Indication);
        Skip_Line;
        
        while Indication/= 'g' and Indication /= 'p' and Indication /= 't' loop
           
            -- Afficher le message explicatif
            New_Line;
            Put ("Je n'ai pas compris. Merci de me répondre : ");
            New_Line;
            Put ("g si ma proposition est trop grande");
            New_Line;
            Put ("p si ma proposition est trop petite");
            New_Line;
            Put ("t si j'ai trouvé le nombre.");
            New_Line;
            
            Get (Indication);
            Skip_Line;
        end loop;
        
        -- Ajuster l'intervalle de recherche
        if Indication = 't' then
            Correct := True;
        else
            
            -- Vérifier la triche
            if (BorneSup - BorneInf) < 3 then
                Triche := True;
                
            else

                -- Rétrécir l'intervalle par dichotomie
                if Indication = 'g' then
                    BorneSup := NombrePropose;
                else
                    BorneInf := NombrePropose;
                end if;   
                NombrePropose := (BorneSup + BorneInf)/2;
                
            end if;
        end if;  
        
        
        exit when Triche or Correct;
    end loop;
    New_Line;

    -- Afficher le résultat
    if Triche then
        Put ("Vous trichez, j'arrête cette partie.");
    else

        -- Afficher le message de victoire
        Put ("J'ai trouvé" & integer'image(NombrePropose) & " en" & integer'image(NbEssais));

        if NbEssais = 1 then
            Put (" essai.");
        else
            Put (" essais.");
        end if;
    end if;
        
end Jeu_Devin_Exo2;
