% fonction qualite_classification (pour l'exercice 2)

function [pourcentage_bonnes_classifications_total,pourcentage_bonnes_classifications_fibrome, ...
          pourcentage_bonnes_classifications_melanome] = qualite_classification(Y_pred,Y)
    
    diff = abs(Y - Y_pred);
    n = length(diff);
    pourcentage_bonnes_classifications_fibrome = (1 - (sum(diff(1:(n/2))) / (n/2))) * 100;
    pourcentage_bonnes_classifications_melanome = (1 - (sum(diff((n/2 + 1):n)) / (n/2))) * 100;
    pourcentage_bonnes_classifications_total = (1 - (sum(diff) / n)) * 100;



end