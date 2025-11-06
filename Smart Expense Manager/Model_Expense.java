package app.persistence;

import app.model.Expense;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class CSVUtil {
    public static final String FILE_PATH = "data/expenses.csv";

    public static List<Expense> readExpenses() {
        List<Expense> expenses = new ArrayList<>();
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return expenses;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String header = reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length < 5) continue;
                int id = Integer.parseInt(arr[0]);
                LocalDate date = LocalDate.parse(arr[1]);
                String category = arr[2];
                String description = arr[3];
                double amount = Double.parseDouble(arr[4]);
                expenses.add(new Expense(id, date, category, description, amount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    public static void writeExpenses(List<Expense> expenses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("id,date,category,description,amount\n");
            for (Expense exp : expenses) {
                writer.write(String.format("%d,%s,%s,%s,%.2f\n",
                        exp.getId(),
                        exp.getDate(),
                        exp.getCategory(),
                        exp.getDescription(),
                        exp.getAmount()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}