(* Pour les tests *)
(* [eq_perm l l'] retourne true ssi [l] et [l']
   sont égales à à permutation près (pour (=)).
   [l'] ne doit pas contenir de doublon. *)
let eq_perm l l' =
  List.length l = List.length l' && List.for_all (fun x -> List.mem x l) l'


module type StructureDonnees =
sig

  (* Type permettant de stocker le dictionnaire *)
  type dico

  (* Dictionnaire vide *)
  val empty : dico

  (* Ajoute un mot et son encodage au dictionnaire *)
  (* premier parametre : l'encodage du mot *)
  (* deuxième paramètre : le mot *)
  (* troisième paramètre : le dictionnaire *)
  val ajouter : int list -> string -> dico -> dico

  (* Cherche tous les mots associés à un encodage dans un dictionnaire *)
  (* premier parametre : l'encodage du mot *)
  (* second paramètre : le dictionnaire *)
  val chercher : int list -> dico -> string list


  (* Calcule le nombre maximum de mots ayant le même encodage dans un
     dictionnaire *)
  (* paramètre : le dictionnaire *)
  val max_mots_code_identique : dico -> int

  (* Liste tous les mots d'un dictionnaire dont un prefixe de l'encodage est donné en paramètre *)
  (* premier paramètre : le prefixe de l'encodage *)
  (* second paramètre : le dictionnaire *)
  val prefixe : int list -> dico -> string list

end

(* Exercice 1 *)
module ListAssoc : StructureDonnees =
struct
  type dico = (int list * string list) list

  let empty = []

  let rec ajouter touches mot dico =
    match touches, mot with
    | [], _ | _, "" -> dico
    | _ -> 
      match dico with
      | [] -> (touches,[mot])::dico
      | (touchesList, motsList)::suite_dico -> 
        if touchesList = touches then
          (touchesList, mot::motsList)::suite_dico
        else
          (touchesList, motsList)::(ajouter touches mot suite_dico)

  let%test _ = ajouter [2;3] "be" [] = [([2;3], ["be"])]
  let%test _ = ajouter [] "pf" [([2;3], ["be"])] = [([2;3], ["be"])]
  let%test _ = ajouter [2;3] "" [] = []
  let%test _ = ajouter [2;3] "be" [([7;3], ["pf"])] = [([7;3], ["pf"]); ([2;3], ["be"])]
  let%test _ = ajouter [2;3] "ae" [([2;3], ["be"])] = [([2;3], ["ae";"be"])]


  (* let rec chercher touches dico = 
    match dico with
    | [] -> []
    | (touchesList, motsList)::suite_dico ->
      if touches = touchesList then
        motsList
      else
        chercher touches suite_dico *)
  let chercher touches = List.fold_left (fun acc (touchesList, motsList) -> if touches = touchesList then motsList else acc) []

  let%test _ = eq_perm (chercher [2;2] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])]) ["bb";"aa";"cc"]
  let%test _ = eq_perm (chercher [3;3] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])]) []
  let%test _ = eq_perm (chercher [2;7;3;3] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])]) ["bref"]
  let%test _ = eq_perm (chercher [2;6;6] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])]) ["bon"]
  let%test _ = eq_perm (chercher [2;6;6] []) []


  let max_mots_code_identique = 
    List.fold_left (fun max (_, motsList) -> let taille = List.length motsList in if taille > max then taille else max) 0

  let%test _ = max_mots_code_identique [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])] = 3
  let%test _ = max_mots_code_identique [([2;7;3;3],["bref"]);([2;2],["bb";"aa";"cc"]); ([2;6;6],["bon"])] = 3
  let%test _ = max_mots_code_identique [] = 0
  let%test _ = max_mots_code_identique [([2;7;3;3],["bref"]);([2;2],["bb"]); ([2;6;6],["bon"])] = 1


  let rec prefixe p dico =
    let rec prefixe_valide pp touchesList =
      match pp, touchesList with
      | [], _ -> true
      | _::_, [] -> false
      | t1::q1, t2::q2 -> 
        if t1 = t2 then
          prefixe_valide q1 q2
        else
          false
    in
      match dico with
      | [] -> []
      | (touchesList, motsList)::suite_dico -> 
        if prefixe_valide p touchesList then
          motsList@(prefixe p suite_dico)
        else
          prefixe p suite_dico


  let%test _ = eq_perm (prefixe [] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])]) ["bb";"aa";"cc";"bref";"bon"]
  let%test _ = eq_perm (prefixe [] [([2;7;3;3],["bref"]);([2;2],["bb";"aa";"cc"]); ([2;6;6],["bon"])]) ["bref";"bb";"aa";"cc";"bon"]
  let%test _ = eq_perm (prefixe [] []) []
  let%test _ = eq_perm (prefixe [] [([2;7;3;3],["bref"]);([2;2],["bb"]); ([2;6;6],["bon"])]) ["bref";"bb";"bon"]
  let%test _ = eq_perm (prefixe [2] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])]) ["bb";"aa";"cc";"bref";"bon"]
  let%test _ = eq_perm (prefixe [2;2] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;6;6],["bon"])]) ["bb";"aa";"cc"]
  let%test _ = eq_perm (prefixe [2;2] [([2;2],["bb";"aa";"cc"]); ([2;7;3;3],["bref"]);([2;2;2],["bac";"bab"]);([2;6;6],["bon"])]) ["bb";"aa";"cc";"bac";"bab"]
end


(* Exercice 2 *)
module Arbre : StructureDonnees =
struct
  type dico = Noeud of (string list * (int * dico) list)

  let empty = Noeud([], [])

  let rec ajouter touches mot (Noeud(mots, fils_dico)) =
    let rec recherche n ldico =
      match ldico with
      | [] -> None
      | (num,sous_dico)::ldico_suite -> 
        if n = num then
          Some (num, sous_dico)
        else
          recherche n ldico_suite
    in
      match touches, mot with
      | _, "" -> Noeud(mots, fils_dico)
      | [], _ -> Noeud(mot::mots, fils_dico)
      | touche1::suite_touches, _ -> 
        match recherche touche1 fils_dico with
        | None -> Noeud(mots, (touche1, ajouter suite_touches mot empty)::fils_dico)
        | Some (_, dico_b) -> Noeud(mots, (touche1, (ajouter suite_touches mot dico_b))::fils_dico)


  let rec chercher touches (Noeud(mots, fils_dico)) = 
    let rec recherche n ldico =
      match ldico with
      | [] -> None
      | (num,sous_dico)::ldico_suite -> 
        if n = num then
          Some (num, sous_dico)
        else
          recherche n ldico_suite
    in
      match touches with
      | [] -> mots
      | touche1::suite_touches ->
        match recherche touche1 fils_dico with
        | None -> []
        | Some (_, dico_b) -> chercher suite_touches dico_b


  let rec max_mots_code_identique dico =
    let max = List.fold_left (fun acc x -> if x > acc then x else acc) 0 in
    let rec parcours_arbre (Noeud(mots, fils_dico)) =
      match fils_dico with
      | [] -> List.length mots
      | _ -> max (List.map parcours_branche fils_dico)  
    and parcours_branche (_, dico) = 
      parcours_arbre dico
    in parcours_arbre dico
  

  let rec prefixe touches (Noeud(mots, fils_dico)) = 
    let premieres_composantes =
      List.fold_left (fun acc (_,b) -> b::acc) []
    in
    let rec tous_les_mots (Noeud(mots, fils_dico)) =
      mots@(List.flatten (List.map tous_les_mots (premieres_composantes fils_dico)))
    in
    let rec recherche n ldico =
      match ldico with
      | [] -> None
      | (num,sous_dico)::ldico_suite -> 
        if n = num then
          Some sous_dico
        else
          recherche n ldico_suite
    in
    match touches, fils_dico with
    | [], _ -> tous_les_mots (Noeud(mots, fils_dico))
    | _, [] -> []
    | touche1::suite_touches, _ ->
      match recherche touche1 fils_dico with
      | None -> []
      | Some dico_b -> prefixe suite_touches dico_b


  (* TESTS - ATTENTION les tests peuvent échouer car l'ordre des branches n'est pas fixé *)

  let a1 = Noeud
      ([],
       [(2,
         Noeud
           ([],
            [(6,
              Noeud
                ([],
                 [(6,
                   Noeud
                     ([],
                      [(5,
                        Noeud
                          ([],
                           [(6,
                             Noeud
                               ([], [(8, Noeud ([], [(7, Noeud (["bonjour"], []))]))]))]))]))]))]))])
  let%test _ = a1 = ajouter [2;6;6;5;6;8;7] "bonjour" empty

  let a2 = Noeud
      ([],
       [(6,
         Noeud
           ([],
            [(2,
              Noeud
                ([],
                 [(2, Noeud ([], [(6, Noeud ([], [(5, Noeud (["ocaml"], []))]))]))]))]))])

  let%test _ = a2 = ajouter [6;2;2;6;5] "ocaml" empty

  let a3 =   Noeud
      ([],
       [(2, Noeud (["a"], []));
        (6,
         Noeud
           ([],
            [(2,
              Noeud
                ([],
                 [(2, Noeud ([], [(6, Noeud ([], [(5, Noeud (["ocaml"], []))]))]))]))]))])

  let%test _ = a3 = ajouter [2] "a" a2

  let a4 = Noeud ([], [(2, Noeud ([], [(8, Noeud (["au"], []))]))])

  let%test _ = a4 = ajouter [2;8] "au" empty

  let a5 = Noeud
      ([], [(2, Noeud ([], [(6, Noeud (["an"], [])); (8, Noeud (["au"], []))]))])

  let%test _ = a5 = ajouter [2;6] "an" a4

  let a6 = Noeud
      ([],
       [(2,
         Noeud
           ([],
            [(6, Noeud (["an"], [(3, Noeud (["ane"], []))]));
             (8, Noeud (["au"], []))]))])

  let%test _ = a6 = ajouter [2;6;3] "ane" a5

  let a7 = Noeud
      ([],
       [(2,
         Noeud
           ([],
            [(6, Noeud (["an"], [(3, Noeud (["ame";"ane"], []))]));
             (8, Noeud (["au"], []))]))])


  let%test _ = a7 = ajouter [2;6;3] "ame" a6

  let a8 = Noeud
      ([],
       [(2,
         Noeud
           ([],
            [(6, Noeud (["an"], [(3, Noeud (["bof";"ame";"ane"], []))]));
             (8, Noeud (["au"], []))]))])


  let%test _ = a8 = ajouter [2;6;3] "bof" a7

  let a9_1 = Noeud
      ([],
       [(2,
         Noeud
           ([],
            [(6, Noeud (["an"], [(3, Noeud (["bof";"ame";"ane"], []))]));
             (8, Noeud (["bu";"au"], []))]))])
  let a9_2 = Noeud
      ([],
       [(2,
         Noeud
           ([],
            [(8, Noeud (["bu"; "au"], []));
             (6, Noeud (["an"], [(3, Noeud (["bof"; "ame"; "ane"], []))]))]))])


  let%test _ = (a9_1 = ajouter [2;8] "bu" a8 || a9_2 = ajouter [2;8] "bu" a8)


  let%test _ = eq_perm (chercher [2;8] a9_1) ["bu"; "au"]
  let%test _ = eq_perm (chercher [2;6] a9_1) ["an"]
  let%test _ = eq_perm (chercher [2;6;3] a9_1) ["bof"; "ame"; "ane"]
  let%test _ = eq_perm (chercher [1;4;5] a9_1) []
  let%test _ = eq_perm (chercher [2;8] a9_2) ["bu"; "au"]
  let%test _ = eq_perm (chercher [2;6] a9_2) ["an"]
  let%test _ = eq_perm (chercher [2;6;3] a9_2) ["bof"; "ame"; "ane"]
  let%test _ = eq_perm (chercher [1;4;5] a9_2) []


  let%test _ = max_mots_code_identique a9_1 = 3
  let%test _ = max_mots_code_identique a9_2 = 3
  let%test _ = max_mots_code_identique a8 = 3
  let%test _ = max_mots_code_identique a7 = 2
  let%test _ = max_mots_code_identique a6 = 1
  let%test _ = max_mots_code_identique a5 = 1
  let%test _ = max_mots_code_identique a4 = 1
  let%test _ = max_mots_code_identique a3 = 1
  let%test _ = max_mots_code_identique a2 = 1
  let%test _ = max_mots_code_identique a1 = 1


  let%test _ = eq_perm (prefixe [2] a9_1) ["ame";"an";"ane";"au";"bof";"bu"]
  let%test _ = eq_perm (prefixe [2;6] a9_1) ["ame";"an";"ane";"bof"]
  let%test _ = eq_perm (prefixe [2;8] a9_1) ["au";"bu"]
  let%test _ = eq_perm (prefixe [3;8] a9_1) []
  let%test _ = eq_perm (prefixe [] a9_1) ["ame";"an";"ane";"au";"bof";"bu"]
  let%test _ = eq_perm (prefixe [] a9_2) ["ame";"an";"ane";"au";"bof";"bu"]
  let%test _ = eq_perm (prefixe [] a6) ["an";"ane";"au"]
end
