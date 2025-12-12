import java.util.Date;

class K2521153_CancelReservationCommand implements K2521153_Command {
    private K2521153_Book book;
    private K2521153_User user;
    public K2521153_CancelReservationCommand(K2521153_Book book, K2521153_User user) { this.book = book; this.user = user; }
    public void execute() { book.getState().cancelReservation(book, user); }
    public void undo() {
        // Simple undo logic: re-add to queue (Date is irrelevant for undo here)
        book.getState().reserveBook(book, user, new Date());
    }
}