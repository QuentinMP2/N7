(*  Exercice à rendre **)
(*
  pgcd : int -> int -> int
  Calcule le pgcd de deux entiers.
  Paramètre a : int
  Paramètre b : int
  Resultat : int, le pgcd de a et b
  Précondition : (a <> 0 || b <> 0)
*)
let rec pgcd a b =
  let abs x = if x < 0 then -x else x in
  let a = abs a in
  let b = abs b in
  match (a,b) with
  | (0,0) -> failwith "Erreur: on doit avoir (a <> 0 || b <> 0)"
  | (0,a) | (a,0) -> a
  | _ -> (
    if a > b then
      pgcd (a-b) b
    else
      pgcd a (b-a)
  ) 

(* Tests unitaires *)
let%test _ = pgcd 4 4 = 4
let%test _ = pgcd 10 2 = 2
let%test _ = pgcd 12 5 = 1
let%test _ = pgcd 8 0 = 8
let%test _ = pgcd 0 3 = 3
let%test _ = pgcd (-7) 0 = 7
let%test _ = pgcd 0 (-14) = 14
let%test _ = pgcd (-4) (-2) = 2