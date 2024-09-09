with Piles;
with Ada.Text_IO;            use Ada.Text_IO;

-- Montrer le risque d'autoriser l'affectation entre variables dont le type
-- est une structure chaînée.
procedure Illustrer_Affectation_Pile is
	package Pile is
		new Piles (Character);
	use Pile;

	procedure Afficher is
		new Pile.Afficher (Put);

	P1, P2 : T_Pile;
begin
	-- construire la pile P1
	Initialiser (P1);
	Empiler (P1, 'A');
	Empiler (P1, 'B');
	Afficher (P1); New_Line;   -- XXX Qu'est ce qui s'affiche ?
                                   -- A et B

	P2 := P1;                  -- XXX Conseillé ?
                                   -- Pas conseillé, comme vu dans les 
                                   -- exercices précédents
	pragma Assert (P1 = P2);

	Depiler (P2);              -- XXX Quel effet ?
                                   -- On affecte un pointeur à la cellule à 
                                   -- détruire, puis on affecte le pointeur de 
                                   -- la Pile vers la prochaine cellule et 
                                   -- enfin on détruit la cellule à détruire.

	Afficher (P2); New_Line;   -- XXX Qu'est ce qui s'affiche ?
                                   -- A

	Afficher (P1); New_Line;   -- XXX Qu'est ce qui s'affiche ?
                                   -- A et ???

	-- XXX Que donne l'exécution avec valgrind ?

	Depiler (P1);	-- XXX correct ?
                        -- Ça ne va pas fonctionner car la mémoire a déjà 
                        -- été libérée
end Illustrer_Affectation_Pile;
