import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class K2521153_ReservedState implements K2521153_BookState {
    public void borrowBook(K2521153_Book book, K2521153_User user, Date borrowDate) {
        if (book.getCurrentHolder() != null && book.getCurrentHolder().equals(user)) {
            book.setState(new K2521153_BorrowedState());

            // Increment Borrow Count
            book.incrementBorrowCount();

            // Calculate Due Date
            Calendar c = Calendar.getInstance();
            c.setTime(borrowDate);
            int daysLimit = user.getStrategy().getBorrowingLimit();
            c.add(Calendar.DAY_OF_MONTH, daysLimit);
            book.setDueDate(c.getTime());

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            System.out.println("   [RESERVATION FULFILLED] Issued to: " + user.getName());
            System.out.println("                           Borrow Date: " + sdf.format(borrowDate));
            System.out.println("                           Due Date:    " + sdf.format(book.getDueDate()));
        } else {
            System.out.println("   [ERROR] Book is reserved for someone else.");
        }
    }
    public void returnBook(K2521153_Book book, K2521153_User user, Date date) { System.out.println("   [ERROR] Book is awaiting pickup."); }
    public void reserveBook(K2521153_Book book, K2521153_User user, Date date) {
        book.getReservationQueue().add(user);
        System.out.println("   [SUCCESS] Added to reservation queue.");
    }
    public void cancelReservation(K2521153_Book book, K2521153_User user) {
        if (book.getCurrentHolder() != null && book.getCurrentHolder().equals(user)) {
            System.out.println("   [CANCELED] Reservation canceled. Book is now Available.");
            book.setState(new K2521153_AvailableState());
            book.setCurrentHolder(null);
        } else if (book.getReservationQueue().remove(user)) {
            System.out.println("   [SUCCESS] Reservation removed from queue.");
        } else {
            System.out.println("   [ERROR] User has no active reservation.");
        }
    }
    public String getStateName() { return "Reserved"; }
}