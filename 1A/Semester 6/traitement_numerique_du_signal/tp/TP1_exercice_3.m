%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%               TP1 de Traitement Numérique du Signal
%                   SCIENCES DU NUMERIQUE 1A
%                       Fevrier 2024 
%                        Prénom Nom
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

clear all
close all

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% PARAMETRES GENERAUX
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
A = 1;                    % Amplitude du cosinus
f0 = 1100;                % Fréquence du cosinus en Hz
T0 = 1/f0;                % Période du cosinus en secondes
N = 90;                   % Nombre d'échantillons souhaités pour le cosinus
Fe = 10000;               % Fréquence d'échantillonnage en Hz
Te = 1/Fe;                % Période d'échantillonnage en secondes
SNR = 10000;                % SNR souhaité en dB pour le cosinus bruité


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% GENERATION DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Définition de l'échelle temporelle
temps = 0:Te:(N-1)*Te;
% Génération de N échantillons de cosinus à la fréquence f0
x=A*cos(2*pi*f0*temps);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TRACE DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Sans se préoccuper de l'échelle temporelle
figure
plot(x)

% Tracé avec une échelle temporelle en secondes
% des labels sur les axes et un titre (utilisation de xlabel, ylabel et
% title)
figure
plot(temps, x);
grid
xlabel('Temps (s)')
ylabel('signal')
title(['Tracé d''un cosinus numérique de fréquence ' num2str(f0) 'Hz']);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CALCUL DE LA TRANSFORMEE DE FOURIER NUMERIQUE (TFD) DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Sans zero padding 
X=fft(x);
% Avec zero padding (ZP : paramètre de zero padding à définir)
nfft = 8192;
X_ZP=fft(x, nfft);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TRACE DU MODULE DE LA TFD DU COSINUS NUMERIQUE EN ECHELLE LOG
% SANS PUIS AVEC ZERO PADDING
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Avec une échelle fréquentielle en Hz
% des labels sur les axes et des titres
% Tracés en utilisant plusieurs zones de la figure (utilisation de subplot) 
figure('name',['Tracé du module de la TFD d''un cosinus numérique de fréquence ' num2str(f0) 'Hz'])

subplot(2,1,1)
echelle_frequentielle = 0:Fe/(N-1):Fe;
semilogy(echelle_frequentielle, abs(X));
grid
title('Sans zero padding')
xlabel('Fréquence (Hz)')
ylabel('|TFD|')

subplot(2,1,2)
echelle_frequentielle = 0:Fe/(nfft-1):Fe;
semilogy(echelle_frequentielle, abs(X_ZP));
grid
title('Avec zero padding')
xlabel('Fréquence (Hz)')
ylabel('|TFD|')

% Avec une échelle fréquentielle en Hz
% des labels sur les axes et des titres
% Tracés superposés sur une même figure 
% (utilisation de hold, de couleurs différentes et de legend)
% !! UTILISER ICI fftshit POUR LE TRACE !!
figure
echelle_frequentielle = -Fe/2:Fe/(N-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(X)), 'b');    %Tracé en bleu : 'b'
hold on
echelle_frequentielle = -Fe/2:Fe/(nfft-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(X_ZP)), 'r'); %Tracé en rouge : 'r'
grid
legend('Sans zero padding','Avec zero padding')
xlabel('Fréquence (Hz)')
ylabel('|TFD|')
title(['Tracé du module de la TFD d''un cosinus numérique de fréquence ' num2str(f0) 'Hz'])

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CALCUL DE LA TFD DU COSINUS NUMERIQUE AVEC FENETRAGE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Application de la fenêtre de pondération de Hamming
x_fenetre_hamming = x.*hamming(length(x)).';
% Calcul de la TFD pondérée, avec zeros padding
X_ZP_hamming = fft(x_fenetre_hamming, nfft);
% Application de la fenêtre de pondération de Blackman
x_fenetre_blackman = x.*blackman(length(x)).';
% Calcul de la TFD pondérée, avec zeros padding
X_ZP_blackman = fft(x_fenetre_blackman, nfft);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TRACE DU MODULE DE LA TFD DU COSINUS NUMERIQUE AVEC FENETRAGE EN ECHELLE LOG
% POUR DIFFERENTES FENETRES
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
figure
echelle_frequentielle = -Fe/2:Fe/(nfft-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(X_ZP)), 'b');         % Tracé en bleu : 'b'
hold on
semilogy(echelle_frequentielle, fftshift(abs(X_ZP_hamming)), 'r'); % Tracé en rouge : 'r'
hold on
semilogy(echelle_frequentielle, fftshift(abs(X_ZP_blackman)), 'g');% Tracé en rouge : 'g'
hold on
grid
legend('Non fenetré','Fenetre de Hamming', 'Fenetre de Blackman')
xlabel('Fréquence (Hz)')
ylabel('|TFD|')
title(['Tracé du module de la TFD d''un cosinus numérique de fréquence ' num2str(f0) 'Hz'])

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CALCUL DE LA DENSITE SPECTRALE DE PUISSANCE (DSP) DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Estimation par périodogramme
Sx_periodogramme = 1/length(X_ZP) * abs(X_ZP).^2;

% Estimation par périodogramme de Welch
Sx_Welch = pwelch(x, [],[],[],Fe,'twosided');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TRACE DE LA DENSITE SPECTRALE DE PUISSANCE (DSP) DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Tracés superposés sur une même figure en utilisant fftshift
% Avec une échelle fréquentielle en Hz
% des labels sur les axes, un titre, une légende
figure
echelle_frequentielle = -Fe/2:Fe/(nfft-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(Sx_periodogramme)), 'b')
hold on
echelle_frequentielle = linspace(-Fe/2, Fe/2, length(Sx_Welch));
semilogy(echelle_frequentielle, fftshift(abs(Sx_Welch)), 'r')
legend('Periodogramme', 'Periodogramme de Welch')
xlabel('Fréquences (Hz)')
ylabel('DSP')
title('Tracés de la DSP d''un cosinus numérique de fréquence 1100 Hz');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CALCUL ET TRACE DE LA FONCTION D'AUTOCORRELATION DU COSINUS BRUITE AVEC LE
% BON SNR
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%       

% Calcul de la puissance du signal
P_signal = mean(abs(x).^2);
% Calcul de la puissance du bruit pour obtenir le SNR souhaité
P_bruit = P_signal/SNR;

% Génération du bruit gaussien à la puissance Pb
bruit = sqrt(P_bruit)*randn(1,length(x));
% Ajout du bruit sur le signal
x_bruite = x+bruit;

% Calcul de la fonction d'autocorrélation du signal bruite
% attention pas le 1/N quand on utilise xcorr => à rajouter
Rx = (1/length(x_bruite))*xcorr(x_bruite); 

% Tracé du signal bruité avec une échelle temporelle en secondes
figure
plot(temps, bruit)
grid
xlabel('Temps (s)')
ylabel('Signal')
title('Tracé du cosinus bruité');

% Tracé de la fonction d'autocorrélation du signal bruite avec une échelle 
% temporelle en secondes
figure
echelle_tau = linspace(0, (N-1)*Fe, length(Rx));
plot(echelle_tau, Rx)
grid
xlabel('\tau (s)')
ylabel('R_x(\tau)')
title('Tracé de la fonction d''autocorrélation du cosinus bruité');