%--------------------------------------------------------------------------
% ENSEEIHT - 1SN - Calcul scientifique
% TP1 - Orthogonalisation de Gram-Schmidt
% mgs.m
%--------------------------------------------------------------------------

function Q = mgs(A)

    % Recuperation du nombre de colonnes de A
    [~, m] = size(A);
    
    % Initialisation de la matrice Q avec la matrice A
    Q = A;
    
    %------------------------------------------------
    % A remplir
    % Algorithme de Gram-Schmidt modifie
    %------------------------------------------------
    for i = 1:m
        y = A(:,i);

        for j = 1:i-1
            proj_Qj_y = (y' * Q(:,j)) * Q(:,j);
            y = y - proj_Qj_y;
        end

        Q(:,i) = y / norm(y);
    end

end