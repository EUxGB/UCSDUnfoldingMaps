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

        String path1 = "data\\prikaz\\МГТУ_2019-08-03(80).txt";
        String path2 = "data\\prikaz\\МГТУ_2019-08-08(20).txt";
        String path3 = "data\\prikaz\\ВШЭ(инф.).txt";
        String path4 = "data\\prikaz\\МГТУ_ГА_80.txt";
        Map<String, Number> map1, map2, map3, map4;
        Map<String, Number> addMap;
        Map<String, Number> map0 = new TreeMap<>();

        Maps maps = new Maps();
        int N = 0, N1 = 0, N2 = 0, N3 = 0;

        for (int i = 200; i <= 320; i++) {
            map0.put(Integer.toString(i), 0);
        }
        map1 = maps.getMap(path1);
        map2 = maps.getMap(path2);
       // addMap = maps.AddMap(map1, map2);
        map3 = maps.getMap(path3);
        map4 = maps.getMap(path4);

        N = maps.getN(map1);
        N1 = maps.getN(map2);
        N2 = maps.getN(map3);
        N3 = maps.getN(map4);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Кол-во абитуриентов");
        xAxis.setLabel("Баллы " + (N + N1 + N2+N3) + " поступающих");

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

        barChart.getData().add(maps.getXYChart(map0));
//        barChart.getData().add(maps.getXYChart(map3));
//        barChart.getData().add(maps.getXYChart(map1));
//        barChart.getData().add(maps.getXYChart(map2));
        barChart.getData().add(maps.getXYChart(map4));
//        barChart.getData().add(maps.getXYChart(addMap));

        VBox vbox = new VBox(barChart);
        primaryStage.setTitle("В МГТУ поступить можно на бюджет!");
        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(400);
        primaryStage.show();
    }

}
