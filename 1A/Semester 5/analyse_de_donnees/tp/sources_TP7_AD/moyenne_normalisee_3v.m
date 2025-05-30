% fonction moyenne_normalisee_3v (pour l'exercice 1bis)

function x = moyenne_normalisee_3v(I)

    centre = [round(size(I,1)/2), round(size(I,2)/2)];
    delta = round(0.1*centre);
    I2 = I((centre(1)-delta(1)):(centre(1)+delta(1)), (centre(2)-delta(2)):(centre(2)+delta(2)));

    x0 = moyenne_normalisee_2v(I);
    x2 = moyenne_normalisee_2v(I2);
    
    x = [x2 x0(1)];


end
