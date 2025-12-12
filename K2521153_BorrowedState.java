import java.text.SimpleDateFormat;
import java.util.Date;

class K2521153_BorrowedState implements K2521153_BookState {
    public void borrowBook(K2521153_Book book, K2521153_User user, Date date) { System.out.println("   [ERROR] Book is currently borrowed."); }

    public void returnBook(K2521153_Book book, K2521153_User user, Date returnDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        System.out.println("   [PROCESSING] Return from " + user.getName() + " on " + sdf.format(returnDate));

        // Reservations Handling
        if (!book.getReservationQueue().isEmpty()) {
            K2521153_User nextUser = book.getReservationQueue().poll();
            book.setState(new K2521153_ReservedState());
            book.setCurrentHolder(nextUser);
            nextUser.update("Good news! The book '" + book.getTitle() + "' is returned and RESERVED for you.");
            System.out.println("             Status: Book is now RESERVED for " + nextUser.getName());
        } else {
            book.setState(new K2521153_AvailableState());
            book.setCurrentHolder(null);
            book.setDueDate(null);
            System.out.println("             Status: Book is now AVAILABLE on shelves.");
        }
    }

    public void reserveBook(K2521153_Book book, K2521153_User user, Date date) {
        book.getReservationQueue().add(user);
        System.out.println("   [SUCCESS] Reservation placed for " + user.getName());
        System.out.println("             Queue Position: #" + book.getReservationQueue().size());
    }

    public void cancelReservation(K2521153_Book book, K2521153_User user) {
        if(book.getReservationQueue().remove(user)) System.out.println("   [SUCCESS] Reservation canceled for " + user.getName());
        else System.out.println("   [ERROR] User does not have a reservation.");
    }
    public String getStateName() { return "Borrowed"; }
}
