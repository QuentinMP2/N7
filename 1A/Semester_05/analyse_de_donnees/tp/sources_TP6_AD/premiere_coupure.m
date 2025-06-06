% fonction premiere_coupure (pour l'exercice 0)
% A COMPLETER
function [valeur_coupure,ind_variable_coupure] = premiere_coupure(X,Y)

    % Initialisation
    min_delta_i_modifie = +Inf;
    X_sorted = zeros(size(X));
    n = length(Y);
    % Parcours de chaque variable
    for k = 1:size(X,2)
        % Tri des valeurs de la variable k
        [X_sorted(:,k),ind_var_k] = sort(X(:,k),'ascend');
        Y_sorted = Y(ind_var_k);
        % Parcours des differentes decoupes possibles
        for split = 1:size(X,1)-1
            % Decoupe des Y (droite et gauche)
            Y_g = Y_sorted(1:split);
            Y_d = Y_sorted(split+1:end);
            % Calcul des indices de Gini (gauche et droit)
            %%% A COMPLETER
            Gini_g = sum(Y_g==1)/length(Y_g) * (1-sum(Y_g==1)/length(Y_g)) + sum(Y_g==-1)/length(Y_g) * (1-sum(Y_g==-1)/length(Y_g));
            Gini_d = sum(Y_d==1)/length(Y_d) * (1-sum(Y_d==1)/length(Y_d)) + sum(Y_d==-1)/length(Y_d) * (1-sum(Y_d==-1)/length(Y_d));
            % Calcul de delta_i_modifie = p_g Gini_g + p_d Gini_d
            p_g = length(Y_g)/length(Y);
            p_d = length(Y_d)/length(Y);
            delta_i_modifie = p_g * Gini_g + p_d * Gini_d;
            % Recuperation de la variable et de la valeur de decoupe
            if delta_i_modifie < min_delta_i_modifie
                min_delta_i_modifie = delta_i_modifie;
                ind_variable_coupure = k;
                valeur_coupure = (X_sorted(split,k)+X_sorted(split+1,k))/2;
            end
        end
    end
end
