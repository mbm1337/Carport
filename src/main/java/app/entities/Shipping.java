package app.entities;

public class Shipping {

    private int zipCode;
    private int shippingPrice;
    private String shippingTime;

    public Shipping(int zipCode, int shippingPrice, String shippingTime) {
        this.zipCode = zipCode;
        this.shippingPrice = shippingPrice;
        this.shippingTime = shippingTime;
    }

    public int getZipCode() {
        return zipCode;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public String getShippingTime() {
        return shippingTime;
    }
}
