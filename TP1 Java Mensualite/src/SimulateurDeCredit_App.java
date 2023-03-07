import DAO.IDao;
import DAO.CreditDao;
import lombok.var;
import METIER.CreditMetier;
import METIER.ICreditMetier;
import MODEL.Credit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import PRESENTATION.CreditControleur;
import PRESENTATION.ICreditControleur;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Scanner;

public class SimulateurDeCredit_App {
    static Scanner clavier = new Scanner(System.in);
    static ICreditControleur AY_creditControleur;

    private static boolean estUnNombre(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e ){
            return false;
        }
    }

    public static void test1(){
        // instanciation des différents compsants de l'application
        var dao = new CreditDao();
        var metier = new CreditMetier();
        var controleur = new CreditControleur();
        // injection des dépendances
        metier.setCreditDao(dao);
        controleur.setCreditControleur(metier);
        // tester
        String rep = "";
        do {
            System.out.println("=> [Test 1] calcule de Mensualité de crédit <= \n");
            try {
                String input = "";
                while (true){
                    System.out.println("Saisir l'ID du crédit : ");
                    input = clavier.nextLine();
                    if (estUnNombre(input)) break;
                    System.err.println("L'ID doit être un nombre");
                }
                long id = Long.parseLong(input);
                controleur.afficher_Mensualite(id);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Voulez vous continuer ? [oui/non]");
            rep = clavier.nextLine();
        }while (!rep.equalsIgnoreCase("oui"));
        System.out.println("Fin du test 1");
    }
    public static void test2() throws Exception {

        String daoClass;
        String serviceClass;
        String controllerClass;

        Properties properties = new Properties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream("config.properties");

        if (propertiesFile == null) throw new Exception("fichier config introuvable");
        else {
            try {
                properties.load(propertiesFile);
                daoClass        = properties.getProperty("DAO");
                serviceClass    = properties.getProperty("SERVICE");
                controllerClass = properties.getProperty("CONTROLLER");
                propertiesFile.close();
            }
            catch (IOException e){
                throw new Exception("Problème de chargement des propriétés du fichier config");
            }
            finally {
                properties.clear();
            }
        }
        try {
            Class cDao          = Class.forName(daoClass);
            Class cMetier       = Class.forName(serviceClass);
            Class cController   = Class.forName(controllerClass);

            var dao = (IDao<Credit, Long>) cDao.getDeclaredConstructor().newInstance();
            var metier = (ICreditMetier) cMetier.getDeclaredConstructor().newInstance();
            AY_creditControleur    = (ICreditControleur) cController.getDeclaredConstructor().newInstance();

            Method setDao       = cMetier.getMethod("setCreditDao", IDao.class);
            setDao.invoke(metier,dao);

            Method setMetier    = cController.getMethod("setCreditMetier", ICreditMetier.class);
            setMetier.invoke(AY_creditControleur,metier);

            AY_creditControleur.afficher_Mensualite(1L);
        }
        catch (Exception e ){
            e.printStackTrace();
        }
    }
    public static void test3() throws Exception{
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-ioc.xml");
            AY_creditControleur = (ICreditControleur) context.getBean("controleur");
            AY_creditControleur.afficher_Mensualite(1L);
    }

    public static void test4() throws Exception{
        ApplicationContext context = new AnnotationConfigApplicationContext("DAO", "METIER", "PRESENTATION");
        AY_creditControleur = (ICreditControleur) context.getBean(ICreditControleur.class);
        AY_creditControleur.afficher_Mensualite(1L);
    }

    public static void main(String[] args) throws Exception {
        test4();
    }
}