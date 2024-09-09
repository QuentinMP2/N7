% fonction estim_param_MC_paire (pour exercice_2.m)

function parametres = estim_param_MC_paire(d,x,y_inf,y_sup) 
    p = length(x);
    Aux = zeros(p,d-1);
    for i = 1:d-1
        Aux(:,i) = vecteur_bernstein(x,d,i);
    end
    A = [Aux, zeros(p,d-1), vecteur_bernstein(x,d,d); zeros(p,d-1), Aux, vecteur_bernstein(x,d,d)];
    
    B = [y_inf-y_inf(1)*vecteur_bernstein(x,d,0); y_sup-y_sup(1)*vecteur_bernstein(x,d,0)];

    parametres = A\B;

end
