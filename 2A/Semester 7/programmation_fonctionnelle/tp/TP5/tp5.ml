(* Définition de types *)
type env = (string*int) list
type eval_env = env -> int

(* Module abstrayant les expressions simples *)
module type ExprSimple =
sig
  type t
  val const : int -> t
  val plus : t -> t -> t
  val mult : t -> t -> t
end

(* Module réalisant l'évaluation d'une expression *)
module EvalSimple : ExprSimple with type t = int =
struct
  type t = int
  let const c = c
  let plus e1 e2 = e1 + e2
  let mult e1 e2 = e1 * e2
end

(* Module transformant les expressions simples en chaîne de caractères *)
module PrintSimple : ExprSimple with type t = string =
struct
  type t = string
  let const c = string_of_int c
  let plus e1 e2 = "(" ^ e1 ^ "+" ^ e2 ^ ")"
  let mult e1 e2 = "(" ^ e1 ^ "*" ^ e2 ^ ")"
end

(* Module comptant les opérations d'une expression *)
module CompteSimple : ExprSimple with type t = int =
struct
  type t = int
  let const _ = 0
  let plus e1 e2 = 1 + e1 + e2
  let mult e1 e2 = 1 + e1 + e2
end

(* Module abstrayant la présence de variables dans les expressions *)
module type ExprVar =
sig
  type t
  val def : string -> t -> t -> t
  val var : string -> t
end

(* Module abstrayant les expressions *)
module type Expr =
sig
  include ExprSimple
  include (ExprVar with type t := t)
end

(* Module transformant les expressions avec variables en chaînes de caractères *)
module PrintVar : ExprVar with type t = string =
struct
  type t = string
  let def name e1 e2 = "let " ^ name ^ " = " ^ e1 ^ " in " ^ e2
  let var name = name
end

(* Module transformant les expressions en chaînes de caractères *)
module Print : Expr with type t = string =
struct
  include PrintSimple
  include (PrintVar : ExprVar with type t := t)
end

(* Module réalisant l'évaluation d'une expression avec variables *)
module EvalVar : ExprVar with type t = eval_env =
struct
  type t = eval_env
  let def name e1 e2 = fun env -> e2 ((name,e1 env)::env)
  let var name = List.assoc name
end

(* Module réalisant l'évaluation d'une expression simple avec un environnement *)
module EvalEnvSimple : ExprSimple with type t = eval_env =
struct
  type t = eval_env
  let const c = fun _ -> c
  let plus e1 e2 = fun env -> (e1 env) + (e2 env)
  let mult e1 e2 = fun env -> (e1 env) * (e2 env)
end

(* Module réalisant l'évalutation des expressions avec un environnement *)
module Eval : Expr with type t = eval_env =
struct
  include EvalEnvSimple
  include (EvalVar : ExprVar with type t := t)
end

(* Définition des expressions *)
module ExemplesSimples (E:ExprSimple) =
struct
  (* (1+(2*3)) *)
  let exemple1  = E.(plus (const 1) (mult (const 2) (const 3)) )
  (* ((5+2)*(2*3)) *)
  let exemple2 =  E.(mult (plus (const 5) (const 2)) (mult (const 2) (const 3)) )
end

module Exemples (E:Expr) =
struct
  (* let x = (1+2) in (x*3) *)
  let exemple1 = E.(def "x" (plus (const 1) (const 2)) (mult (var "x") (const 3)))
  (* ((3*6)+(9+3)) *)
  let exemple2 = E.(plus (mult (const 3) (const 6)) (plus (const 9) (const 3)))
end


(* Module de test des exemples : EvalSimple *)
module EvalExemples = ExemplesSimples (EvalSimple)

let%test _ = (EvalExemples.exemple1 = 7)
let%test _ = (EvalExemples.exemple2 = 42)

(* Module de test des exemples : PrintSimple *)
module PrintExemples = ExemplesSimples (PrintSimple)

let%test _ = (PrintExemples.exemple1 = "(1+(2*3))")
let%test _ = (PrintExemples.exemple2 = "((5+2)*(2*3))")

(* Module de test des exemples : CompteSimple *)
module CompteExemples = ExemplesSimples (CompteSimple)

let%test _ = (CompteExemples.exemple1 = 2)
let%test _ = (CompteExemples.exemple2 = 3)

(* Module de test des exemples : Print *)
module PrintExemplesDur = Exemples (Print)

let%test _ = (PrintExemplesDur.exemple1 = "let x = (1+2) in (x*3)")
let%test _ = (PrintExemplesDur.exemple2 = "((3*6)+(9+3))")

(* Module de test des exemples : Eval *)
module EvalExemplesDur = Exemples (Eval)

let%test _ = (EvalExemplesDur.exemple1 [] = 9)
let%test _ = (EvalExemplesDur.exemple2 [] = 30)