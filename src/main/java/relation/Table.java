package relation;

import utils.TableUtility;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import configuration.JsonToHashMap;

public class Table {
    
    String dataBaseName;
    String name;
    List<Colonne> colonneList;

    public String buildClassCode(String language, String packageName, String framework){
        String template = "".concat(JsonToHashMap.template);
        template = writeSingleton(template,(boolean) ( (HashMap<String, Object>) ( (HashMap<String, Object>) JsonToHashMap.configuration.get("language")).get("java") ).get("singleton"));
        template = template.replace("##package##", ( (HashMap<String, Object>) JsonToHashMap.configuration.get("package")).get(language).toString());
        template = template.replace("##package-name##", packageName.concat(";"));
        template = template.replace("##declaration##", ( (HashMap<String, Object>) JsonToHashMap.configuration.get("class-declaration")).get(language).toString());
        template = template.replace("##class-name##", TableUtility.firtLetterToUpper(this.name));
        template = template.replace("##attributs##", fieldList(language, framework));
        template = template.replace("##import##", importList(language, framework));
        template = template.replace("##getter-setter##", getterSetterList(language, framework));
        template = template.replace("##methods##",apiMethod(language, framework));
        return template;
    }
    public String apiMethod(String language, String framework){
        String templateMethod = ((HashMap<String, Object>) 
        (((HashMap<String, Object>) JsonToHashMap.attributesConfiguration
        .get(language))).get(framework)).get("methods").toString();
        return "";
    }
    public String fieldList(String language, String framework){
        String fieldList = "";
        for (int i = 0; i < this.colonneList.size(); i++) {
            fieldList += colonneList.get(i).buildStringAttributes(language, framework); 
        }
        return fieldList;
    }
    public String importList(String language, String framework){
        String importList = "";
        for (int i = 0; i < this.colonneList.size(); i++) {
            if(!importList.contains(colonneList.get(i).importString(language, framework,( ( HashMap<String, Object>) JsonToHashMap.configuration.get("import")).get(language).toString())))
                importList += colonneList.get(i).importString(language, framework,( ( HashMap<String, Object>) JsonToHashMap.configuration.get("import")).get(language).toString()).concat(";");
        }
        return importList;
    }
    public String getterSetterList(String language, String framework){
        String getterSetterString = "";
        for (int i = 0; i < this.colonneList.size(); i++) {
            getterSetterString += colonneList.get(i).getterSetterString(language, framework);
        }
        return getterSetterString;
    }
    public void setColonneList(ResultSet resultSetColonne, DatabaseMetaData metaData) throws Exception {
        colonneList = new ArrayList<>();
        while(resultSetColonne.next()){
            Colonne colonne = new Colonne(resultSetColonne, metaData, this.name);
            colonneList.add(colonne);
        }
    }
    public static String writeSingleton(String template, boolean singleton){
        if(singleton){
            return template.replace("##start##", "{").replace("##end##", "}");
        }
        return template.replace("##start##", "").replace("##end##", "");
    }
    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public List<Colonne> getColonneList() {
        return colonneList;
    }

    public void setColonneList(List<Colonne> colonneList) {
        this.colonneList = colonneList;
    }

    public Table(ResultSet resultSetColonne, String name, DatabaseMetaData metaData) throws Exception {
        this.name = name;
        this.setColonneList(resultSetColonne, metaData);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
