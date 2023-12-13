package app.entities;


public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int zip;
    private String address;
    private boolean admin;
    private String password;

    public User(int id, String firstName, String lastName, String phoneNumber, String email, int zip, String address, boolean admin, String password) {
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

    public User(int id, String email, String firstName, String lastName, String phoneNumber, int zip, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.zip = zip;
        this.address = address;

    }




    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

    // constructor for login method in UserMapper
    public User(int id, String firstName, String lastName, boolean admin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admin = admin;
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

    public String getPhoneNumber() {
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
