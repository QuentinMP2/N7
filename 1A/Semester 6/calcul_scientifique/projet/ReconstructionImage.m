%%  Application de la SVD : compression d'images

clear all
close all

% Lecture de l'image
I = imread('BD_Asterix_1.png');
I = rgb2gray(I);
I = double(I);

[q, p] = size(I);

% Décomposition par SVD
fprintf('Décomposition en valeurs singulières\n')
tic
[U, S, V] = svd(I);
toc

l = min(p,q);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% On choisit de ne considérer que 200 vecteurs
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% vecteur pour stocker la différence entre l'image et l'image reconstuite
inter = 1:40:(200+40);
inter(end) = 200;
differenceSVD = zeros(size(inter,2), 1);

% images reconstruites en utilisant de 1 à 200 vecteurs (avec un pas de 40)
ti = 0;
td = 0;
for k = inter

    % Calcul de l'image de rang k
    Im_k = U(:, 1:k)*S(1:k, 1:k)*V(:, 1:k)';

    % Affichage de l'image reconstruite
    ti = ti+1;
    figure(ti)
    colormap('gray')
    imagesc(Im_k), axis equal
    
    % Calcul de la différence entre les 2 images
    td = td + 1;
    differenceSVD(td) = sqrt(sum(sum((I-Im_k).^2)));
    pause
end

% Figure des différences entre image réelle et image reconstruite
ti = ti+1;
figure(ti)
hold on 
plot(inter, differenceSVD, 'rx')
ylabel('RMSE')
xlabel('rank k')
pause


% Plugger les différentes méthodes : eig, puissance itérée et les 4 versions de la "subspace iteration method" 

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% QUELQUES VALEURS PAR DÉFAUT DE PARAMÈTRES, 
% VALEURS QUE VOUS POUVEZ/DEVEZ FAIRE ÉVOLUER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% tolérance
eps = 1e-8;
% nombre d'itérations max pour atteindre la convergence
maxit = 10000;

% taille de l'espace de recherche (m)
search_space = 400;

% pourcentage que l'on se fixe
percentage = 0.995;

% p pour les versions 2 et 3 (attention p déjà utilisé comme taille)
puiss = 1;

% Version algorithme
% 10 : eig
% 11 : power_v11
% 12 : power_v12
% 0 : subspace_iter_v0
% 1 : subspace_iter_v1
% 2 : subspace_iter_v2
% 3 : subspace_iter_v3
v = 10;

%%%%%%%%%%%%%
% À COMPLÉTER
%%%%%%%%%%%%%

%%
% calcul des couples propres
n_ev = 240;
if v == 10
    [V2, D2] = eig(I'*I);
    D2 = diag(D2);
    [D2, perm] = sort(D2, 'descend');
    D2 = diag(D2);
    V2 = V2(:,perm);
elseif v == 11
    [V2, D2, n_ev, ~, ~] = power_v11(I'*I, search_space, percentage, eps, maxit);
elseif v == 12
    [V2, D2,n_ev, ~, ~] = power_v12(I'*I, search_space, percentage, eps, maxit);
elseif v == 0
    [V2, D2, ~, ~] = subspace_iter_v0(I'*I, search_space, eps, maxit);
elseif v == 1
    [V2, D2, n_ev, ~, ~, ~] = subspace_iter_v1(I'*I, search_space, percentage, eps, maxit);
elseif v == 2
    [V2, D2, n_ev, ~, ~, ~] = subspace_iter_v2(I'*I, search_space, percentage, puiss, eps, maxit);
elseif v == 3
    [V2, D2, n_ev, ~, ~, ~] = subspace_iter_v3(I'*I, search_space, percentage, puiss, eps, maxit);
end

%%
% calcul des valeurs singulières
S2 = sqrt(D2);

%%
% calcul de l'autre ensemble de vecteurs
k = 200;
U2 = zeros(q,k);
for i = 1:k
    U2(:,i) = (1/S2(i,i))*I*V2(:,i);
end    

%%
% calcul des meilleures approximations de rang faible

A = zeros(q,p);
% for i = 1:k
%     A = A + U2(:,i)*S2(i,i)*transpose(V2(:,i));
% end
% 
% figure
% colormap('gray')
% imagesc(A), axis equal

% vecteur pour stocker la différence entre l'image et l'image reconstuite
inter = 1:40:(200+40);
inter(inter <= n_ev);
inter(end) = 200;
differenceEIG = zeros(size(inter,2), 1);

% images reconstruites en utilisant de 1 à 200 vecteurs (avec un pas de 40)
ti = 0;
td = 0;
for k = inter

    % Calcul de l'image de rang k
    A = A + U2(:,i)*S2(i,i)*transpose(V2(:,i));

    % Affichage de l'image reconstruite
    ti = ti+1;
    figure(ti)
    colormap('gray')
    imagesc(A), axis equal

    % Calcul de la différence entre les 2 images
    td = td + 1;
    differenceEIG(td) = sqrt(sum(sum((I-A).^2)));
    pause
end

% Figure des différences entre image réelle et image reconstruite
ti = ti+1;
figure(ti)
hold on 
plot(inter, differenceEIG, 'rx')
ylabel('RMSE')
xlabel('rank k')
pause