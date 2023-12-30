package app.entities;

public class OrderDetail {
    int orderNumber;
    int quantityordered;
    int materialsId;
    String productName;

    public OrderDetail(int orderNumber, int quantityordered, int materialsId, String productName) {
        this.orderNumber = orderNumber;
        this.quantityordered = quantityordered;
        this.materialsId = materialsId;
        this.productName = productName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getQuantityordered() {
        return quantityordered;
    }

    public void setQuantityordered(int quantityordered) {
        this.quantityordered = quantityordered;
    }

    public int getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(int materialsId) {
        this.materialsId = materialsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
