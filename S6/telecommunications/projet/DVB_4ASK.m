clear all; 
close all; 

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Transmission avec une modulation 4-ASK                                   %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%v
Fe = 24000;
Te = 1/Fe;
Rb = 3000;
Tb = 1/Rb;

nbits = 1000;
bits = randi([0 1], 1, nbits);
n = 2;
M = 2^n;                    %ordre de la modulation
Fp = 2000; 
                      % 2^2 : ordre du modulateur
Rs = Rb / log2(M);          % Débit symbole (symboles/s)
Ts = 1 / Rs;   
Ns = floor(Ts / Te);        % Nombre d'échantillons sur une période symbole
Nbs = nbits / log2(M);     % Nombre de symboles
Ne = Nbs * Ns; 

%Mapping 
entiers= bi2de(reshape(bits,n,nbits/n)');
ak3 = pammod(entiers,M)';

% Filtre de mise en forme en racine de cosinus surélevé
alpha = 0.35;                       % Roll-off
L = 10;
hmef = rcosdesign(alpha, L, Ns);
hr = fliplr(hmef);
g3 = conv(hmef, hr);
%plot(g3);

xb3 = [kron(ak3, [1 zeros(1, Ns-1)]) zeros(1,length(hmef))];
x3 = filter(hmef,1,xb3);
echelle_temps = [0:length(x3)-1]*Te;


% Bruit 
bruitmax1 = 10;
EbN0db = [0:bruitmax1];
EbN0=10.^(EbN0db./10);

for k=1:length(EbN0db)
    rapp = EbN0(k);
    sigma = (mean(abs(x3).^2)*Ns)/(2*log2(4)*rapp);
    bruit = sqrt(sigma)*randn(1,length(x3));
    signal_bruit = x3 + bruit;

    z3 = filter(hr, 1, signal_bruit);

    %%Le projeté de to sur les échantillons(critère de Nyquist);
    n0_3 = L*Ns+1;

    %Echantillonage
    z_echantillone3 = z3(n0_3:Ns:end-1);

    entiers_estimes = pamdemod((z_echantillone3),M);
    bits_recuperes = reshape(de2bi(entiers_estimes)',1,[]);
    
    % Tracé des constellations en sortie du mapping et de l'échantilloneur
    figure
    plot(z_echantillone3,1,'dg','DisplayName','En sortie de l échantilloneur');
    hold on
    plot(ak3 ,1,'rp','DisplayName','En sortie du mapping');
    titre = sprintf('Tracé des constellations pour Eb/N0 = %d', k);
    title(titre);

    
    %Calcul du taux d'erreur binaire expérimental
    taux_erreur = sum(bits_recuperes ~= bits)/length(bits);
    TEB(k) = taux_erreur;
end

%Calcul du taux d'erreur binaire théorique
TES_theorique = 2*(1-1/4)*qfunc(sqrt((12*EbN0)/(15)));
TEB_theorique = TES_theorique / 2;
figure
semilogy(EbN0db, TEB ,'DisplayName','expérimental');
hold on
semilogy(EbN0db, TEB_theorique,'DisplayName', 'théorique');
xlabel("Rapport signal à bruit (Eb/N0) en dB")
ylabel("Taux d'erreur binaire")
title("Taux d'erreur binaire expérimental et théorique");
legend;

