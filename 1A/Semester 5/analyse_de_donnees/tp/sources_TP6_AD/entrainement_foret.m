% fonction entrainement_foret (pour l'exercice 2)

function foret = entrainement_foret(X,Y,nb_arbres,proportion_individus)
        foret = cell(nb_arbres,1);
        nb_individus = round(proportion_individus * size(X,1));
        nb_variables = round(sqrt(size(X,2)));
        for i = 1:nb_arbres
            tirage = randperm(length(X));
            X_rand = X(tirage(1:nb_individus),:);
            Y_rand = Y(tirage(1:nb_individus));
            foret{i} = fitctree(X_rand,Y_rand,'NumVariablesToSample', nb_variables);
        end
        
end
