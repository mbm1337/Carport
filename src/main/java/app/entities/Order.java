package app.entities;

public class Order {

    private int orderNr;
    private int userId;
    private String status;
    private double price;
    private int orderNumber;
    private String productName;
    private  int quantityOrdered;


    public Order(int orderNr, int userId, String status, int price) {
        this.orderNr = orderNr;
        this.userId = userId;
        this.status = status;
        this.price = price;
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

    public String toString() {
        return "OrderNumber: " + orderNumber +
                ", ProductName: " + productName +
                ", QuantityOrdered: " + quantityOrdered +
                ", Price: " + price;
    }
}