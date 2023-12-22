package app.entities;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int phoneNumber; // Changed to int
    private String email;
    private int zip;
    private String address;
    private boolean admin;
    private String password;


    public User(int id, String firstName, String lastName, int phoneNumber, String email, int zip, String address, boolean admin, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.zip = zip;
        this.address = address;
        this.admin = admin;
        this.password = password;
    }

    public User(int id, String email, String firstName, String lastName, int phoneNumber, int zip, String address, boolean admin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.zip = zip;
        this.address = address;
        this.admin = admin;
    }

    public User(int id, String email) {
        this.id = id;
        this.email = email;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setPassword(String password) {
        this.password = password;
    }
// ... other getter methods ...

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", zip=" + zip +
                ", address='" + address + '\'' +
                ", admin=" + admin +
                ", password='" + password + '\'' +
                '}';
    }


}
