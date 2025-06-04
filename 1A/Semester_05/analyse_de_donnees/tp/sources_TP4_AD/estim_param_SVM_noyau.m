% fonction estim_param_SVM_noyau (pour l'exercice 2)

function [X_VS,Y_VS,Alpha_VS,c,code_retour] = estim_param_SVM_noyau(X,Y,sigma)
    n = length(X);
    K = zeros(n,n);
    for i = 1:n
        for j = 1:n
            K(i,j) = exp(-((norm(X(i,:)-X(j,:)))^2) / (2*sigma^2));
        end
    end

    lb = zeros(size(Y));
    beq = 0;
    Aeq = Y';
    f = -ones(size(Y));
    H = diag(Y) * K * diag(Y);
    [Alpha, ~, code_retour] = quadprog(H,f,[],[],Aeq,beq,lb,[]);

    Alpha_VS = Alpha(Alpha > 0.000001);
    X_VS = X(Alpha > 0.000001,:);
    Y_VS = Y(Alpha > 0.000001);

    c = 0;
    for j = 1:n
        c = c + Alpha(j)*Y(j)*K(X(j,:),X_VS(1,:));
    end
    c = c - Y_VS(1);

end
