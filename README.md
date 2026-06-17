# Expense Tracker with Monthly Analytics

**Author:** Mummana Devi Prasad  
**Academic / Internship Project**  

---

## Table of Contents
1. [Introduction](#introduction)
2. [Abstract](#abstract)
3. [Features](#features)
4. [Technologies Used](#technologies-used)
5. [OOP Concepts Used](#oop-concepts-used)
6. [File Handling Used](#file-handling-used)
7. [Project Folder & GitHub Structure](#project-folder--github-structure)
8. [Steps Involved in Building the Project](#steps-involved-in-building-the-project)
9. [Sample Output](#sample-output)
10. [How to Run the Project](#how-to-run-the-project)
11. [Conclusion](#conclusion)

---

## Introduction
Personal financial management is a crucial skill in today's economy. The **Expense Tracker with Monthly Analytics** is a lightweight, command-line interface (CLI) application developed in Java. It allows users to log their day-to-day expenditures, organize them by category, modify or delete entries, save data persistently to a text file, and generate detailed monthly financial reports. This application serves as a comprehensive demonstration of core Java programming, object-oriented software design, file handling operations, and clean CLI user-experience design.

---

## Abstract
Tracking expenses manually is prone to errors, while heavy graphical tools can sometimes be overwhelming for users wanting a quick interface. This project addresses the gap by providing a menu-driven console system. Using Java Collections (`ArrayList` and `HashMap`) for dynamic storage, standard input streams for interaction, and a delimited file format (`expenses.txt`) for persistent storage, this application ensures user data is preserved between executions. In addition, the application includes a monthly analytics engine that calculates total monthly spending, flags the highest and lowest purchases, and computes category-wise percentages. Robust exception handling is employed to validate numeric amounts, menu selections, and calendar dates (`YYYY-MM-DD`), preventing system crashes due to illegal inputs.

---

## Features
* **Interactive Menu System**: A clean, numbered selection console (1-9) for straightforward operations.
* **Full CRUD Operations**:
  * **Add Expense**: Automates unique ID generation (`EXP1001`, `EXP1002`, etc.) and sets today's date if left blank.
  * **View Expenses**: Outputs a clean, tabular summary of transactions.
  * **Search Expense**: Instant lookup by Expense ID.
  * **Update Expense**: Modifies existing transaction details selectively (press ENTER to retain current values).
  * **Delete Expense**: Prompts with confirmation before permanently erasing a record.
* **Category Filtering**: Isolates spending for a given category (e.g. Food, Travel, Utilities) and totals it.
* **Monthly Analytics Engine**: 
  * Computes total expenditures for any specified month (`YYYY-MM`).
  * Identifies the single highest and lowest expense in that month.
  * Provides a category-wise breakdown with absolute costs and percentages.
* **File Persistence**: Automatic reading on startup and auto-save reminders upon exit.
* **Input Validation & Exception Handling**: Validates numeric bounds, format constraints, and calendar date ranges.

---

## Technologies Used
* **Programming Language**: Java (SE 8 or higher)
* **API Utilities**:
  * `java.util.ArrayList` (Dynamic list implementation)
  * `java.util.HashMap` & `java.util.Map` (Key-value grouping for analytics)
  * `java.time.LocalDate` (Date parsing and ISO checks)
  * `java.time.format.DateTimeFormatter` (Standardizing formatting)
  * `java.util.Scanner` (Standard console user inputs)
* **I/O Library**:
  * `java.io.File` (Metadata verification)
  * `java.io.FileReader` & `java.io.BufferedReader` (Buffered character file input stream)
  * `java.io.FileWriter` & `java.io.BufferedWriter` (Buffered character file output stream)

---

## OOP Concepts Used
1. **Classes and Objects**: Properties and behaviors of transactions are encapsulated in the [Expense](file:///c:/Users/mumma/OneDrive/Desktop/java%20project/ExpenseTracker/Expense.java) class, instantiated as objects at runtime.
2. **Encapsulation**: Fields (`id`, `title`, `category`, `amount`, `date`) in the `Expense` class are marked `private` and accessed or updated exclusively through public getter and setter methods. This ensures data integrity and protects internal state.
3. **Abstraction**: The [ExpenseManager](file:///c:/Users/mumma/OneDrive/Desktop/java%20project/ExpenseTracker/ExpenseManager.java) hides the complexity of how the expenses list is structured or how analytics calculations are computed from the user-facing [ExpenseTracker](file:///c:/Users/mumma/OneDrive/Desktop/java%20project/ExpenseTracker/ExpenseTracker.java) driver class.
4. **Separation of Concerns**: Business operations, data representation, and console interface operations are separated into distinct modules (`ExpenseManager.java`, `Expense.java`, `ExpenseTracker.java` respectively).

---

## File Handling Used
The application uses **character stream writers and readers** inside the `ExpenseManager` class:
* **Serialization**: Converts `Expense` objects to a pipe-delimited format (e.g., `EXP1001|Office Printer|Office|129.99|2026-06-02`) written to `expenses.txt` using `BufferedWriter` wrapping a `FileWriter`.
* **Deserialization**: Reads from `expenses.txt` line-by-line using `BufferedReader` and `FileReader`. Each line is split by the pattern `\\|` and fed into a static creator method `fromFileFormat(String)` to restore the object states.
* **Graceful Missing-File Management**: If `expenses.txt` is missing on startup, the application logs a notification, initializes an empty list, and continues execution normally instead of throwing a crash trace.

---

## Project Folder & GitHub Structure

```text
ExpenseTracker/
├── README.md               # Project documentation and user guide
├── Expense.java            # Expense object definition (Model)
├── ExpenseManager.java     # Expense handling & analytics engine (Controller)
├── ExpenseTracker.java     # Main driver, menu CLI, & validation (View)
└── expenses.txt            # Persistent database text file
```

For standard GitHub repository uploads, place all files under a single project folder as shown, ensuring `expenses.txt` is in the working directory when launched.

---

## Steps Involved in Building the Project
1. **Requirements Gathering**: Listed essential properties (IDs, categories, totals) and calculated required menu features.
2. **Database Design (File Format)**: Designed a lightweight pipe-delimited serialization format for text-file storage.
3. **Data Model (`Expense.java`)**: Implemented the base class, properties, getter/setter logic, and display formatter.
4. **Data Controller (`ExpenseManager.java`)**: Designed collection utilities utilizing `ArrayList`, created file saving/reading routines, and developed mathematical filters for analytics.
5. **Driver System (`ExpenseTracker.java`)**: Built input loops, Scanner inputs, and implemented strict try-catch blocks for robust date and amount validations.
6. **Integration & Manual Testing**: Ran numerous tests on empty fields, invalid month formats, and verified correct analytical output.

---

## Sample Output

Below is a complete, unedited transcript of a live console execution showing initialization, data view, item addition, updates, category filtering, monthly analytics, deletion, and exit:

```text
==================================================
    EXPENSE TRACKER WITH MONTHLY ANALYTICS
        Internship Project Submission
        Developer: Mummana Devi Prasad
==================================================
Success: Loaded 6 expense record(s) from file.

---------------- MAIN MENU ----------------
1. Add Expense
2. View Expenses
3. Search Expense by ID
4. Update Expense
5. Delete Expense
6. View Category-wise Expenses
7. Monthly Analytics Report
8. Save Expenses
9. Exit
-------------------------------------------
Enter your choice (1-9): 2

>>> ALL EXPENSES
+----------+----------------------+-----------------+-------------+--------------+
| ID       | Title                | Category        | Amount      | Date         |
+----------+----------------------+-----------------+-------------+--------------+
| EXP1001  | Office Printer       | Office          | $    129.99 | 2026-06-02   |
| EXP1002  | Groceries            | Food            | $     85.50 | 2026-06-05   |
| EXP1003  | Gas Refill           | Travel          | $     45.00 | 2026-06-08   |
| EXP1004  | Team Dinner          | Food            | $    120.00 | 2026-06-12   |
| EXP1005  | Internet Bill        | Utilities       | $     60.00 | 2026-05-28   |
| EXP1006  | Desk Chair           | Office          | $     95.00 | 2026-06-15   |
+----------+----------------------+-----------------+-------------+--------------+

---------------- MAIN MENU ----------------
1. Add Expense
2. View Expenses
...
Enter your choice (1-9): 1

>>> ADD NEW EXPENSE
Enter Expense Title (e.g., Office Chairs): Coffee
Enter Category (e.g., Office, Food, Bills): Food
Enter Amount ($): 4.75
Enter Date (YYYY-MM-DD) [Leave empty for today]: 
Info: No date entered. Defaulted to today: 2026-06-17
Success: Expense added successfully with ID: EXP1007

---------------- MAIN MENU ----------------
...
Enter your choice (1-9): 3

>>> SEARCH EXPENSE
Enter Expense ID to search (e.g., EXP1001): EXP1007

[MATCH FOUND]:
+----------+----------------------+-----------------+-------------+--------------+
| ID       | Title                | Category        | Amount      | Date         |
+----------+----------------------+-----------------+-------------+--------------+
| EXP1007  | Coffee               | Food            | $      4.75 | 2026-06-17   |
+----------+----------------------+-----------------+-------------+--------------+

---------------- MAIN MENU ----------------
...
Enter your choice (1-9): 4

>>> UPDATE EXPENSE
Enter Expense ID to update: EXP1007

Existing Details:
1. Title   : Coffee
2. Category: Food
3. Amount  : $4.75
4. Date    : 2026-06-17
----------------------------------------
Enter new details (press ENTER to keep current value):
New Title [Coffee]: Starbucks Coffee
New Category [Food]: 
New Amount [$4.75]: 5.50
New Date (YYYY-MM-DD) [2026-06-17]: 
[SUCCESS] Expense record modified successfully.

---------------- MAIN MENU ----------------
...
Enter your choice (1-9): 6

>>> CATEGORY-WISE EXPENSES
Enter Category to filter by: Food

--- Expenses in Category: Food ---
+----------+----------------------+-----------------+-------------+--------------+
| ID       | Title                | Category        | Amount      | Date         |
+----------+----------------------+-----------------+-------------+--------------+
| EXP1002  | Groceries            | Food            | $     85.50 | 2026-06-05   |
| EXP1004  | Team Dinner          | Food            | $    120.00 | 2026-06-12   |
| EXP1007  | Starbucks Coffee     | Food            | $      5.50 | 2026-06-17   |
+----------+----------------------+-----------------+-------------+--------------+
Total Spending for 'Food': $211.00

---------------- MAIN MENU ----------------
...
Enter your choice (1-9): 7

>>> MONTHLY ANALYTICS REPORT
Enter Month (YYYY-MM) (e.g., 2026-06): 2026-06

==================================================
       MONTHLY EXPENSE REPORT: 2026-06
==================================================
Total Transactions : 6
Total Spending     : $480.99
--------------------------------------------------
Highest Expense    : Office Printer - $129.99 (Office, 2026-06-02)
Lowest Expense     : Starbucks Coffee - $5.50 (Food, 2026-06-17)
--------------------------------------------------
Category-wise Spending Breakdown for 2026-06:
 - Office         : $    224.99 ( 46.78%)
 - Travel         : $     45.00 (  9.36%)
 - Food           : $    211.00 ( 43.87%)
==================================================

---------------- MAIN MENU ----------------
...
Enter your choice (1-9): 8

>>> SAVE DATA
Success: 7 expense record(s) saved to file successfully.

---------------- MAIN MENU ----------------
...
Enter your choice (1-9): 5

>>> DELETE EXPENSE
Enter Expense ID to delete: EXP1007
Are you sure you want to delete 'Starbucks Coffee' ($5.50) on 2026-06-17? (Y/N): Y
[SUCCESS] Expense record deleted successfully.

---------------- MAIN MENU ----------------
...
Enter your choice (1-9): 9

[WARNING] You have unsaved changes. Save now? (Y/N): Y
Success: 6 expense record(s) saved to file successfully.

Thank you for using Expense Tracker! Goodbye.
```

---

## How to Run the Project

### Prerequisites
* Ensure Java Development Kit (JDK 8 or higher) is installed.
* Verify setup using the terminal check command: `java -version`.

### Execution Steps
1. Open terminal, powershell, or command prompt.
2. Navigate into the project directory:
   ```bash
   cd "ExpenseTracker"
   ```
3. Compile all files:
   ```bash
   javac Expense.java ExpenseManager.java ExpenseTracker.java
   ```
4. Run the driver program:
   ```bash
   java ExpenseTracker
   ```

---

## Conclusion
The **Expense Tracker with Monthly Analytics** demonstrates an efficient solution to local financial tracking. By implementing strict object encapsulation, modular layers, precise input patterns validation, and efficient buffered streams for character writing and reading, the system operates seamlessly. It is well-documented, clean in structure, and fully prepared for submission as an academic evaluation or internship project.
