% fonction classification_SVM_avec_noyau (pour l'exercice 2)

function Y_pred = classification_SVM_avec_noyau(X,sigma,X_VS,Y_VS,Alpha_VS,c)
    n = length(X);
    Y_pred = zeros(n,1);
    
    K = zeros(n,n);
    for i = 1:n
        for j = 1:n
            K(i,j) = exp(-((norm(X(i,:)-X(j,:)))^2) / (2*sigma^2));
        end
    end


    for i = 1:n
        sum = 0;
        for j = 1:n
            sum = sum + Alpha_VS(j)*Y_VS(j)*K(X(j,:),X_VS(1,:));
        end
        sum = sum - c;
        Y_pred(i) = sign(sum);
    end



end