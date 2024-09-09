% Fonction calcul_proba (exercice_2.m)

function [x_min,x_max,probabilite] = calcul_proba(E_nouveau_repere,p)

    x = [0:length(E_nouveau_repere)];
    x_min = min(E_nouveau_repere(:,1));
    x_max = max(E_nouveau_repere(:,1));
    probabilite = binocdf(x,length(E_nouveau_repere),p);

    
end