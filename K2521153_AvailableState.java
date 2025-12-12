import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class K2521153_AvailableState implements K2521153_BookState {
    public void borrowBook(K2521153_Book book, K2521153_User user, Date borrowDate) {
        book.setState(new K2521153_BorrowedState());
        book.setCurrentHolder(user);

        // Increment Borrow Count
        book.incrementBorrowCount();

        // Calculate Due Date
        Calendar c = Calendar.getInstance();
        c.setTime(borrowDate);
        int daysLimit = user.getStrategy().getBorrowingLimit();
        c.add(Calendar.DAY_OF_MONTH, daysLimit);
        Date dueDate = c.getTime();
        book.setDueDate(dueDate);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        System.out.println("   [SUCCESS] Issued to: " + user.getName());
        System.out.println("             Borrow Date: " + sdf.format(borrowDate));
        System.out.println("             Limit:       " + daysLimit + " Days (" + user.getStrategy().getType() + ")");
        System.out.println("             Due Date:    " + sdf.format(dueDate));
    }
    public void returnBook(K2521153_Book book, K2521153_User user, Date returnDate) { System.out.println("   [ERROR] Book is already in the library."); }
    public void reserveBook(K2521153_Book book, K2521153_User user, Date date) { System.out.println("   [NOTE] Book is available. You can borrow it directly."); }
    public void cancelReservation(K2521153_Book book, K2521153_User user) { System.out.println("   [ERROR] No reservation to cancel."); }
    public String getStateName() { return "Available"; }
}