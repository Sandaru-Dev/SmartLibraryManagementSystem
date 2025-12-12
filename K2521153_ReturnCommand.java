import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class K2521153_ReturnCommand implements K2521153_Command {
    private K2521153_Book book;
    private K2521153_User user;
    private Date returnDate;

    public K2521153_ReturnCommand(K2521153_Book book, K2521153_User user, Date returnDate) {
        this.book = book; this.user = user; this.returnDate = returnDate;
    }

    public void execute() {
        //  fine Calculation
        if (book.getDueDate() != null) {
            long diffInMillis = returnDate.getTime() - book.getDueDate().getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

            if (diffInDays > 0) {
                double fine = user.getStrategy().calculateFine((int)diffInDays);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

                System.out.println("   !!! OVERDUE RETURN !!!");
                System.out.println("       Due Date:    " + sdf.format(book.getDueDate()));
                System.out.println("       Return Date: " + sdf.format(returnDate));
                System.out.println("       Days Late:   " + diffInDays);
                System.out.println("       Rate:        LKR " + (fine/diffInDays) + "/day (" + user.getStrategy().getType() + ")");
                System.out.println("       TOTAL FINE:  LKR " + fine);

                user.update("You have been fined LKR " + fine + " for returning '" + book.getTitle() + "' late.");
            } else {
                System.out.println("   [INFO] Returned on time. No fines charged.");
            }
        }
        book.getState().returnBook(book, user, returnDate);
    }
    public void undo() { System.out.println("   [UNDO] Undo 'Return' is complex and disabled in this demo."); }
}