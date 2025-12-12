import java.util.Date;

class K2521153_BorrowCommand implements K2521153_Command {
    private K2521153_Book book;
    private K2521153_User user;
    private Date date;

    public K2521153_BorrowCommand(K2521153_Book book, K2521153_User user, Date date) {
        this.book = book; this.user = user; this.date = date;
    }
    public void execute() { book.getState().borrowBook(book, user, date); }
    public void undo() {
        System.out.println("   [UNDO] Reverting Borrow...");
        book.setState(new K2521153_AvailableState());
        book.setCurrentHolder(null);
        book.setDueDate(null);
    }
}