% fonction maximisation_classification_SVM_noyau (pour l'exercice 2)

function [pourcentage_meilleur_classification_SVM_test, sigma_opt_test, ...
          vecteur_pourcentages_bonnes_classifications_SVM_app, ...
          vecteur_pourcentages_bonnes_classifications_SVM_test] = ...
          maximisation_classification_SVM_noyau(X_app,Y_app,X_test,Y_test,vecteur_sigma)

    vecteur_pourcentages_bonnes_classifications_SVM_app = zeros(length(Y_app));
    vecteur_pourcentages_bonnes_classifications_SVM_test = zeros(length(Y_test));

    for i = 1:length(vecteur_sigma)
        [X_VS,Y_VS,Alpha_VS,c,~] = estim_param_SVM_noyau(X_test,Y_test,vecteur_sigma(i));
        Y_pred_test = classification_SVM_avec_noyau(X_test,vecteur_sigma(i),X_VS,Y_VS,Alpha_VS,c);
    
        pourcentage_meilleur_classification_SVM_test = sum(Y_pred_test == Y_test) * 100;
        vecteur_pourcentages_bonnes_classifications_SVM_test(i) = sum(Y_pred_test == Y_test) * 100;


        [X_VS,Y_VS,Alpha_VS,c,~] = estim_param_SVM_noyau(X_app,Y_app,vecteur_sigma(i));
        Y_pred_app = classification_SVM_avec_noyau(X_app,vecteur_sigma(i),X_VS,Y_VS,Alpha_VS,c);

        vecteur_pourcentages_bonnes_classifications_SVM_app(i) = sum(Y_pred_app == Y_app) * 100;
    
    end

    sigma_opt_test_value = max(vecteur_pourcentages_bonnes_classifications_SVM_test);

end