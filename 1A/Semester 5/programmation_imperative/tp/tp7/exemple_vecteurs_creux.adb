with Ada.Text_IO;       use Ada.Text_IO;
with Ada.Float_Text_IO; use Ada.Float_Text_IO;
with Vecteurs_Creux;    use Vecteurs_Creux;

-- Exemple d'utilisation des vecteurs creux.
procedure Exemple_Vecteurs_Creux is

	V1 : T_Vecteur_Creux;
        V2 : T_Vecteur_Creux;
	Epsilon: constant Float := 1.0e-5;
begin
	Put_Line ("Début du scénario");
	
        -- Initialisation les vecteurs
        Initialiser(V1);
        Initialiser(V2);

        Afficher(V1);
        New_Line;
        Afficher(V2);
        New_Line;

        pragma assert(Est_Nul(V1));
        pragma assert(Est_Nul(V2));

        Modifier(V1, 1, 5.0);
        Modifier(V1, 3, 2.0);
        Modifier(V1, 2 ,3.0);

        Afficher(V1);
        New_Line;

        pragma assert(Composante_Recursif(V1, 1) - 5.0 <= EPSILON and Composante_Recursif(V1, 1) - 5.0 >= -EPSILON);
        pragma assert(Composante_Iteratif(V1, 1) - 5.0 <= EPSILON and Composante_Iteratif(V1, 1) - 5.0 >= -EPSILON);


        pragma assert(not(Sont_Egaux_Recursif(V1, V2)));
        pragma assert(not(Sont_Egaux_Iteratif(V1, V2)));

        Modifier(V2, 4, 1.0);
        Modifier(V2, 5, 2.0);
        Modifier(V2, 6, 3.0);

        pragma assert(Norme2(V2) - 14.0 <= EPSILON and Norme2(V2) - 14.0 >= -EPSILON);
        
        Modifier(V2, 1, 10.0);

        pragma assert(Composante_Iteratif(V2, 1) = 10.0);
        pragma assert(Composante_Recursif(V2, 1) = 10.0);
        pragma assert(Produit_Scalaire(V1,V2) - 61.0 <= EPSILON and Produit_Scalaire(V1,V2) - 61.0 >= -EPSILON);

        Afficher(V1);
        New_Line;
        Afficher(V2);
        New_Line;

        pragma assert(Nombre_Composantes_Non_Nulles(V1) = 3);
        pragma assert(Nombre_Composantes_Non_Nulles(V2) = 4);

        Detruire(V1);
        Detruire(V2);

	Put_Line ("Fin du scénario");
end Exemple_Vecteurs_Creux;
