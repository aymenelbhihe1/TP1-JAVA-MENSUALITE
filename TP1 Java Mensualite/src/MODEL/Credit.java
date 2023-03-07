package MODEL;


import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class Credit {
//AYMEN ELBHIHE 4 IIR 9

    private Long id;
    private Double capitale_Emprunt;
    private Integer nombre_Mois;
    private Double taux_Mensuel;
    private String nom_Demandeur;
    private Double mensualite;

    @Override
    public String toString(){
        String creditStr = "Credit [id = " + id + ", capitale_Emprunt = " + capitale_Emprunt + ", nombre_Mois = " + nombre_Mois + ", taux_Mensuel = " + taux_Mensuel + ", nom_Demandeur = " + nom_Demandeur + ", mensualite = " + mensualite + "]";


        return creditStr;
    }
}
