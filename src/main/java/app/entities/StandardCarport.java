package app.entities;

public class StandardCarport {
    private int id;
    private String merchandiser;
    private String productName;
    private int price;
    private int shippingDays;
    private double shippingPrice;
    private int zip;

    public StandardCarport(int id, String merchandiser, String productName, int price, int shippingDays, double shippingPrice) {
        this.id = id;
        this.merchandiser = merchandiser;
        this.productName = productName;
        this.price = price;
        this.shippingDays = shippingDays;
        this.shippingPrice = shippingPrice;
    }

    // Constructor for the StandardCarportMapper
    public StandardCarport(int zip, int shippingDays, double shippingPrice) {
        this.zip = City.zip;
        this.shippingDays = shippingDays;
        this.shippingPrice = shippingPrice;
    }

    public int getId() {
        return id;
    }

    public String getMerchandiser() {
        return merchandiser;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getShippingDays() {
        return shippingDays;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }
}
