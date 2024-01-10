package relation;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Database {

    String name;
    List<Table> tableList;

    public void createClasse(String language, String packageName, String framework){
        for (Table t : tableList) {
            String value = t.buildClassCode(language, packageName, framework);
            System.out.println(value);
        }
    }

    public void setTableList(Connection connection) throws Exception {
        tableList = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(this.name, null, "%", new String[]{"TABLE"});
        while(resultSet.next()){
            String tableName = resultSet.getString("TABLE_NAME");
            ResultSet colonnes = metaData.getColumns(this.name, null, tableName, "%");
            Table table = new Table(colonnes, tableName, metaData);
            tableList.add(table);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }
}
