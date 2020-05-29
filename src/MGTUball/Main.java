//На скорую руку оценка шансов поступления в МГТУ на основе приказов прошлых лет, приказ сохранен в текстовик, построена гистограмма
package MGTUball;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.TreeMap;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String path = "data\\2019-08-03(80).txt";
        String path1 = "data\\2019-08-08(20).txt";
        Map<String, Number> map1, map2;
        Map<String, Number> addMap;
        Map<String, Integer> map0 = new TreeMap<>();

        Maps maps = new Maps();
        int N, N1 = 0;

        for (int i = 150; i <= 320; i++) {
            String s = Integer.toString(i);
            map0.put(s, 0);
        }

        map1 = maps.getMGTUMap(path);
        map2 = maps.getMGTUMap(path1);
        addMap = maps.AddMGTUMap(map1, map2);

        N = maps.getN(map1);
        N1 = maps.getN(map2);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Кол-во абитуриентов");
        xAxis.setLabel("Баллы " + (N +N1) + " поступающих");
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.getData().add(maps.getXYChart(map0));
        barChart.getData().add(maps.getXYChart(map1));
        barChart.getData().add(maps.getXYChart(map2));
       // barChart.getData().add(maps.getXYChart(addMap));

        VBox vbox = new VBox(barChart);
        primaryStage.setTitle("В МГТУ поступить можно на бюджет!");
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(400);
        primaryStage.show();
    }

}
