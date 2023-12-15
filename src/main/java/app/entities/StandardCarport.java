package app.entities;

public class StandardCarport {

    private int id;
    private String merchandiser;
    private String productName;
    private int price;
    private String description;

    public StandardCarport(int id, String merchandiser, String productName, int price, String description)  {
        this.id = id;
        this.merchandiser = merchandiser;
        this.productName = productName;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}