% Fonction matrice_inertie (exercice_2.m)

function [M_inertie,C] = matrice_inertie(E,G_norme_E) 
    
    pi_maj = sum(G_norme_E);
    x_barre = 1/pi_maj * sum((G_norme_E .* E(:,1)));
    y_barre = 1/pi_maj * sum((G_norme_E .* E(:,2)));
    C = [x_barre, y_barre];

    xi_moins_xbarre = (E(:,1) - x_barre);
    yi_moins_ybarre = (E(:,2) - y_barre);

    M11 = 1/pi_maj * sum(G_norme_E .* xi_moins_xbarre .* xi_moins_xbarre);
    M12 = 1/pi_maj * sum(G_norme_E .* xi_moins_xbarre .* yi_moins_ybarre);
    M22 = 1/pi_maj * sum(G_norme_E .* yi_moins_ybarre .* yi_moins_ybarre);
    M_inertie = [M11, M12; M12, M22];

end