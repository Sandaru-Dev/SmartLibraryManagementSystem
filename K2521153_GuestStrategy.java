class K2521153_GuestStrategy implements K2521153_FineCalculationStrategy {
    public double calculateFine(int overdueDays) { return overdueDays * 100.0; }
    public int getBorrowingLimit() { return 7; } // Defaulting Guest to 7 days
    public String getType() { return "Guest"; }
}