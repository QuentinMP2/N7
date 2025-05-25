open Base

(* A COMPLETER : foncteur Conversion *)
module Conversion (B:Base) =
struct
  (* decompose : réalise une décomposition dans la base d’un entier supposé naturel*)
  (* int -> int list *)
  (* pré-condition : entier est positif *)
  (* résultat : la décomposition dans la base *)
  let decompose n =
    let rec decompose_aux nn acc =
      if nn = 0 then
        acc
      else
        decompose_aux (nn/B.base) ((nn mod B.base)::acc)
    in decompose_aux n []

  (* recompose : réalise la recomposition d'un entien naturel depuis sa représentation dans la base *)
  (* int list -> int *)
  (* L'entier représenté par la liste de booléens *)
  (* let recompose l =
    let taille_l = List.length l in 
    let l_puiss = List.mapi (fun i x -> x*(int_of_float (Float.pow (float_of_int B.base) (float_of_int (taille_l-1-i))))) l in 
    List.fold_left (fun acc x -> x + acc) 0 l_puiss *)

  let recompose l =
    let rec recompose_aux i ll acc =
      match ll with
      | [] -> acc
      | t::q -> recompose_aux (i-1) q (acc + t*(int_of_float (Float.pow (float_of_int B.base) (float_of_int i))))
    in recompose_aux (List.length l -1) l 0
end


module TestConversion2 = struct
  module Conversion2 = Conversion (Base2)
  open Conversion2

  let%test_unit _ =
    print_string "=== Tests du module Conversion en Base 2 ===\n"
  
  (* decompose *)
  let%test _ = decompose 0 = []
  let%test _ = decompose 1 = [ 1 ]
  let%test _ = decompose 2 = [ 1; 0 ]
  let%test _ = decompose 3 = [ 1; 1 ]
  let%test _ = decompose 4 = [ 1; 0; 0 ]
  let%test _ = decompose 5 = [ 1; 0; 1 ]
  let%test _ = decompose 6 = [ 1; 1; 0 ]
  let%test _ = decompose 7 = [ 1; 1; 1 ]
  let%test _ = decompose 14 = [ 1; 1; 1; 0 ]

  (* recompose *)
  let%test _ = recompose [] = 0
  let%test _ = recompose [ 1 ] = 1
  let%test _ = recompose [ 1; 0 ] = 2
  let%test _ = recompose [ 1; 1 ] = 3
  let%test _ = recompose [ 1; 0; 0 ] = 4
  let%test _ = recompose [ 1; 0; 1 ] = 5
  let%test _ = recompose [ 1; 1; 0 ] = 6
  let%test _ = recompose [ 1; 1; 1 ] = 7
  let%test _ = recompose [ 1; 1; 1; 0 ] = 14
end

module TestConversion5 = struct
  module Conversion5 = Conversion (Base5)
  open Conversion5

  let%test_unit _ =
    print_string "=== Tests du module Conversion en Base 5 ===\n"
  
  (* decompose *)
  let%test _ = decompose 0 = []
  let%test _ = decompose 1 = [ 1 ]
  let%test _ = decompose 2 = [ 2 ]
  let%test _ = decompose 3 = [ 3 ]
  let%test _ = decompose 4 = [ 4 ]
  let%test _ = decompose 5 = [ 1; 0 ]
  let%test _ = decompose 6 = [ 1; 1 ]
  let%test _ = decompose 7 = [ 1; 2 ]
  let%test _ = decompose 14 = [ 2; 4 ]
  let%test _ = decompose 36 = [ 1; 2; 1 ]

  (* recompose *)
  let%test _ = recompose [] = 0
  let%test _ = recompose [ 1 ] = 1
  let%test _ = recompose [ 2 ] = 2
  let%test _ = recompose [ 3 ] = 3
  let%test _ = recompose [ 4 ] = 4
  let%test _ = recompose [ 1; 0 ] = 5
  let%test _ = recompose [ 1; 1 ] = 6
  let%test _ = recompose [ 1; 2 ] = 7
  let%test _ = recompose [ 2; 4 ] = 14
  let%test _ = recompose [ 1; 2; 1 ] = 36
end