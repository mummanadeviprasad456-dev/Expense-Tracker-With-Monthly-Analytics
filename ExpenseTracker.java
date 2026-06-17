import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * ExpenseTracker.java
 * The main driver class for the Expense Tracker application.
 * Manages the user input scanner, input validation, menu loop, and interfaces with ExpenseManager.
 * 
 * Author: Mummana Devi Prasad
 */
public class ExpenseTracker {
    private static final String FILE_PATH = "expenses.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ExpenseManager manager = new ExpenseManager();
    private static boolean isModified = false; // Tracks if there are unsaved changes

    public static void main(String[] args) {
        // Welcome Banner
        System.out.println("==================================================");
        System.out.println("    EXPENSE TRACKER WITH MONTHLY ANALYTICS");
        System.out.println("        Internship Project Submission");
        System.out.println("        Developer: Mummana Devi Prasad");
        System.out.println("==================================================");

        // Load existing records from file on startup
        manager.loadDataFromFile(FILE_PATH);

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    addNewExpenseMenu();
                    break;
                case 2:
                    viewExpensesMenu();
                    break;
                case 3:
                    searchExpenseMenu();
                    break;
                case 4:
                    updateExpenseMenu();
                    break;
                case 5:
                    deleteExpenseMenu();
                    break;
                case 6:
                    viewCategoryWiseExpensesMenu();
                    break;
                case 7:
                    monthlyAnalyticsReportMenu();
                    break;
                case 8:
                    saveExpensesMenu();
                    break;
                case 9:
                    running = exitMenu();
                    break;
                default:
                    System.out.println("[ERROR] Invalid choice. Please select from options 1-9.");
            }
        }
        scanner.close();
    }

    /**
     * Displays the standard user menu options.
     */
    private static void displayMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Search Expense by ID");
        System.out.println("4. Update Expense");
        System.out.println("5. Delete Expense");
        System.out.println("6. View Category-wise Expenses");
        System.out.println("7. Monthly Analytics Report");
        System.out.println("8. Save Expenses");
        System.out.println("9. Exit");
        System.out.println("-------------------------------------------");
    }

    /**
     * Safely reads and validates the menu selection.
     */
    private static int getMenuChoice() {
        while (true) {
            System.out.print("Enter your choice (1-9): ");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 9) {
                    return choice;
                }
                System.out.println("[ERROR] Please enter a number between 1 and 9.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * CLI logic for adding a new expense.
     */
    private static void addNewExpenseMenu() {
        System.out.println("\n>>> ADD NEW EXPENSE");
        
        String title = getNonEmptyString("Enter Expense Title (e.g., Office Chairs): ");
        String category = getNonEmptyString("Enter Category (e.g., Office, Food, Bills): ");
        double amount = getPositiveDouble("Enter Amount ($): ");
        String date = getValidDateInput("Enter Date (YYYY-MM-DD) [Leave empty for today]: ", true);

        // If user pressed Enter for date, use current date
        if (date.isEmpty()) {
            date = LocalDate.now().toString();
            System.out.println("Info: No date entered. Defaulted to today: " + date);
        }

        // Create new Expense instance. ID is auto-assigned by the manager.
        Expense newExpense = new Expense(null, title, category, amount, date);
        manager.addExpense(newExpense);
        isModified = true;
    }

    /**
     * CLI logic for viewing all recorded expenses.
     */
    private static void viewExpensesMenu() {
        System.out.println("\n>>> ALL EXPENSES");
        manager.viewAllExpenses();
    }

    /**
     * CLI logic for searching for an expense by ID.
     */
    private static void searchExpenseMenu() {
        System.out.println("\n>>> SEARCH EXPENSE");
        String id = getNonEmptyString("Enter Expense ID to search (e.g., EXP1001): ");
        Expense exp = manager.searchExpenseById(id);

        if (exp != null) {
            System.out.println("\n[MATCH FOUND]:");
            System.out.println("+----------+----------------------+-----------------+-------------+--------------+");
            System.out.println("| ID       | Title                | Category        | Amount      | Date         |");
            System.out.println("+----------+----------------------+-----------------+-------------+--------------+");
            exp.display();
            System.out.println("+----------+----------------------+-----------------+-------------+--------------+");
        } else {
            System.out.println("[NOT FOUND] No expense record exists with ID: " + id);
        }
    }

    /**
     * CLI logic for updating an existing expense's details.
     */
    private static void updateExpenseMenu() {
        System.out.println("\n>>> UPDATE EXPENSE");
        String id = getNonEmptyString("Enter Expense ID to update: ");
        Expense exp = manager.searchExpenseById(id);

        if (exp == null) {
            System.out.println("[NOT FOUND] No expense record exists with ID: " + id);
            return;
        }

        System.out.println("\nExisting Details:");
        System.out.println("1. Title   : " + exp.getTitle());
        System.out.println("2. Category: " + exp.getCategory());
        System.out.println("3. Amount  : $" + exp.getAmount());
        System.out.println("4. Date    : " + exp.getDate());
        System.out.println("----------------------------------------");

        System.out.println("Enter new details (press ENTER to keep current value):");

        System.out.print("New Title [" + exp.getTitle() + "]: ");
        String newTitle = scanner.nextLine().trim();
        if (newTitle.isEmpty()) {
            newTitle = exp.getTitle();
        }

        System.out.print("New Category [" + exp.getCategory() + "]: ");
        String newCategory = scanner.nextLine().trim();
        if (newCategory.isEmpty()) {
            newCategory = exp.getCategory();
        }

        double newAmount = exp.getAmount();
        while (true) {
            System.out.print("New Amount [$" + exp.getAmount() + "]: ");
            String amtStr = scanner.nextLine().trim();
            if (amtStr.isEmpty()) {
                break;
            }
            try {
                double amt = Double.parseDouble(amtStr);
                if (amt < 0) {
                    System.out.println("[ERROR] Amount cannot be negative.");
                    continue;
                }
                newAmount = amt;
                break;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid format. Please enter a valid decimal number.");
            }
        }

        String newDate = getValidDateInput("New Date (YYYY-MM-DD) [" + exp.getDate() + "]: ", true);
        if (newDate.isEmpty()) {
            newDate = exp.getDate();
        }

        boolean success = manager.updateExpense(id, newTitle, newCategory, newAmount, newDate);
        if (success) {
            System.out.println("[SUCCESS] Expense record modified successfully.");
            isModified = true;
        } else {
            System.out.println("[ERROR] Failed to update expense record.");
        }
    }

    /**
     * CLI logic for deleting an expense.
     */
    private static void deleteExpenseMenu() {
        System.out.println("\n>>> DELETE EXPENSE");
        String id = getNonEmptyString("Enter Expense ID to delete: ");
        
        // Confirm deletion
        Expense exp = manager.searchExpenseById(id);
        if (exp == null) {
            System.out.println("[NOT FOUND] No expense record exists with ID: " + id);
            return;
        }

        System.out.printf("Are you sure you want to delete '%s' ($%.2f) on %s? (Y/N): ", 
                exp.getTitle(), exp.getAmount(), exp.getDate());
        String confirmation = scanner.nextLine().trim();
        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {
            boolean success = manager.deleteExpense(id);
            if (success) {
                System.out.println("[SUCCESS] Expense record deleted successfully.");
                isModified = true;
            } else {
                System.out.println("[ERROR] Failed to delete expense record.");
            }
        } else {
            System.out.println("Cancelled: Deletion operation aborted.");
        }
    }

    /**
     * CLI logic for category filtering.
     */
    private static void viewCategoryWiseExpensesMenu() {
        System.out.println("\n>>> CATEGORY-WISE EXPENSES");
        String category = getNonEmptyString("Enter Category to filter by: ");
        manager.viewExpensesByCategory(category);
    }

    /**
     * CLI logic for generating the Monthly Analytics Report.
     */
    private static void monthlyAnalyticsReportMenu() {
        System.out.println("\n>>> MONTHLY ANALYTICS REPORT");
        String yearMonth = getValidYearMonthInput("Enter Month (YYYY-MM) (e.g., 2026-06): ");
        manager.generateMonthlyExpenseReport(yearMonth);
    }

    /**
     * Saves changes to text file.
     */
    private static void saveExpensesMenu() {
        System.out.println("\n>>> SAVE DATA");
        manager.saveDataToFile(FILE_PATH);
        isModified = false;
    }

    /**
     * Gracefully exits the application, handling unsaved modifications.
     * 
     * @return false to break the menu-driven loop
     */
    private static boolean exitMenu() {
        if (isModified) {
            System.out.print("\n[WARNING] You have unsaved changes. Save now? (Y/N): ");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("YES")) {
                manager.saveDataToFile(FILE_PATH);
            }
        }
        System.out.println("\nThank you for using Expense Tracker! Goodbye.");
        return false;
    }

    // Input validation helper functions

    /**
     * Helper to read a non-empty string.
     */
    private static String getNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[ERROR] This field cannot be empty. Please enter a value.");
        }
    }

    /**
     * Helper to read a positive double amount.
     */
    private static double getPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double val = Double.parseDouble(input);
                if (val >= 0) {
                    return val;
                }
                System.out.println("[ERROR] Amount must be positive or zero.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid value. Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Validates and returns date input in YYYY-MM-DD format.
     * 
     * @param prompt    The label printed to console
     * @param allowEmpty Set to true if empty input is acceptable (for defaults/updates)
     * @return Validated date string or empty string if allowed
     */
    private static String getValidDateInput(String prompt, boolean allowEmpty) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty() && allowEmpty) {
                return "";
            }

            try {
                LocalDate.parse(input, formatter);
                return input; // Success!
            } catch (DateTimeParseException e) {
                System.out.println("[ERROR] Invalid date format or date does not exist. Please use YYYY-MM-DD (e.g. 2026-06-15).");
            }
        }
    }

    /**
     * Validates and returns a Year-Month string in YYYY-MM format.
     */
    private static String getValidYearMonthInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Simple pattern check for YYYY-MM
            if (input.matches("\\d{4}-\\d{2}")) {
                try {
                    // Try parsing as the first day of that month to ensure month values are valid (01-12)
                    LocalDate.parse(input + "-01");
                    return input;
                } catch (DateTimeParseException e) {
                    // fall-through to error print
                }
            }
            System.out.println("[ERROR] Invalid format. Please enter as YYYY-MM (e.g., 2026-06).");
        }
    }
}
