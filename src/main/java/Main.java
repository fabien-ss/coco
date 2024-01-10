import connexion.Connexion;
import relation.Database;
import relation.Table;
import java.sql.Connection;

public class Main {
   public static void main(String[] args) throws Exception {

       Connexion connexion = new Connexion();
       Connection connection = connexion.enterBdd();
       Database database = new Database();
       database.setTableList(connection);

       for (Table t : database.getTableList()) {
           String value = t.buildClassCode("dotnet", "test", "entityframework");
           System.out.println(value);
       }
   } 
}
