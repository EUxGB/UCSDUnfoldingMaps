//На скорую руку оценка шансов поступления в МГТУ на основе приказов прошлых лет, приказ сохранен в текстовик, построена гистограмма
package MGTUball;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    public static void main(String[] args) {
        //String path = "C:\\Users\\mrusn\\Downloads\\2019-08-03.txt";//"C:\\Users\\mrusn\\Downloads\\2019-08-03.pdf";  //"C:\\Users\\User\\Downloads\\2019-08-03UTF8.txt";
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../module1/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Кол-во абитуриентов");
        // Create a BarChart
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        String path = "data\\2019-08-03.txt";
        Map<String, Number> map = new TreeMap<>();
        int N = 0;
        map = getMap(path);
        for (Map.Entry<String, Number> entry : map.entrySet()) {
            N += entry.getValue().intValue();
        }
        xAxis.setLabel("Баллы " + N + " поступающих");
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        for (Map.Entry<String, Number> entry : map.entrySet()) {
            dataSeries1.getData().add(new XYChart.Data<String, Number>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(dataSeries1);
        VBox vbox = new VBox(barChart);
        primaryStage.setTitle("В МГТУ поступить можно на бюджет!");
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(400);
        primaryStage.show();
    }

    Map<String, Number> getMap(String path) throws IOException {
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        Map<String, Number> map = new TreeMap<>();
        Reader lnr =
                new BufferedReader(
                        new FileReader(file));
        String s;
        while (true) {
            s = ((BufferedReader) lnr).readLine();
            if (s == null)
                break;
            //System.out.println(s.trim().replaceAll("[^a-яА-ЯЁё0-9]", ""));
            Pattern pattern = Pattern.compile("Сумма баллов:");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                String data = s.substring(s.length() - 4).replace(";", "");
                if (map.get(data) == null) {
                    map.put(data, 1);
                } else map.put(data, map.get(data).intValue() + 1);
            }
        }
        map.remove("лов:");
        System.out.println(map);
        return map;
    }
}
