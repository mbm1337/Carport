package app.entities;

public class StandardCarport {

    private int id;
    private String merchandiser;
    private String productName;
    private int carportPrice;
    private String description;

    public StandardCarport(int id, String merchandiser, String productName, int carportPrice, String description)  {
        this.id = id;
        this.merchandiser = merchandiser;
        this.productName = productName;
        this.carportPrice = carportPrice;
        this.description = description;
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

    public int getCarportPrice() {
        return carportPrice;
    }

    public String getDescription() {
        return description;
    }
}