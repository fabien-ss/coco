package configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.TableUtility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JsonToHashMap {
    
    public static HashMap<String, Object> configuration;
    public static HashMap<String, Object> attributesConfiguration;
    public static HashMap<String, Object> fieldEquivalence;
    public static String template;

    static {
        try {
            configuration = getData("configuration.json");
            attributesConfiguration = getData("attributes.json");
            fieldEquivalence = getData("field_equivalence.json");
            template = TableUtility.chargerModele(System.getProperty("user.dir").concat("/template"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static HashMap<String, Object> getData(String jsonName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonPath = System.getProperty("user.dir").concat(File.separator).concat(jsonName);
        return mapper.readValue(new File(jsonPath), HashMap.class);
    }
    public static void main(String[] args) {
    }
}
