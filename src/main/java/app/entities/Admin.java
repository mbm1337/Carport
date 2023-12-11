package app.entities;

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

    // Konstruktør
    public Admin(int orderId, String orderDate, String status, String comments,
                 int userId, double orderPrice, int materialsId, int quantityOrdered, double detailPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.comments = comments;
        this.userId = userId;
        this.orderPrice = orderPrice;
        this.materialsId = materialsId;
        this.quantityOrdered = quantityOrdered;
        this.detailPrice = detailPrice;
    }

    public Admin() {

    }
    public Admin(int id,int materialsId, String comments) {
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
}
