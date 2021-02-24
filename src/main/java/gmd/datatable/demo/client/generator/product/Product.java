package gmd.datatable.demo.client.generator.product;

public class Product {

    private String company;
    private String productName;
    private String productAdjective;
    private double price;
    private String color;
    private String productMaterial;
    private String department;

    public Product(String company, String productName, String productAdjective, double price, String color, String productMaterial, String department) {
        this.company = company;
        this.productName = productName;
        this.productAdjective = productAdjective;
        this.price = price;
        this.color = color;
        this.productMaterial = productMaterial;
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
