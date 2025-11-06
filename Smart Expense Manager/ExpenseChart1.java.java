package app;

import app.service.ExpenseService;
import app.model.Expense;
import app.ui.ExpenseChart;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApp extends Application {
    private ExpenseService service = new ExpenseService();
    private TableView<Expense> tableView = new TableView<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Smart Expense Manager");
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 16;");

        // Input Form
        HBox form = new HBox(8);
        DatePicker datePicker = new DatePicker(LocalDate.now());
        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");
        TextField descField = new TextField();
        descField.setPromptText("Description");
        TextField amtField = new TextField();
        amtField.setPromptText("Amount");

        Button addBtn = new Button("Add Expense");
        form.getChildren().addAll(
                new Label("Date:"), datePicker,
                new Label("Category:"), categoryField,
                new Label("Desc:"), descField,
                new Label("Amt:"), amtField,
                addBtn
        );

        // TableView setup
        TableColumn<Expense, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()));
        TableColumn<Expense, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDate().toString()));
        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCategory()));
        TableColumn<Expense, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescription()));
        TableColumn<Expense, Number> amtCol = new TableColumn<>("Amount");
        amtCol.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getAmount()));

        tableView.getColumns().addAll(idCol, dateCol, categoryCol, descCol, amtCol);
        tableView.getItems().addAll(service.getExpenses());

        // Add Expense Action
        addBtn.setOnAction(evt -> {
            LocalDate date = datePicker.getValue();
            String category = categoryField.getText().trim();
            String desc = descField.getText().trim();
            String amtText = amtField.getText().trim();
            double amt;
            try {
                amt = Double.parseDouble(amtText);
            } catch (Exception e) {
                showAlert("Amount should be a valid number!");
                return;
            }
            if (category.isEmpty() || desc.isEmpty()) {
                showAlert("Category and Description cannot be empty!");
                return;
            }
            service.addExpense(date, category, desc, amt);
            tableView.getItems().clear();
            tableView.getItems().addAll(service.getExpenses());
            categoryField.clear();
            descField.clear();
            amtField.clear();
        });

        // View Chart Button
        Button chartBtn = new Button("View Chart");
        chartBtn.setOnAction(evt -> new ExpenseChart(service).showChartWindow());

        root.getChildren().addAll(form, tableView, chartBtn);
        Scene scene = new Scene(root, 850, 500);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            service.save();
            Platform.exit();
        });
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}