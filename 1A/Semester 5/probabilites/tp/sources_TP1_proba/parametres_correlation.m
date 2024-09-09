% Fonction parametres_correlation (exercice_1.m)

function [r,a,b] = parametres_correlation(Vd,Vg)
    Vd2 = Vd .* Vd;
    Vg2 = Vg .* Vg;
    
    m_Vd = mean(Vd);
    m_Vg = mean(Vg);
    
    m_Vd2 = m_Vd .* m_Vd;
    m_Vg2 = m_Vg .* m_Vg;
    
    c_sum_Vd = Vd2 - m_Vd2;
    c_sum_Vg = Vg2 - m_Vg2;
    
    v_Vd = 1/(size(Vd,1)) * sum(c_sum_Vd);
    v_Vg = 1/(size(Vg,1)) * sum(c_sum_Vg);

    ec_t_Vd = sqrt(v_Vd);
    ec_t_Vg = sqrt(v_Vg);

    c_sum_c = Vd .* Vg - m_Vd .* m_Vg;
    c_Vd_Vg = 1/(size(Vd,1)) * sum(c_sum_c);

    r = c_Vd_Vg/(ec_t_Vd*ec_t_Vg);
    a = c_Vd_Vg/v_Vd;
    b = m_Vg - a*m_Vd;


end