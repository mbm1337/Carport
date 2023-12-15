package app.entities;


public class Order {

    private int orderNr;
    private int userId;
    private String status;
    private double price;
    private int orderNumber;
    private String productName;
    private  int quantityOrdered;
    private int length;
    private int width;
    private String comment;

    public Order(String status, int userId, int length, int width, String comments) {

        this.userId = userId;
        this.status = status;
        this.comment = comments;
        this.length = length;
        this.width = width;
    }


    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public String getComment() {
        return comment;
    }

    public Order(int orderNr, int userId, String status, int price) {
        this.orderNr = orderNr;
        this.userId = userId;
        this.status = status;
        this.price = price;
    }


    public Order(String status, double price, int length, int width) {
        this.status = status;
        this.price = price;
        this.length = length;
        this.width = width;
    }



    public Order(int userId, String status, int price) {
        this.userId = userId;
        this.status = status;
        this.price = price;
    }
    public Order(int orderNumber, String productName, int quantityOrdered, double price) {
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.quantityOrdered = quantityOrdered;
        this.price = price;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }
/*
    public String toString() {
        return "OrderNumber: " + orderNumber +
                ", ProductName: " + productName +
                ", QuantityOrdered: " + quantityOrdered +
                ", Price: " + price;
    }
*/

    public String toString() {
        return "Order{" +
                "orderNr=" + orderNr +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", orderNumber=" + orderNumber +
                ", productName='" + productName + '\'' +
                ", quantityOrdered=" + quantityOrdered +
                ", length=" + length +
                ", width=" + width +
                ", comment='" + comment + '\'' +
                '}';
    }

}
