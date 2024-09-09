% fonction modelisation_vraisemblance (pour l'exercice 1)

function modele_V = modelisation_vraisemblance(X,mu,Sigma)
    nb_donnees = length(X);
    modele_V = zeros(nb_donnees,1);

    for i = 1:nb_donnees
        modele_V(i) = 1/(2*pi*sqrt(det(Sigma))) * exp((-1/2) * (X(i,:)-mu) * inv(Sigma) * (X(i,:)-mu)');
    end

end