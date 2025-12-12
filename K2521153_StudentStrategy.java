class K2521153_StudentStrategy implements K2521153_FineCalculationStrategy {
    public double calculateFine(int overdueDays) { return overdueDays * 50.0; }
    public int getBorrowingLimit() { return 14; }
    public String getType() { return "Student"; }
}