with Ada.Text_IO;           use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with SDA_Exceptions; 	    use SDA_Exceptions;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
	--! Les Unbounded_String ont une capacité variable, contrairement au String
	--! pour lesquelles une capacité doit être fixée.
with LCA;
with TH;

procedure TH_Sujet is

        function Fonction_Hachage (Cle: Unbounded_String) return Integer is
            Copie_Cle : constant String := To_String(Cle);
        begin
            return Copie_Cle'Length;
        end Fonction_Hachage;


        package TH_String_Integer is
                new TH (T_Cle => Unbounded_String, T_Valeur => Integer, CAPACITE => 11, Fonction_Hachage => Fonction_Hachage);
        use TH_String_Integer;
        

        procedure Afficher_TH_String_Integer (Cle : in Unbounded_String ; Valeur : in Integer) is
        begin        
                Put("-->[");
                Put(To_String(Cle));
                Put(" : ");
                Put(Valeur, 1);
                Put("]");
        end Afficher_TH_String_Integer;
        

        procedure Afficher is
            new Pour_Chaque (Traiter => Afficher_TH_String_Integer);


        procedure Afficher_Integer (N : Integer) is
        begin
                Put(N,1);
        end Afficher_Integer;
        

        procedure Afficher_Unbounded_String (US : Unbounded_String) is
        begin
                Put('"');
                Put(To_String(US));
                Put('"');
        end Afficher_Unbounded_String;


        procedure Afficher_Debug_Unbounded_String_Integer is
                new Afficher_Debug (Afficher_Cle => Afficher_Unbounded_String, Afficher_Donnee => Afficher_Integer);


        TH_Test : T_TH;
begin
        TH_String_Integer.Initialiser(TH_Test);
        TH_String_Integer.Enregistrer(TH_Test, To_Unbounded_String("un"), 1);
        TH_String_Integer.Enregistrer(TH_Test, To_Unbounded_String("deux"), 2);
        TH_String_Integer.Enregistrer(TH_Test, To_Unbounded_String("trois"), 3);
        TH_String_Integer.Enregistrer(TH_Test, To_Unbounded_String("quatre"), 4);
        TH_String_Integer.Enregistrer(TH_Test, To_Unbounded_String("cinq"), 5);
        TH_String_Integer.Enregistrer(TH_Test, To_Unbounded_String("quatre-vingt-dix-neuf"), 99);
        TH_String_Integer.Enregistrer(TH_Test, To_Unbounded_String("vingt-et-un"), 21);
        Afficher(TH_Test);
        New_Line;
        Afficher_Debug_Unbounded_String_Integer(TH_Test);
        TH_String_Integer.Detruire(TH_Test);
        
end TH_Sujet;