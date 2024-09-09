% function ACP (pour exercice_2.m)

function [C,bornes_C,coefficients_RVG2gris] = ACP(X)
    n = size(X,1);
    X_centre = X - mean(X);
    Sigma = (1/n) * (X_centre)' * X_centre;

    [W,D] = eig(Sigma);
    vals_propres = diag(D);
    [~, ordre] = sort(vals_propres, 'descend');
    W = W(:,ordre);
    C = X * W;
    bornes_C = [min(min(C)), max(max(C))];
    coefficients_RVG2gris = W(:,1) / sum(W(:,1));

    
end
