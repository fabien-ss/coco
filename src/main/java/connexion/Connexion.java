package connexion;

import utils.Mapping;
import utils.TableUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Connexion {

    String url = "jdbc:postgresql://localhost:5432/";
    String database = "port";
    String utilisateur = "fabien";
    String motDePasse = "123Fabien$";
    String driver = "org.postgresql.Driver";
    HashMap<String, String> conf;

    public Connection enterBdd() throws SQLException, ClassNotFoundException{
        Class.forName(conf.get("driver"));
        return DriverManager.getConnection(conf.get("url"), conf.get("user"), conf.get("password"));
    }

    public Connexion() throws Exception{
        this.conf = databaseConf();
    }
    
    public Connexion(String database, String utilisateur, String motDePasse) throws Exception
    { 
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
        this.conf = databaseConf();
    }

    public HashMap<String, String> databaseConf() throws Exception{
        String path = System.getProperty("user.dir") + "/database.conf";
        String data = TableUtility.chargerModele(path);
        HashMap<String, String> conf = Mapping.fetchData(data);
        return conf;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
       
}
