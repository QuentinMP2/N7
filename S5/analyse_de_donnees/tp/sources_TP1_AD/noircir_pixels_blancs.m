% function noircir_pixels_blancs (pour exercice_3.m)

function I_sans_blanc = noircir_pixels_blancs(I)
    R = I(:,:,1);
    V = I(:,:,2);
    B = I(:,:,3);

    pixels_blancs = find(R == 255 & V == 255 & B == 255);

    R(pixels_blancs) = 0;
    V(pixels_blancs) = 0;
    B(pixels_blancs) = 0;

    I_sans_blanc = cat(3, R, V, B);
    
end
