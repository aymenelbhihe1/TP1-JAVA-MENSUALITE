package METIER;

import DAO.IDao;

import lombok.*;
import MODEL.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
//AYMEN ELBHIHE 4 IIR 9
@Data @AllArgsConstructor @NoArgsConstructor
@Service("METIER")
public class CreditMetier implements ICreditMetier{
    @Autowired
    @Qualifier("dao")
    IDao<Credit,Long> creditDao;
    @Override
    public Credit calculer_Mensualite(Long id) throws Exception{
        var credit = creditDao.trouverParID(id);

        if (credit == null)
        {
            throw new Exception("L'id du credit est incorrecte :: [Credit non trouve]");
        }
        else {
            double  taux         = credit.getTaux_Mensuel();
                    taux         = taux/1200;
            double  capitale     = credit.getCapitale_Emprunt();
            int     nbr_mois     = credit.getNombre_Mois();

            double  mensualite   = (capitale * taux) / (1 - (Math.pow((1 + taux), -1 * nbr_mois)));
                    mensualite   = Math.round(mensualite*100)/100;

                   credit.setMensualite(mensualite);

            return credit;
        }
    }
}
