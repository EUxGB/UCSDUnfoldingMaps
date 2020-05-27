package myJson;
// Вытащим из json файла в Map сведения о популяции в каждом городе записаном в
// формате String и отсортируем по количеству жителей в порядке убывания от большего к меньшему

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filename = "city-data.json";//test "my.json";
        getPopulation(filename);

    }

    public static void getPopulation(String filename) {
        ClassLoader classLoader = new Main().getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        JSONParser jsonParser = new JSONParser();
        HashMap<String, Double> cityPopulation = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(file.getAbsolutePath());
            Object object = jsonParser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) object;
            JSONArray countyArr = (JSONArray) jsonObject.get("features");
            Iterator usersItr = countyArr.iterator();
            while (usersItr.hasNext()) {
                JSONObject user = (JSONObject) usersItr.next();
                JSONObject properties = (JSONObject) user.get("properties");
                cityPopulation.put(
                        (String) properties.get("name"),
                        Double.parseDouble((String) properties.get("population"))
                );

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        cityPopulation.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(System.out::println);

    }

}
