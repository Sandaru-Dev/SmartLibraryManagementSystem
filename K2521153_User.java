class K2521153_User implements K2521153_Observer {
    private String userId;
    private String name;
    private String email;
    private String contactNumber;
    private K2521153_FineCalculationStrategy fineStrategy;

    public K2521153_User(String userId, String name, String email, String contactNumber, K2521153_FineCalculationStrategy strategy) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.fineStrategy = strategy;
    }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public void update(String message) {
        System.out.println("\n   [EMAIL TO " + email + " (" + name + ")]: " + message);
    }

    public String getName() { return name; }
    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getContactNumber() { return contactNumber; }
    public K2521153_FineCalculationStrategy getStrategy() { return fineStrategy; }
}
