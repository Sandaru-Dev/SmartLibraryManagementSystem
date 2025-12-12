import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class K2521153_Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;

    private K2521153_BookState state;
    private K2521153_User currentHolder;
    private Date dueDate;
    private Queue<K2521153_User> reservationQueue;
    private int borrowCount = 0; 

    private String review;
    private List<String> tags;

    protected K2521153_Book(K2521153_BookBuilder builder) {
        this.bookId = builder.bookId;
        this.title = builder.title;
        this.author = builder.author;
        this.category = builder.category;
        this.isbn = builder.isbn;
        this.review = builder.review;
        this.tags = builder.tags;

        this.state = new K2521153_AvailableState();
        this.reservationQueue = new LinkedList<>();
    }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }

    public void setState(K2521153_BookState state) { this.state = state; }
    public K2521153_BookState getState() { return state; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public String getBookId() { return bookId; }
    public K2521153_User getCurrentHolder() { return currentHolder; }
    public void setCurrentHolder(K2521153_User u) { this.currentHolder = u; }
    public void setDueDate(Date d) { this.dueDate = d; }
    public Date getDueDate() { return dueDate; }
    public Queue<K2521153_User> getReservationQueue() { return reservationQueue; }

    // Methods for borrow count ---
    public void incrementBorrowCount() { this.borrowCount++; }
    public int getBorrowCount() { return borrowCount; }

    public String getDetails() {
        String info = (currentHolder != null) ? "Held by " + currentHolder.getName() : "";

        String dTitle = (title.length() > 20) ? title.substring(0, 17) + "..." : title;
        String dAuthor = (author != null && author.length() > 15) ? author.substring(0, 12) + "..." : (author == null ? "-" : author);
        String dCat = (category != null && category.length() > 10) ? category.substring(0, 7) + "..." : (category == null ? "-" : category);

        return String.format("| %-5s | %-20s | %-15s | %-10s | %-13s | %-10s | %-18s |",
                bookId, dTitle, dAuthor, dCat, (isbn==null?"-":isbn), state.getStateName(), info);
    }

    public static class K2521153_BookBuilder {
        private String bookId;
        private String title;
        private String author;
        private String category;
        private String isbn;
        private String review;
        private List<String> tags;

        public K2521153_BookBuilder(String bookId, String title) {
            this.bookId = bookId;
            this.title = title;
        }
        public K2521153_BookBuilder setAuthor(String author) { this.author = author; return this; }
        public K2521153_BookBuilder setCategory(String category) { this.category = category; return this; }
        public K2521153_BookBuilder setIsbn(String isbn) { this.isbn = isbn; return this; }
        public K2521153_Book build() { return new K2521153_Book(this); }
    }
}
