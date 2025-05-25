(* CONTRAT 
combinaison : int -> 'a list -> 'a list list - 
    Calcule la liste de toutes les permutations à k éléments dans une liste.
Paramètres :
    - k : int - entier positif
    - l : 'a list - liste d'éléments quelconques
Préconditions : 
    - k >= 0
Résultat : 'a list list - Liste de toutes les combinaisons à k éléments dans l
*)  
let rec combinaison k l =  
  match k,l with
  | (0,_) -> [[]]
  | (_,[]) -> []
  | (_,t::q) -> (List.map (fun l -> t::l) (combinaison (k-1) q)) @ (combinaison k q)

(* TESTS *)
let%test _ = combinaison 2 [] = [] 
let%test _ = combinaison 0 [1;2;3;4] = [[]]
let%test _ = combinaison 1 [1;2;3;4] = [[1]; [2]; [3]; [4]]
let%test _ = combinaison 2 [1;2;3;4] = [[1;2]; [1;3]; [1;4]; [2;3]; [2;4]; [3;4]]
let%test _ = combinaison 3 [1;2;3;4] = [[1;2;3]; [1;2;4]; [1;3;4]; [2;3;4]]
let%test _ = combinaison 4 [1;2;3;4] = [[1;2;3;4]]
let%test _ = combinaison 5 [1;2;3;4] = []