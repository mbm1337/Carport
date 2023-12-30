package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private int id;
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
    private String userEmail;
    private String firstname;
    private String lastname;
    private int zip;
    private String address;
    private boolean isAdmin;

    private double ShedLength;
    private double ShedWidth;
    private boolean ShedSide;

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    List<Admin> adminList;

    public double getShedLength() {
        return ShedLength;
    }

    public void setShedLength(double shedLength) {
        ShedLength = shedLength;
    }

    public double getShedWidth() {
        return ShedWidth;
    }

    public void setShedWidth(double shedWidth) {
        ShedWidth = shedWidth;
    }

    public boolean isShedSide() {
        return ShedSide;
    }

    public void setShedSide(boolean shedSide) {
        ShedSide = shedSide;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Admin(int id, int orderId, String orderDate, String status, String comments, int userId, double orderPrice, int materialsId, int quantityOrdered, double detailPrice, int length, int width, String productName, String productType, String productSize, String unit, int quantityInStock, double sellPrice, double purchasePrice, String userEmail, String firstname, String lastname, int zip, String address, boolean isAdmin, String password, int phone) {
        this.id = id;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.comments = comments;
        this.userId = userId;
        this.orderPrice = orderPrice;
        this.materialsId = materialsId;
        this.quantityOrdered = quantityOrdered;
        this.detailPrice = detailPrice;
        this.length = length;
        this.width = width;
        this.productName = productName;
        this.productType = productType;
        this.productSize = productSize;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
        this.sellPrice = sellPrice;
        this.purchasePrice = purchasePrice;
        this.userEmail = userEmail;
        this.firstname = firstname;
        this.lastname = lastname;
        this.zip = zip;
        this.address = address;
        this.isAdmin = isAdmin;
        this.password = password;
        this.phone = phone;
    }

    private String password;
    private int phone;

    public Admin() {
        adminList = new ArrayList<>();

    }

    public Admin(int id, int materialsId, String comments) {
        this.id = id;
        this.materialsId = materialsId;
        this.comments = comments;
    }

    // Tilføj getters og setters efter behov

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(int materialsId) {
        this.materialsId = materialsId;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public double getDetailPrice() {
        return detailPrice;
    }

    public void setDetailPrice(double detailPrice) {
        this.detailPrice = detailPrice;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }


    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }


}
