package app.ui;

import app.service.ExpenseService;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Map;

public class ExpenseChart {
    private ExpenseService service;

    public ExpenseChart(ExpenseService service) {
        this.service = service;
    }

    public void showChartWindow() {
        Stage chartStage = new Stage();
        chartStage.setTitle("Monthly Expense Summary (Bar Chart)");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Amount");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Expense Totals for " + LocalDate.now().getMonth());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Current Month");

        Map<String, Double> summary = service.getCategoryTotalsForMonth(LocalDate.now());
        for (Map.Entry<String, Double> entry : summary.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 600, 400);
        chartStage.setScene(scene);
        chartStage.show();
    }
}