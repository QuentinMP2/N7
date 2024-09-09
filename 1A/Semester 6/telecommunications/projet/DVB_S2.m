clear all;
close all;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Transmission avec une modulation 8-PSK                                   %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Suite de bits transmise
nbits = 1002;
bits = randi([0 1], 1, nbits);

% Paramètres 
% Modulation 8-PSK
M = 8;                      % Ordre de modulation
Fe = 6000;                  % Fréquence d'échantillonnage (Hz)
Te = 1/ Fe;                 % Période d'échantillonnage (s)
Rb = 3000;                  % Débit binaire (bit/s)
Tb = 1 / Rb;                % Période binaire (s)
Fp = 2000;                  % Fréquence porteuse (Hz)
Rs = Rb / log2(M);                % Débit symbole
Ts = 1/Rs;                  % Période de symbole
Ns = floor(Ts / Te);        % Nombre d'échantillons par symbole
Nbs = nbits / log2(M);           % Nombre de symboles
Ne = Nbs * Ns;              % Nombre total d'échantillons


% Filtre de mise en forme
alpha = 0.2; % Roll-off
L = 10; 
h = rcosdesign(alpha, L, Ns);

% Filtre de réception adapté
hr = fliplr(h);

% Mapping 8-PSK
n = log2(M);
entiers= (bi2de(reshape(bits,n,nbits/n).')).';
dk = pskmod(entiers, M, pi/M); % Modulation 8-PSK 

% Peigne de Dirac
Diracs = [kron(dk, [1 zeros(1, Ns - 1)]) zeros(1, length(h))];

% Signal à la sortie du filtre de mise en forme
x = filter(h, 1, Diracs);

% Bruit 
bruitmax1 = 6;
EbN0db = [0:bruitmax1];
EbN0=10.^(EbN0db./10);

for k = 1:length(EbN0db)
    rapp = EbN0(k);

    sigma = mean(abs(x).^2)*Ns/(2*log2(M)*rapp);
    bruit = sqrt(sigma)*randn(1,length(x)) + 1i*sqrt(sigma)*randn(1, length(x));
    signal_bruit = x + bruit;
   
    % Demodulation bande de base
    %signal sortie du filtre de réception
    signal_demod = filter(hr, 1, signal_bruit); 

    % Echantillonnage du signal démodulé
    n0 = L*Ns+1;
    signaldem_ech = signal_demod(n0:Ns:length(signal_demod)-1); 
    
    
    % Tracé des constellations en sortie du mapping et de l'échantilloneur
    figure
    plot(signaldem_ech ,'dg');
    hold on
    plot(dk, 'pr', 'LineWidth', 3);
    titre = sprintf('Tracé des constellations pour Eb/N0 = %d', k);
    title(titre);
    
    % Demapping
    symboles_estim = pskdemod(signaldem_ech , M, pi / M);
    
    bits_estim = de2bi(symboles_estim, log2(M)).';
    bits_estim = bits_estim(:).';

    % Calcul du TEB
    taux_erreur = sum(bits ~= bits_estim) / nbits;
    TEB(k) = taux_erreur;

end

% Comparaison des TEB
TEB_theorique = (2 / log2(M)) * qfunc(sqrt(2 * log2(M) * EbN0) * sin(pi / M));
figure
semilogy(EbN0db, TEB);
hold on
semilogy(EbN0db, TEB_theorique);
xlabel('Eb/N0');
ylabel('TEB');
title("Tracé des TEB théorique et expérimental");
legend('TEB experimental','TEB Théorique');