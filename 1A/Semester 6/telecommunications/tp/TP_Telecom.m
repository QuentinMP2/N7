clear all
close all

%% Paramètres généraux
Fe = 24000;     % Hz
Te = 1 / Fe;    % s
Rb = 3000;      % bits/s
Tb = 1 / Rb;    % s/bits
NbBits = 1000;

% Création d'une séquence aléatoire de bits dans [0 1]
bits = randi([0 1], 1, NbBits);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Etude de modulateurs en bande de base - efficacité spectrale %%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%% Modulateur 1

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

% Définition du filtre h
h = ones(1, Ns);    % car hauteur de la porte = 1

% Filtrage
x = filter(h, 1, peigneDirac);

% Echelle de temps
echelle_temporelle = linspace(0, Ts, Ne);

% Calcul de la DSP
Sx_Welch_Mod1 = pwelch(x, [],[],[],Fe,'twosided');

% Echelle en fréquence
echelle_frequentielle_Mod1 = linspace(-Fe/2, Fe/2, length(Sx_Welch_Mod1));

% Tracé de la réponse impulsionnelle du filtre
figure
subplot(2,1,1)
plot(echelle_temporelle, x);
title('Signal généré en sortie du filtre pour le modulateur 1');
xlabel('Temps');
ylabel('Signal');

subplot(2,1,2)
semilogy(echelle_frequentielle_Mod1, fftshift(abs(Sx_Welch_Mod1)), 'b')
title('DSP pour le modulateur 1');
xlabel('Frequences');
ylabel('|DSP|');


%% Modulateur 2

% Paramètres du modulateur
M = 4;                      % 2^2 : ordre du modulateur
Rs = Rb / log2(M);  
Ts = 1 / Rs;
Ns = floor(Ts / Te);        % Nombre d'échantillons sur une période symbole
Nbs = NbBits / log2(M);
Ne = Nbs * Ns;              % Nombre d'échantillons

% Transformer la séquence de bits en séquence de symboles
entiers = bi2de(reshape(bits, NbBits/log2(M), log2(M)));
symboles = (2*entiers-3)';

% Calculer le peigne de Dirac associé à notre séquence de symboles
peigneDirac = kron(symboles, [1 zeros(1, Ns-1)]);

% Définition du filtre h
h = ones(1, Ns);    % car hauteur de la porte = 1

% Filtrage
x = filter(h, 1, peigneDirac);

% Echelle de temps
echelle_temporelle = linspace(0, Ts, length(x));

% Calcul de la DSP
Sx_Welch_Mod2 = pwelch(x, [],[],[],Fe,'twosided');

% Echelle en fréquence
echelle_frequentielle_Mod2 = linspace(-Fe/2, Fe/2, length(Sx_Welch_Mod2));

% Tracé de la réponse impulsionnelle du filtre
figure
subplot(2,1,1)
plot(echelle_temporelle, x);
title('Signal généré en sortie du filtre pour le modulateur 2');
xlabel('Temps');
ylabel('Signal');

subplot(2,1,2)
semilogy(echelle_frequentielle_Mod2, fftshift(abs(Sx_Welch_Mod2)), 'b')
title('DSP pour le modulateur 2');
xlabel('Frequences');
ylabel('|DSP|');


%% Modulateur 3

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

% Définition du filtre h
alpha = 0.5;
L = 10;
h = rcosdesign(alpha, L, Ns);

% Filtrage
x = filter(h, 1, peigneDirac);

% Echelle de temps
echelle_temporelle = linspace(0, Ts, length(x));

% Calcul de la DSP
Sx_Welch_Mod3 = pwelch(x, [],[],[],Fe,'twosided');

% Echelle en fréquence
echelle_frequentielle_Mod3 = linspace(-Fe/2, Fe/2, length(Sx_Welch_Mod3));

% Tracé de la réponse impulsionnelle du filtre
figure
subplot(2,1,1)
plot(echelle_temporelle, x);
title('Signal généré en sortie du filtre pour le modulateur 3');
xlabel('Temps');
ylabel('Signal');

subplot(2,1,2)
semilogy(echelle_frequentielle_Mod3, fftshift(abs(Sx_Welch_Mod3)), 'b')
title('DSP pour le modulateur 3');
xlabel('Frequences');
ylabel('|DSP|');


%% Tracé des 3 DSP sur la meme figure

figure
semilogy(echelle_frequentielle_Mod1, fftshift(abs(Sx_Welch_Mod1)), 'b')
hold on
semilogy(echelle_frequentielle_Mod2, fftshift(abs(Sx_Welch_Mod2)), 'r')
hold on
semilogy(echelle_frequentielle_Mod3, fftshift(abs(Sx_Welch_Mod3)), 'g')
legend('Modulateur 1', 'Modulateur 2', 'Modulateur 3')
title('Tracé des DSP supperposées')
xlabel('Frequences')
ylabel('|DSP|')


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Etude des interférences entre symboles - critère de Nyquist %%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Nombre de bits
NbBits = 10;

% Création d'une séquence aléatoire de bits dans [0 1]
bits = randi([0 1], 1, NbBits);

%% Signal en sortie du filtre de réception

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
demodulation_signal = filter(h, 1, modulation_signal);
reponse_impulsionnelle_globale = conv(h, h);


% Tracé des symboles
% Echelle de temps
echelle_temporelle = linspace(0, Ts, Ne);

figure
subplot(3,1,1)
plot(echelle_temporelle, peigneDirac);
title('Symboles');
xlabel('Temps');
ylabel('Symboles')

% Tracé de la réponse impulsionnelle globale de la chaine de transmission
% Echelle de temps
echelle_temporelle = linspace(0, Ts, length(reponse_impulsionnelle_globale));

subplot(3,1,2)
plot(echelle_temporelle, reponse_impulsionnelle_globale);
n0 = 3;
%for i = 0:2
%    xline(n0);

%end
title('Réponse impulsionnelle globale de la chaine de transmission');
xlabel('Temps');
ylabel('Réponse impulsionnelle');

% Tracé du signal en sortie du filtre de réception
% Echelle de temps
echelle_temporelle = linspace(0, Ts, Ne);

subplot(3,1,3)
plot(echelle_temporelle, demodulation_signal);
title('Signal en sortie du filtre de réception');
xlabel('Temps');
ylabel('Signal');

%% Tracé du diagramme de l'oeil
% Création du diagramme de l'oeil
diagramme_oeil = reshape(demodulation_signal, Ns, length(demodulation_signal) / Ns);

figure
plot(diagramme_oeil);
title("Diagramme de l'oeil en sortie du filtre de réception");
xlabel('Temps');
ylabel('Signal');


%% Détecteur à seuil (n0 = 8)
n0 = 8;
signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal)); % Ns = 8
classes = signaldem_ech > 0;

% Taux d'erreur binaire
TEB_optimal = sum(classes ~= bits) / NbBits


%% Détecteur à seuil (n0 = 3)
n0 = 5;
signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal)); % Ns = 8
classes = signaldem_ech > 0;

% Taux d'erreur binaire
TEB_nonoptimal = sum(classes ~= bits) / NbBits


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Étude de l'impact d'un canal à bruit additif, blanc et Gaussien - Efficacité en puissance %%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Nombre de bits
NbBits = 1000;

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
demodulation_signal = filter(h, 1, modulation_signal);

% Tracé du diagramme de l'oeil
% Création du diagramme de l'oeil
diagramme_oeil = reshape(demodulation_signal, Ns, length(demodulation_signal) / Ns);

figure
plot(diagramme_oeil);
title("Diagramme de l'oeil de la chaine 1");
xlabel('Temps');
ylabel('Signal');

% Détecteur à seuil
n0 = 8;
signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal)); % Ns = 8
classes = signaldem_ech > 0;

% Taux d'erreur binaire
TEB_chaine1_opt = sum(classes ~= bits) / NbBits

% Ajout du bruit
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

    if i == 1
        subplot(1,2,1);
        plot(diagramme_oeil);
        title("Diagramme de l'oeil de la chaine 1 avec Eb/N0 = 1");
        xlabel('Temps');
        ylabel('Signal');
    elseif i == 8
        subplot(1,2,2);
        plot(diagramme_oeil);
        title("Diagramme de l'oeil de la chaine 1 avec Eb/N0 = 8");
        xlabel('Temps');
        ylabel('Signal');
    end

end

figure
semilogy(EbN0dB, TEB_theorique,'g','LineWidth',3)
hold on
semilogy(EbN0dB, TEB_chaine1,'r^','LineWidth',3,'MarkerSize',10);
legend("TEB théorique","TEB empirique");
title("Tracé des TEB pour la chaine 1");
xlabel('E_b/N_0(dB)')
grid


%% Chaine 2
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

% Définition du filtre h (mise en forme)
h = ones(1, Ns);    % car hauteur de la porte = 1

% Définition du filtre h (réception)
hr = ones(1, Ns/2);    % car hauteur de la porte = 1

% Filtrage
modulation_signal = filter(h, 1, peigneDirac);
demodulation_signal = filter(hr, 1, modulation_signal);

% Tracé du diagramme de l'oeil
% Création du diagramme de l'oeil
diagramme_oeil = reshape(demodulation_signal, Ns, length(demodulation_signal) / Ns);

figure
plot(diagramme_oeil);
title("Diagramme de l'oeil de la chaine 2");
xlabel('Temps');
ylabel('Signal');

% Détecteur à seuil
n0 = 6;
signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal)); % Ns = 8
classes = signaldem_ech > 0;

% Taux d'erreur binaire
TEB_chaine2_opt = sum(classes ~= bits) / NbBits

% Ajout du bruit
EbN0dB=[1:8];
EbN0=10.^(EbN0dB./10);
TEB_theorique = qfunc(sqrt(2 * EbN0));
TEB_chaine2 = zeros(1,length(EbN0dB));

for k = 1:length(EbN0dB)
    
    Px = mean(abs(modulation_signal).^2);
    sigma_n = sqrt((Px*Ns) / (2*log2(M) * EbN0(k) ));
    bruit = sigma_n * randn(1, length(modulation_signal));

    signal_canal = modulation_signal + bruit;

    demodulation_signal = filter(hr, 1, signal_canal);

    diagramme_oeil = reshape(demodulation_signal, Ns, length(demodulation_signal) / Ns);
    
    % Détecteur à seuil
    n0 = 8;
    signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal)); % Ns = 8
    classes = signaldem_ech > 0;
    
    % Taux d'erreur binaire
    TEB_chaine2(k) = sum(classes ~= bits) / NbBits;

    if i == 1
        subplot(1,2,1);
        plot(diagramme_oeil);
        title("Diagramme de l'oeil de la chaine 1 avec Eb/N0 = 1");
        xlabel('Temps');
        ylabel('Signal');
    elseif i == 8
        subplot(1,2,2);
        plot(diagramme_oeil);
        title("Diagramme de l'oeil de la chaine 1 avec Eb/N0 = 8");
        xlabel('Temps');
        ylabel('Signal');
    end

end

figure
semilogy(EbN0dB, TEB_theorique,'g','LineWidth',3)
hold on
semilogy(EbN0dB, TEB_chaine2,'r^','LineWidth',3,'MarkerSize',10);
legend("TEB théorique","TEB empirique");
title("Tracé des TEB pour la chaine 2");
xlabel('E_b/N_0(dB)')
grid


%% Chaine 3
% Paramètres du modulateur
M = 4;                      % 2^2 : ordre du modulateur
Rs = Rb / log2(M);  
Ts = 1 / Rs;
Ns = floor(Ts / Te);        % Nombre d'échantillons sur une période symbole
Nbs = NbBits / log2(M);
Ne = Nbs * Ns;              % Nombre d'échantillons

% Transformer la séquence de bits en séquence de symboles
entiers = bi2de(reshape(bits, NbBits/log2(M), log2(M)));
symboles = qammod(entiers, M);

% Calculer le peigne de Dirac associé à notre séquence de symboles
peigneDirac = kron(symboles.', [1 zeros(1, Ns-1)]);

% Définition du filtre h (mise en forme et réception)
h = ones(1, Ns);    % car hauteur de la porte = 1

% Filtrage
modulation_signal = filter(h, 1, peigneDirac);
demodulation_signal = filter(h, 1, modulation_signal);
reponse_impulsionnelle_globale = conv(h, h);

% Tracé du diagramme de l'oeil
% Création du diagramme de l'oeil
% diagramme_oeil = reshape(demodulation_signal, Ns, length(demodulation_signal) / Ns);
diagramme_oeil = eyediagram(demodulation_signal, 2*Ns, 2*Ns);

% Détecteur à seuil
n0 = 16;
signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal));
symboles_estimes = qamdemod(signaldem_ech, M);
entiers_estimes = de2bi(symboles_estimes);

cpt_TEB = 0;
for i = 1:length(entiers)
    if entiers(i) ~= symboles_estimes(i)
        cpt_TEB = cpt_TEB + 1;
    end
end

% Taux d'erreur binaire
TEB_chaine3_opt = cpt_TEB / length(entiers)

% Ajout du bruit
EbN0dB=[1:8];
EbN0=10.^(EbN0dB./10);
TEB_theorique = (2/log2(M)) * ((M-1) / M) * qfunc(sqrt(((6*log2(M)) / (M^2 - 1)) * EbN0));
TEB_chaine3 = zeros(1,length(EbN0dB));

for k = 1:length(EbN0dB)
    
    Px = mean(abs(modulation_signal).^2);
    sigma_n = sqrt((Px*Ns) / (2*log2(M) * EbN0(k) ));
    bruit = sigma_n * randn(1, length(modulation_signal));

    signal_canal = modulation_signal + bruit;

    demodulation_signal = filter(h, 1, signal_canal);


    % Détecteur à seuil
    n0 = 16;
    signaldem_ech = demodulation_signal(n0:Ns:length(demodulation_signal));
    symboles_estimes = qamdemod(signaldem_ech, M);
    entiers_estimes = de2bi(symboles_estimes);
    
    cpt_TEB = 0;
    for j = 1:length(entiers)
        if entiers(j) ~= symboles_estimes(j)
            cpt_TEB = cpt_TEB + 1;
        end
    end

    % Taux d'erreur binaire
    TEB_chaine3(k) = cpt_TEB / length(entiers);

    if k == 1 || k == 8
        diagramme_oeil = eyediagram(demodulation_signal, 2*Ns, 2*Ns);
    end

end

figure
semilogy(EbN0dB, TEB_theorique,'g','LineWidth',3)
hold on
semilogy(EbN0dB, TEB_chaine3,'r^','LineWidth',3,'MarkerSize',10);
legend("TEB théorique","TEB empirique");
title("Tracé des TEB pour la chaine 3");
xlabel('E_b/N_0(dB)')
grid
