(* Module précisant la base dans laquelle vont être décomposer des entiers *)
module type Base = sig
  (* Entier qui va servir de base de décomposition des entiers*)
  val base : int
end

(* Module de la base 2 *)
module Base2 : Base =
struct
  let base = 2
end

(* Module de la base 5 *)
module Base5 : Base =
struct
  let base = 5
end