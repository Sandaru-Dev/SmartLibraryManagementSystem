import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class K2521153_LibrarySystem {
    private List<K2521153_Book> inventory = new ArrayList<>();
    private List<K2521153_User> userList = new ArrayList<>();
    private Stack<K2521153_Command> commandHistory = new Stack<>();

    public void addBook(K2521153_Book book) { inventory.add(book); }
    public void removeBook(K2521153_Book book) { inventory.remove(book); }

    public void registerUser(K2521153_User user) { userList.add(user); }
    public void removeUser(K2521153_User user) { userList.remove(user); }

    public K2521153_Book findBook(String bookId) {
        return inventory.stream().filter(b -> b.getBookId().equals(bookId)).findFirst().orElse(null);
    }
    public K2521153_User findUser(String userId) {
        return userList.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
    }

    public List<K2521153_User> getAllUsers() { return userList; }

    public void executeAction(K2521153_Command command) {
        command.execute();
        commandHistory.push(command);
    }

    public void undoLastAction() {
        if (!commandHistory.isEmpty()) {
            K2521153_Command lastCmd = commandHistory.pop();
            lastCmd.undo();
        } else {
            System.out.println("   [INFO] No actions to undo.");
        }
    }

    // All books Report
    public void reportAllBooks() {
        System.out.println("\n--- REPORT: ALL BOOKS ---");
        printTableDivider();
        System.out.println(String.format("| %-5s | %-20s | %-15s | %-10s | %-13s | %-10s | %-18s |",
                "ID", "TITLE", "AUTHOR", "CATEGORY", "ISBN", "STATUS", "INFO"));
        printTableDivider();
        for (K2521153_Book b : inventory) System.out.println(b.getDetails());
        printTableDivider();
    }

    public void reportOverdueBooks() {
        // Overdue Books Report
        System.out.println("\n--- REPORT: OVERDUE BOOKS (System Time) ---");
        notifyOverdueBooks(new Date());
    }

    public void reportActiveBorrowers() {
        System.out.println("\n--- REPORT: ACTIVE BORROWERS ---");
        printTableDivider();
        System.out.println(String.format("| %-5s | %-20s | %-15s | %-10s | %-10s | %-18s |",
                "ID", "TITLE", "AUTHOR", "CATEGORY", "STATUS", "BORROWER"));
        printTableDivider();

        boolean found = false;
        for (K2521153_Book b : inventory) {
            if (b.getCurrentHolder() != null) {
                String dTitle = (b.getTitle().length() > 20) ? b.getTitle().substring(0, 17) + "..." : b.getTitle();
                String dAuthor = (b.getAuthor() != null && b.getAuthor().length() > 15) ? b.getAuthor().substring(0, 12) + "..." : "-";
                String dCat = (b.getCategory() != null && b.getCategory().length() > 10) ? b.getCategory().substring(0, 7) + "..." : "-";

                System.out.println(String.format("| %-5s | %-20s | %-15s | %-10s | %-10s | %-18s |",
                        b.getBookId(), dTitle, dAuthor, dCat, b.getState().getStateName(), b.getCurrentHolder().getName()));
                found = true;
            }
        }
        if(!found) System.out.println("| No active borrowers found.                                                                        |");
        printTableDivider();
    }

    // Most Borrowed Books Report
    public void reportMostBorrowedBooks() {
        System.out.println("\n--- REPORT: MOST BORROWED BOOKS ---");
        System.out.println("+-------+--------------------------+---------------------+");
        System.out.println("| ID    | BOOK TITLE               | TIMES BORROWED      |");
        System.out.println("+-------+--------------------------+---------------------+");

        List<K2521153_Book> sortedList = inventory.stream()
                .sorted((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
                .collect(Collectors.toList());

        for (K2521153_Book b : sortedList) {
            String dTitle = (b.getTitle().length() > 24) ? b.getTitle().substring(0, 21) + "..." : b.getTitle();
            System.out.println(String.format("| %-5s | %-24s | %-19d |", b.getBookId(), dTitle, b.getBorrowCount()));
        }
        System.out.println("+-------+--------------------------+---------------------+");
    }

    // NOTIFICATIONS 

    public void notifyOverdueBooks(Date currentDate) {
        System.out.println("\n--- NOTIFICATION: OVERDUE BOOKS CHECK ---");
        System.out.println("    Checking against date: " + new SimpleDateFormat("dd-MMM-yyyy").format(currentDate));
        System.out.println("+-------+-----------+-------------+-------------+------------+");
        System.out.println("| BK ID | USER ID   | BORROW DATE | DUE DATE    | FINE       |");
        System.out.println("+-------+-----------+-------------+-------------+------------+");

        boolean found = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");

        for (K2521153_Book b : inventory) {
            if (b.getDueDate() != null && b.getDueDate().before(currentDate)) {
                K2521153_User u = b.getCurrentHolder();
                if (u != null) {
                    long diff = currentDate.getTime() - b.getDueDate().getTime();
                    long diffInDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    double fine = u.getStrategy().calculateFine((int)diffInDays);

                   // Calculate borrow date
                    Calendar c = Calendar.getInstance();
                    c.setTime(b.getDueDate());
                    c.add(Calendar.DAY_OF_MONTH, -u.getStrategy().getBorrowingLimit());
                    Date borrowDate = c.getTime();

                    System.out.println(String.format("| %-5s | %-9s | %-11s | %-11s | LKR %-6s |",
                            b.getBookId(), u.getUserId(), sdf.format(borrowDate), sdf.format(b.getDueDate()), (int)fine));
                    found = true;
                }
            }
        }
        if(!found) System.out.println("| No overdue books found.                                    |");
        System.out.println("+-------+-----------+-------------+-------------+------------+");
    }

    public void notifyBookAvailability() {
        System.out.println("\n--- NOTIFICATION: BOOK AVAILABILITY ---");
        System.out.println("+-------+------------------------+--------------+");
        System.out.println("| ID    | BOOK NAME              | AVAILABILITY |");
        System.out.println("+-------+------------------------+--------------+");
        for (K2521153_Book b : inventory) {
            String dTitle = (b.getTitle().length() > 22) ? b.getTitle().substring(0, 19) + "..." : b.getTitle();
            System.out.println(String.format("| %-5s | %-22s | %-12s |", b.getBookId(), dTitle, b.getState().getStateName()));
        }
        System.out.println("+-------+------------------------+--------------+");
    }

    private void printTableDivider() {
        System.out.println("+-------+----------------------+-----------------+------------+---------------+------------+--------------------+");
    }
}
