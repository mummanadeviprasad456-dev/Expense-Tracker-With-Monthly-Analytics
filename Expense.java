/**
 * Expense.java
 * Represents a single expense record in the system.
 * Demonstrates the OOP concept of Encapsulation using private properties and public getters/setters.
 * 
 * Author: Mummana Devi Prasad
 */
public class Expense {
    // Encapsulated data fields (private fields)
    private String id;
    private String title;
    private String category;
    private double amount;
    private String date; // Format: YYYY-MM-DD

    /**
     * Default constructor.
     */
    public Expense() {
    }

    /**
     * Parameterized constructor to initialize all fields of an expense.
     * 
     * @param id       The unique ID of the expense (e.g., EXP1001)
     * @param title    The title or description of the expense
     * @param category The category of the expense (e.g., Food, Transport)
     * @param amount   The cost of the expense
     * @param date     The date of the expense (YYYY-MM-DD)
     */
    public Expense(String id, String title, String category, double amount, String date) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters (Encapsulation interface)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Displays details of the expense in a clean, formatted manner.
     */
    public void display() {
        System.out.printf("| %-8s | %-20s | %-15s | $%10.2f | %-12s |\n", 
                id, truncateString(title, 20), truncateString(category, 15), amount, date);
    }

    /**
     * Helper method to truncate long strings for neat formatting.
     */
    private String truncateString(String str, int length) {
        if (str == null) return "";
        if (str.length() > length) {
            return str.substring(0, length - 3) + "...";
        }
        return str;
    }

    /**
     * Converts the expense object into a formatted file-friendly string.
     * Uses pipe (|) as a delimiter for safe file reading and writing.
     * 
     * @return Pipe-delimited string representing the expense details
     */
    public String toFileFormat() {
        return id + "|" + title + "|" + category + "|" + amount + "|" + date;
    }

    /**
     * Creates an Expense object from a pipe-delimited string (deserialization).
     * 
     * @param fileLine A line from the text file representing an expense
     * @return An Expense object, or null if the string format is invalid
     */
    public static Expense fromFileFormat(String fileLine) {
        try {
            String[] parts = fileLine.split("\\|");
            if (parts.length == 5) {
                String id = parts[0].trim();
                String title = parts[1].trim();
                String category = parts[2].trim();
                double amount = Double.parseDouble(parts[3].trim());
                String date = parts[4].trim();
                return new Expense(id, title, category, amount, date);
            }
        } catch (Exception e) {
            // Log parse errors if needed or silently skip corrupt lines
            System.err.println("Warning: Skipping corrupted line - " + fileLine + " (Error: " + e.getMessage() + ")");
        }
        return null;
    }
}
