% Fonction ecriture_RVB

function image_RVB = ecriture_RVB(image_originale)

canal_R = image_originale(1:2:(size(image_originale,1)), 2:2:(size(image_originale,2)));
canal_B = image_originale(2:2:(size(image_originale,1)), 1:2:(size(image_originale,2)));
canal_V1 = image_originale(1:2:(size(image_originale,1)), 1:2:(size(image_originale,2)));
canal_V2 = image_originale(2:2:(size(image_originale,1)), 2:2:(size(image_originale,2)));
canal_V = (canal_V1 + canal_V2)/2;
image_RVB = cat(3,canal_R,canal_V,canal_B)

end