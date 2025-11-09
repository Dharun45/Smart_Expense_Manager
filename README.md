# Smart_Expense_Manager
Smart Expense Manager is a Java-based desktop application that helps users track and manage their personal expenses efficiently. Built entirely using Core Java (JDK 17) and JavaFX 23, this project demonstrates solid OOP design, file handling, collections, and UI development in Java.

## Features

- Add new expense via form
- View all expenses in a table
- Visualize monthly category totals with a Bar Chart
- Data saved locally in `data/expenses.csv`
- Auto-load and auto-save

## Setup Instructions

1. **Install Java JDK 17**
2. **Download JavaFX SDK 23**
   - [JavaFX Downloads](https://gluonhq.com/products/javafx)
   - Extract to: `C:\javafx-sdk-23` (default in configuration)
3. **Clone this repository**
4. **Open with VS Code**

## Run in VS Code

Ensure you have the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) installed.

### Run Configuration

```
--module-path "C:\javafx-sdk-23\lib" --add-modules javafx.controls,javafx.fxml
```

Launch via `Run > Run Without Debugging` or use the included `.vscode/launch.json`.

## Folder Structure

```
SmartExpenseManager/
├─ src/
│  └─ main/java/app/
│     ├─ MainApp.java
│     ├─ model/Expense.java
│     ├─ service/ExpenseService.java
│     ├─ persistence/CSVUtil.java
│     └─ ui/ExpenseChart.java
├─ data/
│  └─ expenses.csv
├─ .vscode/
│  ├─ settings.json
│  └─ launch.json
└─ README.md
```

## Sample Output

![UI Screenshot](docs/screenshot.png)
_Expense Form, TableView, and Chart View_

## CSV Format Example

id,date,category,description,amount  
1,2025-01-04,Food,Lunch,120  
2,2025-01-05,Travel,Bus Ticket,30  
3,2025-01-06,Shopping,Clothes,500  

---

**Built with Core Java, JavaFX 23 (JDK 17).**
