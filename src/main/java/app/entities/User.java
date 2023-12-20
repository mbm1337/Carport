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
    private int orderId;
    private String orderDate;
    private String status;
    private String comments;
    private int userId;
    private double orderPrice;
    private int materialsId;
    private int quantityOrdered;
    private double detailPrice;
    private int length;
    private int width;
    private String productName;
    private String productType;
    private String productSize;
    private String unit;
    private int quantityInStock;
    private double sellPrice;
    private double purchasePrice;

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

    public User(int id, String email, String firstName, String lastName, int phoneNumber, int zip, String address) {
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

    public User() {

    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setMaterialsId(int materialsId) {
        this.materialsId = materialsId;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public void setDetailPrice(double detailPrice) {
        this.detailPrice = detailPrice;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
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
