(******* TRIS ******)

(*  Tri par insertion **)

(*CONTRAT
Fonction qui ajoute un élément dans une liste triée, selon un ordre donné
Type : ('a -> 'a -> bool) -> 'a -> 'a list -> 'a list
Paramètre : ordre  ('a -> 'a -> bool), un ordre sur les éléments de la liste
Paramètre : elt, l'élement à ajouter
Paramètre : l, la liste triée dans laquelle ajouter elt
Résultat : une liste triée avec les éléments de l, plus elt
*)
let rec insert ordre elt l = 
  match l with
  | [] -> [elt]
  | t::q -> (
    if ordre elt t then
      elt::l
    else
      t::(insert ordre elt q) 
  )

(* let insert ordre elt l = 
  match l with 
  | [] -> [elt]
  | _ -> if ordre elt (List.hd l) then elt::l else List.fold_right (fun t iq -> if ordre elt t then t::iq else t::elt::iq) l [] *)

(* TESTS *)
let%test _ = insert (fun x y -> x<y) 3 [] = [3]
let%test _ = insert (fun x y -> x<y) 3 [2;4;5] = [2;3;4;5]
let%test _ = insert (fun x y -> x>y) 6 [3;2;1] = [6;3;2;1]


(*CONTRAT
Fonction qui trie une liste, selon un ordre donné
Type : ('a -> 'a -> bool)-> 'a list -> 'a list
Paramètre : ordre  ('a -> 'a -> bool), un ordre sur les éléments de la liste
Paramètre : l, la liste à trier
Résultat : une liste triée avec les éléments de l
*)
let tri_insertion ordre l = List.fold_left (fun acc x -> insert ordre x acc) [] l

(* TESTS *)
let%test _ = tri_insertion (fun x y -> x<y) [] =[]
let%test _ = tri_insertion (fun x y -> x<y) [4;2;4;3;1] =[1;2;3;4;4]
let%test _ = tri_insertion (fun x y -> x > y) [4;7;2;4;1;2;2;7]=[7;7;4;4;2;2;2;1]


(*  Tri fusion **)

(* CONTRAT
Fonction qui décompose une liste en deux listes de tailles égales à plus ou moins un élément
Paramètre : l, la liste à couper en deux
Retour : deux listes
*)
(* let rec scinde l =
  match l with
  | [] -> ([],[])
  | [e] -> ([e],[])
  | t1::t2::q -> let (l1, l2) = scinde q in (t1::l1, t2::l2) *)

let scinde l = 
  let rec scinde_aux l0 l1 l2 =
    match l0 with
    | [] -> (l1,l2)
    | [e] -> (e::l1,l2)
    | t1::t2::q -> scinde_aux q (t1::l1) (t2::l2)
  in scinde_aux l [] []

(* TESTS *)
(* Peuvent être modifiés selon l'algorithme choisi *)
let%test _ = scinde [1;2;3;4] = ([3;1],[4;2])
let%test _ = scinde [1;2;3] = ([3;1],[2])
let%test _ = scinde [1] = ([1],[])
let%test _ = scinde [] = ([],[])

let scinde_term l =
  let taille = (List.length l) / 2 in
  let rec scinde_term_aux l1 l2 i =
    if i >= taille then 
      l1,l2 
    else
      match l2 with 
      | [] -> l1,l2
      | t::q -> scinde_term_aux (t::l1) q (i+1)
  in scinde_term_aux [] l 0

(* TESTS *)
(* Peuvent être modifiés selon l'algorithme choisi *)
let%test _ = scinde_term [1;2;3;4] = ([2;1],[3;4])
let%test _ = scinde_term [1;2;3] = ([1],[2;3])
let%test _ = scinde_term [1] = ([],[1])
let%test _ = scinde_term [] = ([],[])


(* Fusionne deux listes triées pour en faire une seule triée
Paramètre : ordre  ('a -> 'a -> bool), un ordre sur les éléments de la liste
Paramètre : l1 et l2, les deux listes triées
Résultat : une liste triée avec les éléments de l1 et l2
*)
let rec fusionne ordre l1 l2 = 
  match l1,l2 with 
  | [],[] -> []
  | l,[] | [],l -> l
  | t1::q1,t2::q2 -> 
    if ordre t1 t2 then 
      t1::(fusionne ordre q1 l2) 
    else 
      t2::(fusionne ordre l1 q2)  

(*TESTS*)
let%test _ = fusionne (fun x y -> x<y) [1;2;4;5;6] [3;4] = [1;2;3;4;4;5;6]
let%test _ = fusionne (fun x y -> x<y) [1;2;4] [3;4] = [1;2;3;4;4]
let%test _ = fusionne (fun x y -> x<y) [1;2;4] [3;4;8;9;10] = [1;2;3;4;4;8;9;10]
let%test _ = fusionne (fun x y -> x<y) [] [] = []
let%test _ = fusionne (fun x y -> x<y) [1] [] = [1]
let%test _ = fusionne (fun x y -> x<y) [] [1] = [1]
let%test _ = fusionne (fun x y -> x<y) [1] [2] = [1;2]
let%test _ = fusionne (fun x y -> x>y) [1] [2] = [2;1]


let fusionne_term ordre l1 l2 = 
  let rec fusionne_term_aux l1 l2 acc =
    match l1,l2 with
    | [],[] -> acc
    | t::q,[] | [],t::q -> fusionne_term_aux q [] (t::acc) 
    | t1::q1,t2::q2 -> 
      if ordre t1 t2 then 
        fusionne_term_aux q1 l2 (t1::acc) 
      else 
        fusionne_term_aux l1 q2 (t2::acc)
      in fusionne_term_aux l1 l2 []

(*TESTS*)
let%test _ = fusionne_term (fun x y -> x<y) [1;2;4;5;6] [3;4] = [6;5;4;4;3;2;1]
let%test _ = fusionne_term (fun x y -> x<y) [1;2;4] [3;4] = [4;4;3;2;1]
let%test _ = fusionne_term (fun x y -> x<y) [1;2;4] [3;4;8;9;10] = [10;9;8;4;4;3;2;1]
let%test _ = fusionne_term (fun x y -> x<y) [] [] = []
let%test _ = fusionne_term (fun x y -> x<y) [1] [] = [1]
let%test _ = fusionne_term (fun x y -> x<y) [] [1] = [1]
let%test _ = fusionne_term (fun x y -> x<y) [1] [2] = [2;1]
let%test _ = fusionne_term (fun x y -> x>y) [1] [2] = [1;2]

(*
Contrat not_ordre : ('a -> 'a -> bool) -> ('a -> 'a -> bool) -
  Renvoie la négation de l'ordre donné
Paramètres :
  - ordre : 'a -> 'a -> bool - ordre donné
Résultat : 'a -> 'a -> bool - négation de ordre
*)
let not_ordre ordre =
  fun x y -> ordre y x

let%test _ = not_ordre (fun x y -> x<y) 5 3
let%test _ = not(not_ordre (fun x y -> x<y) 'a' 'b')


(* CONTRAT
Fonction qui trie une liste, selon un ordre donné
Type : ('a -> 'a -> bool)-> 'a list -> 'a list
Paramètre : ordre  ('a -> 'a -> bool), un ordre sur les éléments de la liste
Paramètre : l, la liste à trier
Résultat : une liste triée avec les éléments de l
*)
let rec tri_fusion ordre l =
  match l with
  | [] -> []
  | [e] -> [e] 
  | _ -> let l1,l2 = scinde l in fusionne ordre (tri_fusion ordre l1) (tri_fusion ordre l2)

(* TESTS *)
let%test _ = tri_fusion (fun x y -> x<y) [] =[]
let%test _ = tri_fusion (fun x y -> x<y) [4;2;4;3;1] =[1;2;3;4;4]
let%test _ = tri_fusion (fun x y -> x > y) [4;7;2;4;1;2;2;7]=[7;7;4;4;2;2;2;1]


let tri_fusion_term ordre l =
  let rec aux_neg l =
    match l with
    | [] -> []
    | [e] -> [e]
    | _ -> let l1,l2 = scinde_term l in 
    fusionne_term (not_ordre ordre) (aux_pos l1) (aux_pos l2)
  and aux_pos l =
    match l with
    | [] -> []
    | [e] -> [e]
    | _ -> let l1,l2 = scinde_term l in
    fusionne_term ordre (aux_neg l1) (aux_neg l2)
  in aux_neg l

(* TESTS *)
let%test _ = tri_fusion_term (fun x y -> x<y) [] = []
let%test _ = tri_fusion_term (fun x y -> x<y) [4;2;4;3;1] = [1;2;3;4;4]
let%test _ = tri_fusion_term (fun x y -> x > y) [4;7;2;4;1;2;2;7] = [7;7;4;4;2;2;2;1]

(*
Réponse exercice 6 :
La liste doit être coupée en deux morceaux égaux pour minimiser la complexité.
*)


(*  Parsing du fichier *)

(* Affiche un quadruplet composé 
- du sexe des personnes ayant reçu ce prénom : 1 pour les hommes, 2 pour les femmes
- du prénom
- de l'année
- du nombre de fois où ce prénom a été donné cette année là
*)
let print_stat (sexe,nom,annee,nb) =
  Printf.eprintf "%s,%s,%d,%d%!\n" (if (sexe=1) then "M" else "F") nom annee nb

(* Analyse le fichier nat2016.txt (stratistique des prénoms entre 1900 et 2016) 
 et construit une liste de quadruplet (sexe,prénom,année,nombre d'affectation)
*)
let listStat = 
  let input = open_in "/home/quentin/Documents/N7/S7/programmation_fonctionnelle/tp/TP2/nat2016.txt" in 
  let filebuf = Lexing.from_channel input in
  Parser.main Lexer.token filebuf
  

(* Analyse le fichier nathomme2016.txt (stratistique des prénoms d'homme commençant par un A ou un B entre 1900 et 2016) 
 et construit une liste de quadruplets (sexe,prénom,année,nombre d'affectations)
*)
let listStatHomme = 
  let input = open_in "/home/quentin/Documents/N7/S7/programmation_fonctionnelle/tp/TP2/nathomme2016.txt" in
  let filebuf = Lexing.from_channel input in
  Parser.main Lexer.token filebuf

(*  Les contrats et les tests des fonctions suivantes sont à écrire *)
let ordre_nb_prenoms quad1 quad2 =
  let (_,_,_,nb1) = quad1 and (_,_,_,nb2) = quad2 in nb1 < nb2

let test = tri_fusion ordre_nb_prenoms listStat
let test_mieux = tri_fusion_term ordre_nb_prenoms listStat


(*
Contrat n_premiers : 'a list -> int -> 'a list - 
  Garde les n premiers éléments d'une liste
Paramètres :
  - l : 'a list - liste dont on va garder les n premiers éléments
  - n : int - nombre d'éléments à garder
Résultat : 'a list - liste des n premiers éléments de l
*)
let n_premiers l n = 
  let rec n_premiers_aux _l _n l_acc =
    if _n = 0 then 
      l_acc 
    else
      match _l with
      | [] -> l_acc
      | t::q -> n_premiers_aux q (_n-1) (t::l_acc)
  in n_premiers_aux l n []


let%test _ = n_premiers [] 0 = []
let%test _ = n_premiers [1;2;3;4] 0 = []
let%test _ = n_premiers [1;2;3;4] 2 = [2;1]
let%test _ = n_premiers [1;2;3;4] 10 = [4;3;2;1]
let%test _ = n_premiers ['b';'w';'l'] 3 = ['l';'w';'b']

(*
Contrat n_premiers_ordre : 'a list -> int -> ('a -> 'a -> bool) -> 'a list - 
  Garde les n premiers éléments d'une liste selon un ordre donné
Paramètres :
  - l : 'a list - liste dont on va garder les n premiers éléments
  - n : int - nombre d'éléments à garder
  - ordre : 'a -> 'a -> bool - ordre donné
Résultat : 'a list - liste des n premiers éléments de l selon ordre
*)
let n_premiers_ordre l n ordre =
  n_premiers (tri_fusion_term ordre l) n

let%test _ = n_premiers_ordre [] 0 (fun x y -> x<y) = []
let%test _ = n_premiers_ordre [1;2;3;4] 0 (fun x y -> x<y) = []
let%test _ = n_premiers_ordre [3;2;1;4] 2 (fun x y -> x<y) = [2;1]
let%test _ = n_premiers_ordre [1;2;3;4] 10 (fun x y -> x<y) = [4;3;2;1]
let%test _ = n_premiers_ordre ['b';'w';'l'] 3 (fun x y -> x<y) = ['w';'l';'b']
let%test _ = n_premiers_ordre ['b';'w';'l'] 1 (fun x y -> x>y) = ['w']