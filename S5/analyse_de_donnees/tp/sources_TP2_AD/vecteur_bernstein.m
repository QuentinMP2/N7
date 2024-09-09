% fonction vecteur_bernstein (pour exercice_1.m)

function resultat = vecteur_bernstein(x,d,k)
    k_parmi_d = nchoosek(d,k);
    resultat = k_parmi_d * x.^k .* (1-x(:)).^(d-k);

end