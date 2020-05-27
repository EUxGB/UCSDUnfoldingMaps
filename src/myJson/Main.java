package myJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ClassLoader classLoader = new Main().getClass().getClassLoader();
        String filename = "city-data.json";//"my.json";

        File file = new File(classLoader.getResource(filename).getFile());

        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(file.getAbsolutePath());
            Object object = jsonParser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) object;
            JSONArray countyArr = (JSONArray) jsonObject.get("features");
            Iterator usersItr = countyArr.iterator();
            Map cityPopulation = new HashMap();

            while (usersItr.hasNext()) {
                JSONObject user = (JSONObject) usersItr.next();
                JSONObject properties = (JSONObject) user.get("properties");

                System.out.println((properties.get("name") + " - " + properties.get("population") + " mln"));
//                cityPopulation.keySet().add(properties.get("name"));
//                System.out.println(cityPopulation.get());


            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println();
    }

}
