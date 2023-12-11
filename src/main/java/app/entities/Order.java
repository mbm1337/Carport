package app.entities;

public class Order {

    private int userId;
    private int orderdate;
    private String status;

    private String comment;

    private int price;
    private int length;


    private int width;

    public Order(int userId, String status, String comment, int price, int length, int width) {
        this.userId = userId;
        this.orderdate = orderdate;
        this.status = status;
        this.comment = comment;
        this.price = price;
        this.length = length;
        this.width = width;
    }

    public Order(String status, String comment, int price, int length, int width) {
        this.status = status;
        this.comment = comment;
        this.price = price;
        this.length = length;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }


    public String getStatus() {
        return status;
    }

    public Order(int userId) {
        this.userId = userId;
    }



    public int getUserId() {
        return userId;
    }

    public int getOrderdate() {
        return orderdate;
    }

    public String getComment() {
        return comment;
    }


    public int getPrice() {
        return price;
    }
}
