package db.model;

public class CustomerRecord {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String username;

    public CustomerRecord(int id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public int id() { return id; }
    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String username() { return username; }
}
