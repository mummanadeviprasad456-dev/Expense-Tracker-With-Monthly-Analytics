import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ExpenseManager.java
 * Manages a list of expenses and performs business logic operations.
 * Demonstrates CRUD, calculations, file handling, and analytics.
 * 
 * Author: Mummana Devi Prasad
 */
public class ExpenseManager {
    private ArrayList<Expense> expenses;
    private int nextIdCounter;
    private static final String DEFAULT_ID_PREFIX = "EXP";
    private static final int STARTING_ID = 1001;

    /**
     * Constructor initializing the empty list of expenses.
     */
    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        this.nextIdCounter = STARTING_ID;
    }

    /**
     * Generates a unique sequential Expense ID.
     * Searches existing items to prevent collisions.
     * 
     * @return Unique ID string
     */
    public String generateNextId() {
        // Find the maximum ID value currently present to ensure uniqueness
        int maxIdVal = STARTING_ID - 1;
        for (Expense exp : expenses) {
            try {
                if (exp.getId().startsWith(DEFAULT_ID_PREFIX)) {
                    int num = Integer.parseInt(exp.getId().substring(DEFAULT_ID_PREFIX.length()));
                    if (num > maxIdVal) {
                        maxIdVal = num;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignore non-standard IDs
            }
        }
        nextIdCounter = maxIdVal + 1;
        return DEFAULT_ID_PREFIX + nextIdCounter;
    }

    /**
     * Adds an expense to the list and auto-assigns a unique ID if not already set.
     * 
     * @param expense The expense to add
     */
    public void addExpense(Expense expense) {
        if (expense.getId() == null || expense.getId().trim().isEmpty()) {
            expense.setId(generateNextId());
        }
        expenses.add(expense);
        System.out.println("Success: Expense added successfully with ID: " + expense.getId());
    }

    /**
     * Retrieves all managed expenses.
     * 
     * @return List of expenses
     */
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Views all expenses in a structured table.
     */
    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("Information: No expenses recorded yet.");
            return;
        }
        printTableHeader();
        for (Expense exp : expenses) {
            exp.display();
        }
        printTableFooter();
    }

    /**
     * Searches for an expense by its ID.
     * 
     * @param id The ID to search for
     * @return The Expense object if found, otherwise null
     */
    public Expense searchExpenseById(String id) {
        for (Expense exp : expenses) {
            if (exp.getId().equalsIgnoreCase(id.trim())) {
                return exp;
            }
        }
        return null;
    }

    /**
     * Updates an existing expense's details.
     * 
     * @param id          The ID of the expense to update
     * @param newTitle    The new title
     * @param newCategory The new category
     * @param newAmount   The new amount
     * @param newDate     The new date
     * @return true if updated successfully, false otherwise
     */
    public boolean updateExpense(String id, String newTitle, String newCategory, double newAmount, String newDate) {
        Expense exp = searchExpenseById(id);
        if (exp != null) {
            exp.setTitle(newTitle);
            exp.setCategory(newCategory);
            exp.setAmount(newAmount);
            exp.setDate(newDate);
            return true;
        }
        return false;
    }

    /**
     * Deletes an expense by its ID.
     * 
     * @param id The ID of the expense to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteExpense(String id) {
        Expense exp = searchExpenseById(id);
        if (exp != null) {
            expenses.remove(exp);
            return true;
        }
        return false;
    }

    /**
     * Filters and displays expenses of a specific category.
     * 
     * @param category The category name to filter by
     */
    public void viewExpensesByCategory(String category) {
        ArrayList<Expense> filtered = new ArrayList<>();
        for (Expense exp : expenses) {
            if (exp.getCategory().equalsIgnoreCase(category.trim())) {
                filtered.add(exp);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("Information: No expenses found under the category: '" + category + "'");
            return;
        }

        System.out.println("\n--- Expenses in Category: " + category + " ---");
        printTableHeader();
        double sum = 0;
        for (Expense exp : filtered) {
            exp.display();
            sum += exp.getAmount();
        }
        printTableFooter();
        System.out.printf("Total Spending for '%s': $%.2f\n", category, sum);
    }

    /**
     * Calculates total expenses for a specific month.
     * 
     * @param yearMonth The target month in format YYYY-MM (e.g. 2026-06)
     * @return Total amount spent
     */
    public double calculateMonthlyTotalExpenses(String yearMonth) {
        double total = 0.0;
        for (Expense exp : expenses) {
            if (exp.getDate().startsWith(yearMonth)) {
                total += exp.getAmount();
            }
        }
        return total;
    }

    /**
     * Calculates category-wise total spending for all expenses.
     * 
     * @return Map of category names to total expenses
     */
    public Map<String, Double> calculateCategoryWiseExpenses() {
        Map<String, Double> categoryMap = new HashMap<>();
        for (Expense exp : expenses) {
            String cat = exp.getCategory().trim();
            // Handle case-insensitivity grouping nicely by capitalizing first letter
            if (!cat.isEmpty()) {
                cat = cat.substring(0, 1).toUpperCase() + cat.substring(1).toLowerCase();
            }
            categoryMap.put(cat, categoryMap.getOrDefault(cat, 0.0) + exp.getAmount());
        }
        return categoryMap;
    }

    /**
     * Generates a detailed monthly analytics report including:
     * - Total spending
     * - Highest & Lowest expense
     * - Category-wise breakdown for the month
     * 
     * @param yearMonth The month string in format YYYY-MM
     */
    public void generateMonthlyExpenseReport(String yearMonth) {
        ArrayList<Expense> monthlyExpenses = new ArrayList<>();
        double total = 0.0;
        Expense highest = null;
        Expense lowest = null;
        Map<String, Double> categoryMap = new HashMap<>();

        for (Expense exp : expenses) {
            if (exp.getDate().startsWith(yearMonth)) {
                monthlyExpenses.add(exp);
                double amount = exp.getAmount();
                total += amount;

                // Track highest
                if (highest == null || amount > highest.getAmount()) {
                    highest = exp;
                }
                // Track lowest
                if (lowest == null || amount < lowest.getAmount()) {
                    lowest = exp;
                }

                // Track category split
                String cat = exp.getCategory().trim();
                if (!cat.isEmpty()) {
                    cat = cat.substring(0, 1).toUpperCase() + cat.substring(1).toLowerCase();
                }
                categoryMap.put(cat, categoryMap.getOrDefault(cat, 0.0) + amount);
            }
        }

        if (monthlyExpenses.isEmpty()) {
            System.out.println("Information: No records found for the month: " + yearMonth);
            return;
        }

        // Output report
        System.out.println("\n==================================================");
        System.out.println("       MONTHLY EXPENSE REPORT: " + yearMonth);
        System.out.println("==================================================");
        System.out.printf("Total Transactions : %d\n", monthlyExpenses.size());
        System.out.printf("Total Spending     : $%.2f\n", total);
        System.out.println("--------------------------------------------------");

        if (highest != null) {
            System.out.printf("Highest Expense    : %s - $%1.2f (%s, %s)\n", 
                    highest.getTitle(), highest.getAmount(), highest.getCategory(), highest.getDate());
        }
        if (lowest != null) {
            System.out.printf("Lowest Expense     : %s - $%1.2f (%s, %s)\n", 
                    lowest.getTitle(), lowest.getAmount(), lowest.getCategory(), lowest.getDate());
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Category-wise Spending Breakdown for " + yearMonth + ":");
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            double percentage = (entry.getValue() / total) * 100;
            System.out.printf(" - %-15s: $%10.2f (%6.2f%%)\n", entry.getKey(), entry.getValue(), percentage);
        }
        System.out.println("==================================================");
    }

    /**
     * Saves the current list of expenses to the specified file path.
     * Uses FileWriter and BufferedWriter as requested.
     * 
     * @param filePath Path of the file to save data
     */
    public void saveDataToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Expense exp : expenses) {
                writer.write(exp.toFileFormat());
                writer.newLine();
            }
            System.out.println("Success: " + expenses.size() + " expense record(s) saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error: Failed to save data to file. " + e.getMessage());
        }
    }

    /**
     * Loads the list of expenses from the specified file path.
     * Uses FileReader and BufferedReader as requested.
     * Handles non-existent file gracefully by keeping list empty.
     * 
     * @param filePath Path of the file to load data from
     */
    public void loadDataFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Notification: No previous data file found at '" + filePath + "'. Starting with a clean list.");
            return;
        }

        expenses.clear();
        int loadedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Expense exp = Expense.fromFileFormat(line);
                if (exp != null) {
                    expenses.add(exp);
                    loadedCount++;
                }
            }
            System.out.println("Success: Loaded " + loadedCount + " expense record(s) from file.");
        } catch (IOException e) {
            System.err.println("Error: Failed to read data from file. " + e.getMessage());
        }
    }

    // Tabular formatting helpers
    private void printTableHeader() {
        System.out.println("+----------+----------------------+-----------------+-------------+--------------+");
        System.out.println("| ID       | Title                | Category        | Amount      | Date         |");
        System.out.println("+----------+----------------------+-----------------+-------------+--------------+");
    }

    private void printTableFooter() {
        System.out.println("+----------+----------------------+-----------------+-------------+--------------+");
    }
}
