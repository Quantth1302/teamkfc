package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.Statistic;
import service.StatisticService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChartController implements Initializable {
    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < 12; i++){
            double price1 = 0;
            ObservableList<Statistic> statistics = null;
            StatisticService statisticService = new StatisticService();

            try {
                statistics = statisticService.getAllStatistic_p(null, null, null, 1, ""+ (i+1) +"", "2019", 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Statistic s: statistics) {
                price1 += s.getItemTotalPrice();
            }
            series.getData().add(new XYChart.Data(""+ (i+1) + "", price1));
        }

        lineChart.getData().addAll(series);
    }
}
