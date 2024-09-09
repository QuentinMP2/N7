with Piles;

procedure Parenthesage is


    -- L'indice dans la chaîne Meule de l'élément Aiguille.
    -- Si l'Aiguille n'est pas dans la Meule, on retroune Meule'Last + 1.
    Function Index (Meule : in String; Aiguille: Character) return Integer with
        Post => Meule'First <= Index'Result and then Index'Result <= Meule'Last + 1
            and then (Index'Result > Meule'Last or else Meule (Index'Result) = Aiguille)
    is
        Indice : Integer;
    begin
        Indice := Meule'First;
        while Indice <= Meule'Last and then Meule(Indice) /= Aiguille loop
                Indice := Indice + 1;
        end loop;

        return Indice;
    end Index;


    -- Programme de test de Index.
    procedure Tester_Index is
        ABCDEF : constant String := "abcdef";
    begin
        pragma Assert (1 = Index (ABCDEF, 'a'));
        pragma Assert (3 = Index (ABCDEF, 'c'));
        pragma Assert (6 = Index (ABCDEF, 'f'));
        pragma Assert (7 = Index (ABCDEF, 'z'));
        pragma Assert (4 = Index (ABCDEF (1..3), 'z'));
        pragma Assert (3 = Index (ABCDEF (3..5), 'c'));
        pragma Assert (5 = Index (ABCDEF (3..5), 'e'));
        pragma Assert (6 = Index (ABCDEF (3..5), 'a'));
        pragma Assert (6 = Index (ABCDEF (3..5), 'g'));
    end;


    -- Vérifier les bon parenthésage d'une Chaîne (D).  Le sous-programme
    -- indique si le parenthésage est bon ou non (Correct : R) et dans le cas
    -- où il n'est pas correct, l'indice (Indice_Erreur : R) du symbole qui
    -- n'est pas appairé (symbole ouvrant ou fermant).
    --
    -- Exemples
    --   "[({})]"  -> Correct
    --   "]"       -> Non Correct et Indice_Erreur = 1
    --   "((()"    -> Non Correct et Indice_Erreur = 2
    --
    procedure Verifier_Parenthesage (Chaine: in String ; Correct : out Boolean ; Indice_Erreur : out Integer) is
        OUVRANTS : Constant String := "([{";
        FERMANTS : Constant String := ")]}";

        package Pile_Ouvrants
        is new Piles (Capacite => Chaine'Length, T_Element => Character);
        use Pile_Ouvrants;

        package Pile_Index
        is new Piles (Capacite => Chaine'Length, T_Element => Integer);
        use Pile_Index;

        Indice : Integer := Chaine'First;
        Pile_O : Pile_Ouvrants.T_Pile;
        Pile_I : Pile_Index.T_Pile;

    begin

        Correct := true;
        
        Initialiser (Pile_O);
        Initialiser (Pile_I);

        while Correct and Indice <= Chaine'Last loop
            
            if Index(OUVRANTS, Chaine(Indice)) < OUVRANTS'Length then
                Empiler(Pile_O, Chaine(Indice));
                Empiler(Pile_I, Indice);
            
            elsif Index(FERMANTS, Chaine(Indice)) < FERMANTS'Length then
                if not(Est_Vide(Pile_O)) and then Index(FERMANTS, Chaine(Indice)) = Index(OUVRANTS, Sommet(Pile_O)) then
                    Depiler(Pile_O);
                    Depiler(Pile_I);

                else
                    Correct := false;
                    Indice_Erreur := Indice;

                end if;

            else
                null;

            end if;

            Indice := Indice + 1;

        end loop;

        -- Correct = true
        if Correct then
            if Est_Vide(Pile_O) then
                null;
            else
                Correct := false;
                Indice_Erreur := Sommet(Pile_I);
            end if;
        else
            null;
        end if;

    end Verifier_Parenthesage;


    -- Programme de test de Verifier_Parenthesage
    procedure Tester_Verifier_Parenthesage is
        Exemple1 : constant String(1..2) :=  "{}";
        Exemple2 : constant String(11..18) :=  "]{[(X)]}";

        Indice : Integer;   -- Résultat de ... XXX
        Correct : Boolean;
    begin
        Verifier_Parenthesage ("(a < b)", Correct, Indice);
        pragma Assert (Correct);

        Verifier_Parenthesage ("([{a}])", Correct, Indice);
        pragma Assert (Correct);

        Verifier_Parenthesage ("(][{a}])", Correct, Indice);
        pragma Assert (not Correct);
        pragma Assert (Indice = 2);

        Verifier_Parenthesage ("]([{a}])", Correct, Indice);
        pragma Assert (not Correct);
        pragma Assert (Indice = 1);

        Verifier_Parenthesage ("([{}])}", Correct, Indice);
        pragma Assert (not Correct);
        pragma Assert (Indice = 7);

        Verifier_Parenthesage ("([{", Correct, Indice);
        pragma Assert (not Correct);
        pragma Assert (Indice = 3);

        Verifier_Parenthesage ("([{}]", Correct, Indice);
        pragma Assert (not Correct);
        pragma Assert (Indice = 1);

        Verifier_Parenthesage ("", Correct, Indice);
        pragma Assert (Correct);

        Verifier_Parenthesage (Exemple1, Correct, Indice);
        pragma Assert (Correct);

        Verifier_Parenthesage (Exemple2, Correct, Indice);
        pragma Assert (not Correct);
        pragma Assert (Indice = 11);

        Verifier_Parenthesage (Exemple2(12..18), Correct, Indice);
        pragma Assert (Correct);

        Verifier_Parenthesage (Exemple2(12..15), Correct, Indice);
        pragma Assert (not Correct);
        pragma Assert (Indice = 14);
    end Tester_Verifier_Parenthesage;

begin
    Tester_Index;
    Tester_Verifier_Parenthesage;
end Parenthesage;
