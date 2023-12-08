package app.entities;

public class Material {
    private int id;
    private String productName;
    private String productType;
    private String productSize;
    private String unit;
    private short quantityInStock;
    private double buyPrice;
    private double purchasePrice;

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductSize() {
        return productSize;
    }

    public String getUnit() {
        return unit;
    }

    public short getQuantityInStock() {
        return quantityInStock;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    // Constructor
    public Material(int id, String productName, String productType, String productSize,
                    String unit, short quantityInStock, double buyPrice, double purchasePrice) {
        this.id = id;
        this.productName = productName;
        this.productType = productType;
        this.productSize = productSize;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.purchasePrice = purchasePrice;
    }

    public Material(String productName, String productType, String productSize,
                    String unit, short quantityInStock, double buyPrice, double purchasePrice) {
        this.productName = productName;
        this.productType = productType;
        this.productSize = productSize;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.purchasePrice = purchasePrice;
    }
    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", productSize='" + productSize + '\'' +
                ", unit='" + unit + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", buyPrice=" + buyPrice +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}
