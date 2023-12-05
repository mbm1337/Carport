package app.entities;

public class Shipping {

    private String zip;
    private int shippingPrice;

    public Shipping(String zip, int shippingPrice) {
        this.zip = zip;
        this.shippingPrice = shippingPrice;
    }

    public String getZip() {
        return zip;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }
}
