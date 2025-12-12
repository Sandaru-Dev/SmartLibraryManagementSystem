import java.util.*;
import java.text.SimpleDateFormat;

public class K2521153_Main{
    private static Scanner scanner = new Scanner(System.in);
    private static K2521153_LibrarySystem lib = new K2521153_LibrarySystem();

    public static void main(String[] args) {
        seedData();

        while (true) {
            System.out.println("\n=========================================");
            System.out.println("      LIBRARIAN CONTROL PANEL");
            System.out.println("=========================================");
            System.out.println("1. User Management");
            System.out.println("2. Book Management");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Reserve Book");
            System.out.println("6. Cancel Reservation");
            System.out.println("7. Reports Section");
            System.out.println("8. Notifications");
            System.out.println("0. Exit");
            System.out.print("Select Option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": handleUserManagement(); break;
                case "2": handleBookManagement(); break;
                case "3": handleBorrow(); break;
                case "4": handleReturn(); break;
                case "5": handleReserve(); break;
                case "6": handleCancelReserve(); break;
                case "7": handleReports(); break;
                case "8": handleNotifications(); break;
                case "0": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // DATE PARSING HELPER
    private static Date getDateInput(String prompt) {
        System.out.print(prompt + " (dd-MM-yyyy): ");
        String dateStr = scanner.nextLine();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            System.out.println("   [ERROR] Invalid Date Format. Using System Date.");
            return new Date();
        }
    }

    // USER MANAGEMENT MENU

    private static void handleUserManagement() {
        System.out.println("\n--- USER MANAGEMENT ---");
        System.out.println("1. Add User");
        System.out.println("2. Edit User");
        System.out.println("3. View Users");
        System.out.println("4. Delete User");
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String c = scanner.nextLine();

        if(c.equals("1")) {
            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print("Name: "); String name = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.print("Contact: "); String contact = scanner.nextLine();
            System.out.print("Type (1.Student 2.Faculty 3.Guest): "); String t = scanner.nextLine();

            K2521153_FineCalculationStrategy s = t.equals("2") ? new K2521153_FacultyStrategy() : (t.equals("3") ? new K2521153_GuestStrategy() : new K2521153_StudentStrategy());

            lib.registerUser(new K2521153_User(id, name, email, contact, s));
            System.out.println("User added.");
        }
        else if (c.equals("2")) {
            System.out.print("Enter User ID to Edit: "); String id = scanner.nextLine();
            K2521153_User u = lib.findUser(id);
            if(u!=null) {
                System.out.print("New Name (Current: "+u.getName()+"): ");
                String n = scanner.nextLine(); if(!n.isEmpty()) u.setName(n);

                System.out.print("New Email (Current: "+u.getEmail()+"): ");
                String e = scanner.nextLine(); if(!e.isEmpty()) u.setEmail(e);

                System.out.print("New Contact (Current: "+u.getContactNumber()+"): ");
                String ct = scanner.nextLine(); if(!ct.isEmpty()) u.setContactNumber(ct);

                System.out.println("User updated.");
            } else System.out.println("User not found.");
        }
        else if (c.equals("3")) {
            System.out.println("\n--- LIST OF USERS ---");
            System.out.println("+-------+----------------------+---------------------------+---------------+-----------+");
            System.out.println("| ID    | NAME                 | EMAIL                     | CONTACT       | TYPE      |");
            System.out.println("+-------+----------------------+---------------------------+---------------+-----------+");
            for(K2521153_User u : lib.getAllUsers()) {
                String type = u.getStrategy().getType();
                System.out.println(String.format("| %-5s | %-20s | %-25s | %-13s | %-9s |",
                        u.getUserId(), u.getName(), u.getEmail(), u.getContactNumber(), type));
            }
            System.out.println("+-------+----------------------+---------------------------+---------------+-----------+");
        }
        else if (c.equals("4")) {
            System.out.print("Enter User ID to Delete: "); String id = scanner.nextLine();
            K2521153_User u = lib.findUser(id);
            if(u!=null) { lib.removeUser(u); System.out.println("User deleted."); }
            else System.out.println("User not found.");
        }
    }


     // BOOK MANAGEMENT MENU
    private static void handleBookManagement() {
        System.out.println("\n--- BOOK MANAGEMENT ---");
        System.out.println("1. Add Book");
        System.out.println("2. Edit Book");
        System.out.println("3. View Books");
        System.out.println("4. Delete Book");
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String c = scanner.nextLine();

        if(c.equals("1")) {
            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print("Title: "); String title = scanner.nextLine();
            System.out.print("Author: "); String author = scanner.nextLine();
            System.out.print("Category: "); String cat = scanner.nextLine();
            System.out.print("ISBN: "); String isbn = scanner.nextLine();
            System.out.print("Featured? (y/n): "); String f = scanner.nextLine();

            K2521153_Book b = new K2521153_Book.K2521153_BookBuilder(id, title)
                    .setAuthor(author)
                    .setCategory(cat)
                    .setIsbn(isbn)
                    .build();

            if(f.equalsIgnoreCase("y")) b = new K2521153_FeaturedBook(b);
            lib.addBook(b);
            System.out.println("Book added.");
        }
        else if (c.equals("2")) {
            System.out.print("Enter Book ID to Edit: "); String id = scanner.nextLine();
            K2521153_Book b = lib.findBook(id);
            if(b!=null) {
                System.out.print("New Title (Curr: "+b.getTitle()+"): ");
                String t = scanner.nextLine(); if(!t.isEmpty()) b.setTitle(t);

                System.out.print("New Author (Curr: "+b.getAuthor()+"): ");
                String a = scanner.nextLine(); if(!a.isEmpty()) b.setAuthor(a);

                System.out.print("New Category (Curr: "+b.getCategory()+"): ");
                String cat = scanner.nextLine(); if(!cat.isEmpty()) b.setCategory(cat);

                System.out.println("Book updated.");
            } else System.out.println("Book not found.");
        }
        else if (c.equals("3")) {
            lib.reportAllBooks();
        }
        else if (c.equals("4")) {
            System.out.print("Enter Book ID to Delete: "); String id = scanner.nextLine();
            K2521153_Book b = lib.findBook(id);
            if(b!=null) { lib.removeBook(b); System.out.println("Book deleted."); }
            else System.out.println("Book not found.");
        }
    }

    private static void handleReports() {
        System.out.println("\n--- REPORTS SECTION ---");
        System.out.println("1. View All Books (Inventory)");
        System.out.println("2. View Active Borrowers");
        System.out.println("3. View Overdue Books (System Time)");
        System.out.println("4. Most Borrowed Books"); 
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String c = scanner.nextLine();

        if(c.equals("1")) lib.reportAllBooks();
        if(c.equals("2")) lib.reportActiveBorrowers();
        if(c.equals("3")) lib.reportOverdueBooks();
        if(c.equals("4")) lib.reportMostBorrowedBooks(); 
    }

    private static void handleNotifications() {
        System.out.println("\n--- NOTIFICATIONS ---");
        System.out.println("1. Overdue Books");
        System.out.println("2. Book Availability");
        System.out.println("0. Back");
        System.out.print("Choice: ");
        String c = scanner.nextLine();

        if(c.equals("1")) {
            Date d = getDateInput("Enter Current Verification Date");
            lib.notifyOverdueBooks(d);
        }
        if(c.equals("2")) {
            lib.notifyBookAvailability();
        }
    }



    private static void handleBorrow() {
        System.out.print("User ID: "); String u = scanner.nextLine();
        System.out.print("Book ID: "); String b = scanner.nextLine();
        if(validate(u,b)) {
            Date d = getDateInput("Enter Borrow Date");
            lib.executeAction(new K2521153_BorrowCommand(lib.findBook(b), lib.findUser(u), d));
        }
    }

    private static void handleReturn() {
        System.out.print("User ID: "); String u = scanner.nextLine();
        System.out.print("Book ID: "); String b = scanner.nextLine();
        if(validate(u,b)) {
            Date d = getDateInput("Enter Return Date");
            lib.executeAction(new K2521153_ReturnCommand(lib.findBook(b), lib.findUser(u), d));
        }
    }

    private static void handleReserve() {
        System.out.print("User ID: "); String u = scanner.nextLine();
        System.out.print("Book ID: "); String b = scanner.nextLine();
        if(validate(u,b)) {
            Date d = getDateInput("Enter Reservation Date");
            lib.executeAction(new K2521153_ReserveCommand(lib.findBook(b), lib.findUser(u), d));
        }
    }

    private static void handleCancelReserve() {
        System.out.print("User ID: "); String u = scanner.nextLine();
        System.out.print("Book ID: "); String b = scanner.nextLine();
        if(validate(u,b)) lib.executeAction(new K2521153_CancelReservationCommand(lib.findBook(b), lib.findUser(u)));
    }

    private static boolean validate(String uid, String bid) {
        if(lib.findUser(uid) == null || lib.findBook(bid) == null) {
            System.out.println(" [ERROR] Invalid User ID or Book ID");
            return false;
        }
        return true;
    }

    private static void seedData() {
        lib.registerUser(new K2521153_User("u1", "Kamal", "kamal@esu.lk", "0771234567", new K2521153_StudentStrategy()));
        lib.registerUser(new K2521153_User("u2", "Dr. Perera", "perera@uni.lk", "0717654321", new K2521153_FacultyStrategy()));
        lib.registerUser(new K2521153_User("u3", "John (Guest)", "john@gmail.com", "0700000000", new K2521153_GuestStrategy()));

        K2521153_Book b1 = new K2521153_Book.K2521153_BookBuilder("B1", "Tintin")
                .setAuthor("Herge")
                .setCategory("Adventure")
                .setIsbn("555-0201633610")
                .build();

        K2521153_Book b2 = new K2521153_Book.K2521153_BookBuilder("B2", "Harry Potter 1")
                .setAuthor("JK Rowling")
                .setCategory("Fantasy")
                .setIsbn("123-0132350884")
                .build();

        lib.addBook(new K2521153_FeaturedBook(b1));
        lib.addBook(b2);
    }
}