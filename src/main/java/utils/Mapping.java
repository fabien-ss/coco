package utils;

import java.util.HashMap;

public class Mapping {

    HashMap<String, String> mappings = new HashMap<String, String>();
    
    public void setMappingFromList(String language) throws Exception{
        String filePath = System.getProperty("user.dir") + "/modele/"+language+"/Map";  
        String data = TableUtility.chargerModele(filePath);
        HashMap<String, String> mappings = fetchData(data);
        this.mappings = mappings;
    }

    public static HashMap<String, String> fetchData(String data){
        String[] line = data.split("\n");
        HashMap<String, String> mappings = new HashMap<String, String>();
        for (String string : line) {
            String[] column = string.split("=");
            mappings.put(column[0], column[1]);
        }   
        return mappings;
    }

    public String getMapping(String columnName) {
        return mappings.get(columnName);
    }
}
