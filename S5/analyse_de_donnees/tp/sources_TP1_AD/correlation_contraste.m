% function correlation_contraste (pour exercice_1.m)

function [correlation,contraste] = correlation_contraste(X)
    n = size(X,1);
    X_centre = X - mean(X);
    Sigma = (1/n) * (X_centre)' * X_centre;
    r_R_V = Sigma(2,1) / sqrt(Sigma(1,1) * Sigma(2,2));
    r_R_B = Sigma(3,1) / sqrt(Sigma(1,1) * Sigma(3,3));
    r_V_B = Sigma(3,2) / sqrt(Sigma(2,2) * Sigma(3,3));
    correlation = [r_R_V, r_R_B, r_V_B];

    c_R = Sigma(1,1) / (Sigma(1,1) + Sigma(2,2) + Sigma(3,3));
    c_V = Sigma(2,2) / (Sigma(1,1) + Sigma(2,2) + Sigma(3,3));
    c_B = Sigma(3,3) / (Sigma(1,1) + Sigma(2,2) + Sigma(3,3));
    contraste = [c_R, c_V, c_B];
    
end
