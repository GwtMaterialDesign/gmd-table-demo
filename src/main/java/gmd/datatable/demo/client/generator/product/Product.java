package gmd.datatable.demo.client.generator.product;

public class Product {

    private String date;
    private String productName;
    private String productAdjective;
    private String price;
    private String color;
    private String productMaterial;
    private String department;

    public Product(String date, String productName, String productAdjective, String price, String color, String productMaterial, String department) {
        this.date = date;
        this.productName = productName;
        this.productAdjective = productAdjective;
        this.price = price;
        this.color = color;
        this.productMaterial = productMaterial;
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAdjective() {
        return productAdjective;
    }

    public void setProductAdjective(String productAdjective) {
        this.productAdjective = productAdjective;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
