clear all
close all

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Comparaison 4-ASK et QPSK                                         %
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Paramètres généraux
Fe = 24000;                 % Fréquence d'échantillonnage (Hz)
Te = 1/ Fe;                 % Période d'échantillonnage (s)
Rb = 3000;                  % Débit binaire (bit/s)
Tb = 1 / Rb;                % Période binaire (s)
Fp = 2000;                  % Fréquence porteuse (Hz)

% Création d'une séquence aléatoire de bits dans [0 1]
NbBits = 10000;
bits = randi([0 1], 1, NbBits);

% Paramètres du modulateur
M = 4;                      % 2^2 : ordre du modulateur
Rs = Rb / log2(M);          % Débit symbole (symboles/s)
Ts = 1 / Rs;                % Période symbole (s)
Ns = floor(Ts / Te);        % Nombre d'échantillons sur une période symbole
Nbs = NbBits / log2(M);     % Nombre de symboles
Ne = Nbs * Ns;              % Nombre d'échantillons sur la totalité du signal

% Mapping
signal_mappe = (1-2*bits(1:2:NbBits) + 1i*(1-2*bits(2:2:NbBits))).';

% Filtre de mise en forme en racine de cosinus surélevé
alpha = 0.15;                       % Roll-off
L = 10;
h = rcosdesign(alpha, L, Ns);       % Filtre de mise en forme

% Filtre de réception adapté
hr = fliplr(h);

% Séparation signal en phase et signal en quadrature
% Signal en phase
signal_phase = real(signal_mappe);

% Signal en quadrature
signal_quadrature = imag(signal_mappe);

% Peigne de Dirac
peigneDirac_phase = [kron(signal_phase', [1 zeros(1, Ns - 1)]) zeros(1,length(h))];
I_t = filter(h, 1, peigneDirac_phase);
peigneDirac_quadrature = [kron(signal_quadrature', [1 zeros(1, Ns - 1)]) zeros(1,length(h))];
Q_t = filter(h, 1, peigneDirac_quadrature);

% Echelle temporelle
echelle_temps = [0:length(I_t)-1]*Te;

% Bruit 
bruitmax1 = 6;
EbN0db = [0:bruitmax1];
EbN0=10.^(EbN0db./10);

% Transposition sur fréquence porteuse pour calculer la puissance du
% signal
    signal_phase_porteuse = I_t .* cos(2*pi*Fp*echelle_temps);
    signal_quadrature_porteuse = Q_t .* sin(2*pi*Fp*echelle_temps);
    signal = signal_phase_porteuse - signal_quadrature_porteuse;

for k=1:length(EbN0db)
    rapp = EbN0(k);
    sigma = mean(abs(signal).^2)*Ns/(log2(4)*rapp);
    bruit = sqrt(sigma)*randn(1,length(signal)) + 1i*sqrt(sigma)*randn(1,length(signal));
    signal_transmis = I_t + 1i*Q_t;
    signal_bruit = signal_transmis + bruit
    I_t_bruit = I_t + real(bruit);
    Q_t_bruit = Q_t + imag(bruit);

    
    % DSP de l'enveloppe complexe associée au signal transmis
    Sx_Welch_DVB = pwelch(signal_bruit, [],[],[],Fe,'twosided');
    
    
    % Demodulation du signal
    signal_demod = filter(hr, 1, signal_bruit);
    
    % Echantillonnage du signal démodulé
    n0 = L*Ns+1;
    signaldem_ech = signal_demod(n0:Ns:length(signal_demod)-1);

       
    % Demapping
    bits_estim = zeros(1, NbBits);
    bits_estim(1:2:NbBits) = real(signaldem_ech) < 0;
    bits_estim(2:2:NbBits) = imag(signaldem_ech) < 0;
    
    % Calcul du TEB
    taux_erreur = sum(bits_estim ~= bits)/length(bits);
    TEB_DVB(k) = taux_erreur;
end
nbits = NbBits;
%Mapping 
entiers= bi2de(reshape(bits,2,nbits/2)');
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
%signal_phase_porteuse = x3 .* cos(2*pi*Fp*echelle_temps);
signal_phase_porteuse = x3;

% Bruit 

for k=1:length(EbN0db)
    rapp = EbN0(k);
    sigma = (mean(abs(signal_phase_porteuse).^2)*Ns)/(2*log2(4)*rapp);
    bruit = sqrt(sigma)*randn(1,length(signal_phase_porteuse));
    signal_phase_porteuse_bruit = signal_phase_porteuse + bruit;

    %z3 = filter(hr, 1, signal_phase_porteuse_bruit.* cos(2*pi*Fp*echelle_temps));
    z3 = filter(hr, 1, signal_phase_porteuse_bruit);

    %%Le projeté de to sur les échantillons(critère de Nyquist);
    n0_3 = L*Ns+1;

    %On multiplie par 2 parce que la modulation par deux cosinus donne un facteur
    %multipicatif de 1/2
    %z_echantillone3 = 2*z3(n0_3+1:Ns:length(z3));
    z_echantillone3 = z3(n0_3:Ns:end-1);

    entiers_estimes = pamdemod((z_echantillone3),M);
    bits_recuperes = reshape(de2bi(entiers_estimes)',1,[]);
    taux_erreur = sum(bits_recuperes ~= bits)/length(bits)
    TEB_4ASK(k) = taux_erreur;
end
Sx_Welch_4ASK = pwelch(signal_phase_porteuse_bruit, [],[],[],Fe,'twosided');
    
figure;
echelle_frequentielle = linspace(-Fe/2, Fe/2, length(Sx_Welch_DVB));
semilogy(echelle_frequentielle, fftshift(abs(Sx_Welch_DVB)), 'r', 'LineWidth', 1.2);
hold on
semilogy(echelle_frequentielle, fftshift(abs(Sx_Welch_4ASK)), 'b', 'LineWidth', 1.2);
xlabel("Fréquences (Hz)")
ylabel("DSP")
title("Tracé des DSP de modulateurs QPSK et 4-ASK");

TES_theorique = 2*(1-1/4)*qfunc(sqrt((12*EbN0)/(15)));
TEB_theorique = TES_theorique / 2;
figure
semilogy(EbN0db, TEB_4ASK ,'DisplayName','4ASK');
hold on
semilogy(EbN0db, TEB_DVB,'DisplayName', 'QPSK');
xlabel("Rapport signal à bruit (Eb/N0) en dB")
ylabel("Taux d'erreur binaire")
title("Taux d'erreur binaire pour la 4ASK et la QPSK");
legend;
