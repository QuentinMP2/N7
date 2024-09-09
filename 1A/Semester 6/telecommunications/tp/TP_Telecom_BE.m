clear all
close all

%% Paramètres généraux
Fe = 24000;     % Hz
Te = 1 / Fe;    % s
Rb = 3000;      % bits/s
Tb = 1 / Rb;    % s/bits
NbBits = 100000;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Étude de l'impact d'un canal à bruit additif, blanc et Gaussien - Efficacité en puissance %%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Création d'une séquence aléatoire de bits dans [0 1]
bits = randi([0 1], 1, NbBits);

%% Chaine 1
% Paramètres du modulateur
M = 2;                      % 2^1 : ordre du modulateur
Rs = Rb / log2(M);  
Ts = 1 / Rs;
Ns = floor(Ts / Te);        % Nombre d'échantillons sur une période symbole
Nbs = NbBits / log2(M);
Ne = Nbs * Ns;              % Nombre d'échantillons

% Transformer la séquence de bits en séquence de symboles
symboles = 2 * bits - 1;

% Calculer le peigne de Dirac associé à notre séquence de symboles
peigneDirac = kron(symboles, [1 zeros(1, Ns-1)]);

% Définition du filtre h (mise en forme et réception)
h = ones(1, Ns);    % car hauteur de la porte = 1

% Filtrage
modulation_signal = filter(h, 1, peigneDirac);

EbN0dB=[1:8];
EbN0=10.^(EbN0dB./10);
TEB_theorique = qfunc(sqrt(2 * EbN0));


TEB_chaine1 = zeros(1,length(EbN0dB));
for k = 1:length(EbN0dB)
    
    Px = mean(abs(modulation_signal).^2);
    sigma_n = sqrt((Px*Ns) / (2*log2(M) * EbN0(k) ));
    bruit = sigma_n * randn(1, length(modulation_signal));

    signal_canal = modulation_signal + bruit;

    demodulation_signal = filter(h, 1, signal_canal);

    diagramme_oeil = reshape(demodulation_signal, Ns, length(demodulation_signal) / Ns);
    
    % Détecteur à seuil
    n0 = 8;
    signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal)); % Ns = 8
    classes = signaldem_ech > 0;
    
    % Taux d'erreur binaire
    TEB_chaine1(k) = sum(classes ~= bits) / NbBits;

end

figure
semilogy(EbN0dB, TEB_theorique,'g','LineWidth',3)
hold on
semilogy(EbN0dB, TEB_chaine1,'r^','LineWidth',3,'MarkerSize',10);
legend("TEB théorique","TEB empirique");
title("Tracé des TEB pour la chaine 1");
xlabel('E_b/N_0(dB)')
grid
set(gca,'FontSize',12)

