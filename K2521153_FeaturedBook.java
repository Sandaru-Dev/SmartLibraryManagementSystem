class K2521153_FeaturedBook extends K2521153_BookDecorator {
    public K2521153_FeaturedBook(K2521153_Book book) { super(book); }

    @Override
    public String getDetails() {
        String dTitle = "★ " + getTitle();
        if (dTitle.length() > 20) dTitle = dTitle.substring(0, 17) + "...";

        String dAuthor = (getAuthor() != null && getAuthor().length() > 15) ? getAuthor().substring(0, 12) + "..." : (getAuthor() == null ? "-" : getAuthor());
        String dCat = (getCategory() != null && getCategory().length() > 10) ? getCategory().substring(0, 7) + "..." : (getCategory() == null ? "-" : getCategory());
        String info = (getCurrentHolder() != null) ? "Held by " + getCurrentHolder().getName() : "FEATURED";

        return String.format("| %-5s | %-20s | %-15s | %-10s | %-13s | %-10s | %-18s |",
                getBookId(), dTitle, dAuthor, dCat, (getIsbn()==null?"-":getIsbn()), getState().getStateName(), info);
    }
}
