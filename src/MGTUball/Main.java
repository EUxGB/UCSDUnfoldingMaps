//На скорую руку оценка шансов поступления в МГТУ на основе приказов прошлых лет, нужно взять приказ сохраненный в текстовик и спарсить все баллы составив по ним после гистограмму
package MGTUball;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {


        String path = "C:\\Users\\User\\Downloads\\2019-08-03UTF8.txt";
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        Map<String, Integer>  map = new HashMap<>();





        Reader lnr =
                new BufferedReader(
                        new FileReader(file));

        String s;
        while (true) {
            s = ((BufferedReader) lnr).readLine();
            if (s == null)
                break;
            s.replaceAll("\\s+", " ");
            s.trim();
            Pattern pattern = Pattern.compile("Сумма баллов:");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                String data = (s.substring(matcher.start() + 13, s.length() - 1).replace(";", "").replace("\\s+", ""));

               // int x = Integer.parseInt(data);
                if (map.get(data) == null) {
                    map.put(data,1);
                } else map.put(data, map.get(data) + 1);




//                System.out.println(s);

            }

        }
        System.out.println(map);


    }
}
