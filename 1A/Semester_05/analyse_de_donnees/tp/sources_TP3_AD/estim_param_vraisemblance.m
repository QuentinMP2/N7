% fonction estim_param_vraisemblance (pour l'exercice 1)

function [mu,Sigma] = estim_param_vraisemblance(X)
    mu = [mean(X(:,1)), mean(X(:,2))];
    X_c = [X(:,1) - mu(1), X(:,2) - mu(2)];
    Sigma = (1/length(X)) * (X_c)' * X_c;
end