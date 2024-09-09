% fonction classification_MAP (pour l'exercice 3)

function Y_pred_MAP = classification_MAP(X,p1,mu_1,Sigma_1,mu_2,Sigma_2)
    n = length(X);
    Y_pred_MAP = zeros(n,1);

    for i = 1:n
        Y_pred_MAP(i) = modelisation_vraisemblance(X, );
    end

    
end
