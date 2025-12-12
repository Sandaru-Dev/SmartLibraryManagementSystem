class K2521153_FacultyStrategy implements K2521153_FineCalculationStrategy {
    public double calculateFine(int overdueDays) { return overdueDays * 20.0; }
    public int getBorrowingLimit() { return 30; }
    public String getType() { return "Faculty"; }
}