package relation;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import configuration.JsonToHashMap;
import utils.TableUtility;

public class Colonne {
    ResultSet key;
    String name;
    String type;
    boolean primaryKey = false;
    boolean foreignKey = false;

    public String buildStringAttributes(String language, String framework){
        String attributString = "";
        attributString = attributString.concat(primaryKeyFieldAnnotation(language, framework));
        attributString = attributString.concat(foreignKeyFieldAnnotation(language, framework));
        attributString = attributString.concat(columnString(language, framework));
        return attributString;
    }

    public String columnString(String language, String framework){
        String columnAnnotation = ((HashMap<String, HashMap<String, Object>>) JsonToHashMap.attributesConfiguration.get(language)).get(framework).get("column").toString();
        columnAnnotation = columnAnnotation.replace("?", this.name);
        String type  =( (HashMap<String, HashMap<String, Object>>) JsonToHashMap.fieldEquivalence.get(language)).get(this.getType()).get("type").toString();
        return columnAnnotation + " \n" + type + " " + TableUtility.toJavaFormat(this.name) + "; \n";
    }

    public String importString(String language, String framework, String importMethod){
        String typeClass = ((HashMap<String, HashMap<String, Object>>) JsonToHashMap.fieldEquivalence.get(language)).get(this.getType()).get("class").toString();
        return importMethod+" "+ typeClass + "\n";
    }

    public String getterSetterString(String language, String framework){
        String template =((HashMap<String, Object>) JsonToHashMap.configuration.get("encapsulation")).get(language).toString();
        String type  =( (HashMap<String, HashMap<String, Object>>) JsonToHashMap.fieldEquivalence.get(language)).get(this.getType()).get("type").toString();
        template = template.replace("t?", type);
        template = template.replace("#?", TableUtility.firtLetterToUpper(TableUtility.toJavaFormat(this.name)));
        template = template.replace("?", TableUtility.toJavaFormat(this.name));
        return template;
    }

    public String foreignKeyFieldAnnotation(String language, String framework){
        if(foreignKey) return ((HashMap<String, HashMap<String, Object>>) JsonToHashMap.attributesConfiguration.get(language)).get(framework).get("foreignkey").toString().concat("\n");
        return "";
    }
    public String primaryKeyFieldAnnotation(String language, String framework){
        if(primaryKey) return ((HashMap<String, HashMap<String, Object>>) JsonToHashMap.attributesConfiguration.get(language)).get(framework).get("primarykey").toString().concat("\n");
        return "";
    }
    public Colonne(ResultSet resultSet, DatabaseMetaData metaData, String tableName) throws Exception {
        this.setName(resultSet.getString("COLUMN_NAME"));
        this.setType(resultSet.getString("TYPE_NAME").toLowerCase());
        setForeignKey(tableName, metaData);
        setPrimaryKey(tableName, metaData);
    }

    public void setForeignKey(String tableName, DatabaseMetaData databaseMetaData) throws SQLException {
        ResultSet resultSet = databaseMetaData.getImportedKeys(tableName, null, this.name);
        while(resultSet.next()){
            if(this.name.equals(resultSet.getString("FKCOLUMN_NAME"))) foreignKey = true;
        }
    }
    public void setPrimaryKey(String tableName, DatabaseMetaData databaseMetaData) throws SQLException {
        ResultSet resultSet = databaseMetaData.getImportedKeys(tableName, null, this.name);
        while(resultSet.next()){
            if(this.name.equals(resultSet.getString("COLUMN_NAME"))) primaryKey = true;
        }
    }

    public void setKey(ResultSet key) {
        this.key = key;
    }

    public ResultSet getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name.equals("") | name == null) throw new Exception("Nom colonne vide");
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws Exception {
        if(type.equals("") | type == null) throw new Exception("Type vide");
        this.type = type;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }
}
