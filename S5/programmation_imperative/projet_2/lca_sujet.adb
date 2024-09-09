with Ada.Text_IO;           use Ada.Text_IO;
with Ada.Integer_Text_IO;   use Ada.Integer_Text_IO;
with SDA_Exceptions; 	    use SDA_Exceptions;
with Ada.Strings.Unbounded; use Ada.Strings.Unbounded;
	--! Les Unbounded_String ont une capacité variable, contrairement au String
	--! pour lesquelles une capacité doit être fixée.
with LCA;

procedure LCA_Sujet is

        package LCA_String_Integer is
                new LCA (T_Cle => Unbounded_String, T_Valeur => Integer);
        use LCA_String_Integer;
               

        procedure Afficher_LCA_String_Integer (Cle: in Unbounded_String ; Valeur: in Integer) is
        begin        
                Put("-->[");
                Put(To_String(Cle));
                Put(" : ");
                Put(Valeur, 1);
                Put("]");

        end Afficher_LCA_String_Integer;
        

        procedure Afficher is
            new Pour_Chaque (Traiter => Afficher_LCA_String_Integer);


        procedure Afficher_Integer (N: Integer) is
        begin
                Put(N,1);
        end Afficher_Integer;
        
        procedure Afficher_Unbounded_String (US: Unbounded_String) is
        begin
                Put('"');
                Put(To_String(US));
                Put('"');
        end Afficher_Unbounded_String;

        procedure Afficher_Debug_Unbounded_String_Integer is
                new Afficher_Debug (Afficher_Cle => Afficher_Unbounded_String, Afficher_Donnee => Afficher_Integer);


        LCA_Test : T_LCA;
begin
        Initialiser(LCA_Test);
        Enregistrer(LCA_Test, To_Unbounded_String("un"), 1);
        Enregistrer(LCA_Test, To_Unbounded_String("deux"), 2);
        Afficher(LCA_Test);
        New_Line;
        Afficher_Debug_Unbounded_String_Integer(LCA_Test);
        Detruire(LCA_Test);
        
end LCA_Sujet;
