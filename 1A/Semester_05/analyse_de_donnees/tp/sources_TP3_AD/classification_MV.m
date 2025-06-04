% fonction classification_MV (pour l'exercice 2)

function Y_pred_MV = classification_MV(X,mu_1,Sigma_1,mu_2,Sigma_2)
    nb_donnees = length(X);
    Y_pred_MV = zeros(nb_donnees,1);
    
    MV1 = modelisation_vraisemblance(X, mu_1, Sigma_1);
    MV2 = modelisation_vraisemblance(X, mu_2, Sigma_2);

    Y_pred_MV(MV1 > MV2) = 1;
    Y_pred_MV(MV1 <= MV2) = 2;
    
end
