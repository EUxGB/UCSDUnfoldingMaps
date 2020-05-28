package module1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class Controller {
    @FXML
    public BarChart<Integer, Integer> barChart  ;
    @FXML
    public CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();







}
