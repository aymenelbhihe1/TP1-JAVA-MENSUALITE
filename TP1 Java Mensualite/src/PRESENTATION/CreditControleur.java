package PRESENTATION;
import lombok.*;
import METIER.ICreditMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
//AYMEN ELBHIHE 4 IIR 9
@Data @AllArgsConstructor @NoArgsConstructor
@Controller
public class CreditControleur implements ICreditControleur{
    @Autowired
    @Qualifier("metier")
    ICreditMetier creditControleur;
    @Override
    public void afficher_Mensualite(Long id) throws Exception {
        var creditAvecMensualite = creditControleur.calculer_Mensualite(id);
        System.out.println(creditAvecMensualite);
    }
}
