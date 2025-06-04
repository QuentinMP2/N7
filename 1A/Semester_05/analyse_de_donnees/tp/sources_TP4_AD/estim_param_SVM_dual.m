% fonction estim_param_SVM_dual (pour l'exercice 1)

function [X_VS,w,c,code_retour] = estim_param_SVM_dual(X,Y)
    lb = zeros(size(Y));
    beq = 0;
    Aeq = Y';
    f = -ones(size(Y));
    H = (X.*Y) * (X.*Y)';
    [X_VS, ~, code_retour] = quadprog(H,f,[],[],Aeq,beq,lb,[]);
    
    w = sum(diag(X_VS .* Y)*X);
    w=w';
    X_VS_i = X(X_VS > 0.000001,:);
    Y_VS_i = Y(X_VS > 0.000001);
    c = w'*X_VS_i(1,:)'-Y_VS_i(1);

end
