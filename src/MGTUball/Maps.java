package MGTUball;

import javafx.scene.chart.XYChart;

import java.io.*;
import java.lang.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Maps<String, Number> {

    private Map<String, Number> map;

    XYChart.Series<String, Number> getXYChart(Map<String, Number> map) {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<String, Number>();
        for (Map.Entry<String, Number> entry : map.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<String, Number>(entry.getKey(), entry.getValue()));
        }

        return dataSeries;
    }

    public int getN(Map<String, Number> map) {

        int N = 0;

        for (Map.Entry<String, Number> entry : map.entrySet()) {
            N += (java.lang.Integer) entry.getValue();
        }
        return N;
    }

    Map<java.lang.String, java.lang.Number> getMGTUMap(java.lang.String path) throws IOException {
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        Map<java.lang.String, java.lang.Number> map = new TreeMap<>();
        Reader lnr =
                new BufferedReader(
                        new FileReader(file));
        java.lang.String s;
        while (true) {
            s = ((BufferedReader) lnr).readLine();
            if (s == null)
                break;

            Pattern pattern = Pattern.compile("Сумма баллов:");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                java.lang.String data = s.substring(s.length() - 4).replace(";", "");
                if (map.get(data) == null) {
                    map.put(data, 1);
                } else map.put(data, map.get(data).intValue() + 1);
            }
        }
        map.remove("лов:");
        map.remove("лиц:");
        map.remove("умма");
        System.out.println(map);
        return map;
    }

    Map<String, Integer> AddMGTUMap(Map<String, Number> map1, Map<String, Number> map2) {
        Map<String, Integer> map3 = new TreeMap<>();
        for (Map.Entry<String, Number> entry : map1.entrySet()) {
            if (map3.get(entry.getKey()) == null) {
                map3.put(entry.getKey(),  (Integer) entry.getValue());
            } else {
                map3.put(entry.getKey(), (Integer) entry.getValue());
            }

        }

        for (Map.Entry<String, Number> entry : map2.entrySet()) {
            if (map3.get(entry.getKey()) == null) {
                map3.put(entry.getKey(),(Integer) entry.getValue());
            } else {

                        map3.put(entry.getKey(), (Integer) entry.getValue()+map3.get(entry.getKey()));
//                (Integer) map2.get(entry.getKey())+
            }
        }
            System.out.println(map3.entrySet());


        return map3;
    }

}
