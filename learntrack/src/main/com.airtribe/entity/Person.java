package entity;


public class Person {

    private final int id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean active;

    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s  | Email: %-20s",
                getId(),
                getFirstName() + " " + getLastName(),
                getEmail()
        );
    }
}
