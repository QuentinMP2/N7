% fonction calcul_bon_partitionnement (pour l'exercice 1)

function meilleur_pourcentage_partitionnement = calcul_bon_partitionnement(Y_pred,Y)

    n = length(unique(Y));
    permutations = perms(1:n);
    pourcentages = zeros(factorial(n),1);
    
    for i = 1:factorial(n)
        perm_actu = permutations(i,:);
        Y_permute = Y_pred;
        for j = 1:length(Y)
            if Y_permute(j) == 1
                Y_permute(j) = perm_actu(1);
            elseif Y_permute(j) == 2
                Y_permute(j) = perm_actu(2);
            else
                Y_permute(j) = perm_actu(3);
            end
        end
        pourcentages(i) = sum(Y == Y_permute)/length(Y) * 100;
    end
    
    meilleur_pourcentage_partitionnement = max(pourcentages);
end