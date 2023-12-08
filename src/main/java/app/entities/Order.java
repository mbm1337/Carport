package app.entities;

public class Order {
    private int orderNr;
    private int userId;
    private int orderdate;
    private String status;

    private String comment;
    int kundenummer;
    private int price;

    public String getStatus() {
        return status;
    }

    public Order(int userId) {
        this.userId = userId;
    }
}
