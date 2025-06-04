with Ada.Text_IO;          use Ada.Text_IO;
with Ada.Integer_Text_IO;  use Ada.Integer_Text_IO;

-- Auteur: 
-- Gérer un stock de matériel informatique.
--
package body Stocks_Materiel is

    procedure Creer (Stock : out T_Stock) is
    begin
        Stock.Capacite := 0;
    end Creer;


    function Nb_Materiels (Stock: in T_Stock) return Integer is
    begin
        return Stock.Capacite;
    end;


    procedure Enregistrer (
            Stock        : in out T_Stock;
            Numero_Serie : in     Integer;
            Nature       : in     T_Nature;
            Annee_Achat  : in     Integer
        ) is
        Materiel : T_Materiel;
    begin
        Materiel.Num_Serie := Numero_Serie;
        Materiel.Nature := Nature;
        Materiel.Annee := Annee_Achat;

        Stock.Tab_Materiel(Capacite) := Materiel;
        Stock.Capacite := Stock.Capacite + 1;
    end;


end Stocks_Materiel;
