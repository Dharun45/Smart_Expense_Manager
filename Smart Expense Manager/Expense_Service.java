package app.service;

import app.model.Expense;
import app.persistence.CSVUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    public ExpenseService() {
        expenses = CSVUtil.readExpenses();
        nextId = expenses.stream().mapToInt(Expense::getId).max().orElse(0) + 1;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(LocalDate date, String category, String description, double amount) {
        Expense expense = new Expense(nextId++, date, category, description, amount);
        expenses.add(expense);
    }

    public void removeExpense(int id) {
        expenses.removeIf(e -> e.getId() == id);
    }

    public Map<String, Double> getCategoryTotalsForMonth(LocalDate monthDate) {
        int year = monthDate.getYear();
        int month = monthDate.getMonthValue();
        Map<String, Double> summary = new LinkedHashMap<>();
        for (Expense e : expenses) {
            if (e.getDate().getYear() == year && e.getDate().getMonthValue() == month) {
                summary.put(e.getCategory(),
                        summary.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
            }
        }
        return summary;
    }

    public void save() {
        CSVUtil.writeExpenses(expenses);
    }
}