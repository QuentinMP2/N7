%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%               TP1 de Traitement NumÈrique du Signal
%                   SCIENCES DU NUMERIQUE 1A
%                       Fevrier 2024 
%                        PrÈnom Nom
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

clear all
close all

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%PARAMETRES GENERAUX
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
A = 1;                    % Amplitude du cosinus
f1 = 1000;                % FrÈquence du cosinus 1 en Hz
T1 = 1/f1;                % PÈriode du cosinus 1 en secondes
f2 = 3000;                % FrÈquence du cosinus 2 en Hz
T2 = 1/f2;                % PÈriode du cosinus 2 en secondes
N = 100;                  % Nombre d'Èchantillons souhaitÈs pour le cosinus
Fe = 10000;               % FrÈquence d'Èchantillonnage en Hz
Te = 1/Fe;                % PÈriode d'Èchantillonnage en secondes

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%GENERATION DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%DÈfinition de l'Èchelle temporelle
temps = 0:Te:(N-1)*Te;
%GÈnÈration de N Èchantillons de cosinus ‡ la frÈquence f0
x = A*cos(2*pi*f1*temps) + A*cos(2*pi*f2*temps);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%TRACE DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%Sans se prÈoccuper de l'Èchelle temporelle
figure
plot(x)

%TracÈ avec une Èchelle temporelle en secondes
%des labels sur les axes et un titre (utilisation de xlabel, ylabel et
%title)
figure
plot(temps, x);
grid
xlabel('Temps (s)')
ylabel('signal')
title(['TracÈ d''une somme de deux cosinus numÈrique de frÈquence ' num2str(f1) 'Hz et ' num2str(f2) 'Hz']);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CALCUL DE LA TRANSFORMEE DE FOURIER NUMERIQUE (TFD) DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Sans zero padding 
X=fft(x);
% Avec zero padding (ZP : param√®tre de zero padding √† d√©finir)
nfft = 8192;
X_ZP=fft(x, nfft);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TRACE DU MODULE DE LA TFD DU COSINUS NUMERIQUE EN ECHELLE LOG
% SANS PUIS AVEC ZERO PADDING
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Avec une √©chelle fr√©quentielle en Hz
% des labels sur les axes et des titres
% Trac√©s en utilisant plusieurs zones de la figure (utilisation de subplot) 
figure('name',['Trac√© du module de la TFD d''un cosinus num√©rique de fr√©quence ' num2str(f1) 'Hz'])

subplot(2,1,1)
echelle_frequentielle = 0:Fe/(N-1):Fe;
semilogy(echelle_frequentielle, abs(X));
grid
title('Sans zero padding')
xlabel('Fr√©quence (Hz)')
ylabel('|TFD|')

subplot(2,1,2)
echelle_frequentielle = 0:Fe/(nfft-1):Fe;
semilogy(echelle_frequentielle, abs(X_ZP));
grid
title('Avec zero padding')
xlabel('Fr√©quence (Hz)')
ylabel('|TFD|')

% Avec une √©chelle fr√©quentielle en Hz
% des labels sur les axes et des titres
% Trac√©s superpos√©s sur une m√™me figure 
% (utilisation de hold, de couleurs diff√©rentes et de legend)
% !! UTILISER ICI fftshit POUR LE TRACE !!
figure
echelle_frequentielle = -Fe/2:Fe/(N-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(X)), 'b');    %Trac√© en bleu : 'b'
hold on
echelle_frequentielle = -Fe/2:Fe/(nfft-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(X_ZP)), 'r'); %Trac√© en rouge : 'r'
grid
legend('Sans zero padding','Avec zero padding')
xlabel('Fr√©quence (Hz)')
ylabel('|TFD|')
title(['Trac√© du module de la TFD d''un cosinus num√©rique de fr√©quence ' num2str(f1) 'Hz'])

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CALCUL DE LA TFD DU COSINUS NUMERIQUE AVEC FENETRAGE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Application de la fen√™tre de pond√©ration de Hamming
x_fenetre_hamming = x.*hamming(length(x)).';
% Calcul de la TFD pond√©r√©e, avec zeros padding
X_ZP_hamming = fft(x_fenetre_hamming, nfft);
% Application de la fen√™tre de pond√©ration de Blackman
x_fenetre_blackman = x.*blackman(length(x)).';
% Calcul de la TFD pond√©r√©e, avec zeros padding
X_ZP_blackman = fft(x_fenetre_blackman, nfft);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TRACE DU MODULE DE LA TFD DU COSINUS NUMERIQUE AVEC FENETRAGE EN ECHELLE LOG
% POUR DIFFERENTES FENETRES
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
figure
echelle_frequentielle = -Fe/2:Fe/(nfft-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(X_ZP)), 'b');         % Trac√© en bleu : 'b'
hold on
semilogy(echelle_frequentielle, fftshift(abs(X_ZP_hamming)), 'r'); % Trac√© en rouge : 'r'
hold on
semilogy(echelle_frequentielle, fftshift(abs(X_ZP_blackman)), 'g');% Trac√© en rouge : 'g'
hold on
grid
legend('Non fenetr√©','Fenetre de Hamming', 'Fenetre de Blackman')
xlabel('Fr√©quence (Hz)')
ylabel('|TFD|')
title(['Trac√© du module de la TFD d''un cosinus num√©rique de fr√©quence ' num2str(f1) 'Hz'])

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% CALCUL DE LA DENSITE SPECTRALE DE PUISSANCE (DSP) DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Estimation par p√©riodogramme
Sx_periodogramme = 1/length(X_ZP) * abs(X_ZP).^2;

% Estimation par p√©riodogramme de Welch
Sx_Welch = pwelch(x, [],[],[],Fe,'twosided');

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% TRACE DE LA DENSITE SPECTRALE DE PUISSANCE (DSP) DU COSINUS NUMERIQUE
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Trac√©s superpos√©s sur une m√™me figure en utilisant fftshift
% Avec une √©chelle fr√©quentielle en Hz
% des labels sur les axes, un titre, une l√©gende
figure
echelle_frequentielle = -Fe/2:Fe/(nfft-1):Fe/2;
semilogy(echelle_frequentielle, fftshift(abs(Sx_periodogramme)), 'b')
hold on
echelle_frequentielle = linspace(-Fe/2, Fe/2, length(Sx_Welch));
semilogy(echelle_frequentielle, fftshift(abs(Sx_Welch)), 'r')
legend('Periodogramme', 'Periodogramme de Welch')
xlabel('Fr√©quences (Hz)')
ylabel('DSP')
title('Trac√©s de la DSP d''un cosinus num√©rique de fr√©quence 1100 Hz');



n = 11; % ordre du filtre
k = -n:n;
h = 2*(f1+f2)*sinc(2*(f1+f2)*k);
y = filter(h, 1, x);
figure
echelle_frequentielle = linspace(-Fe/2, Fe/2, length(y));
semilogy(echelle_frequentielle, abs(y));