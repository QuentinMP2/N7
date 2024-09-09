% Fonction ensemble_E_recursif (exercie_1.m)

function [E,contour,G_somme] = ensemble_E_recursif(E,contour,G_somme,i,j,...
                                                   voisins,G_x,G_y,card_max,cos_alpha)

contour(i,j) = 0;
for v = voisins'
    iv = i+v(1);
    jv = j+v(2);
    
    if contour(iv,jv) == 1
        Gv = [G_x(iv,jv), G_y(iv,jv)];
        
        if Gv/norm(Gv) * transpose(G_somme/norm(G_somme)) >= cos_alpha
            E = [E; iv jv];
            G_somme = G_somme + Gv;
            [E, contour, G_somme] = ensemble_E_recursif(E, contour, G_somme, iv, jv, voisins, G_x, G_y, card_max, cos_alpha);

        end

    end
    
end