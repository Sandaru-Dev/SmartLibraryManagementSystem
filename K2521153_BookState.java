import java.util.Date;

interface K2521153_BookState {
    void borrowBook(K2521153_Book book, K2521153_User user, Date date);
    void returnBook(K2521153_Book book, K2521153_User user, Date date);
    void reserveBook(K2521153_Book book, K2521153_User user, Date date);
    void cancelReservation(K2521153_Book book, K2521153_User user);
    String getStateName();
}