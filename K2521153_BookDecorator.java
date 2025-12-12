class K2521153_BookDecorator extends K2521153_Book {
    protected K2521153_Book decoratedBook;
    public K2521153_BookDecorator(K2521153_Book book) {
        super(new K2521153_Book.K2521153_BookBuilder(book.getBookId(), book.getTitle())
                .setAuthor(book.getAuthor())
                .setCategory(book.getCategory())
                .setIsbn(book.getIsbn()));
        this.decoratedBook = book;
    }
}