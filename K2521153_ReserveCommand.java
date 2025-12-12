import java.util.Date;

class K2521153_ReserveCommand implements K2521153_Command {
    private K2521153_Book book;
    private K2521153_User user;
    private Date date;
    public K2521153_ReserveCommand(K2521153_Book book, K2521153_User user, Date date) {
        this.book = book; this.user = user; this.date = date;
    }
    public void execute() { book.getState().reserveBook(book, user, date); }
    public void undo() { book.getState().cancelReservation(book, user); }
}