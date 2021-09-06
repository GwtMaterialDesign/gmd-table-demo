package gmd.datatable.demo.client.generator.user;

public class User {

    private long id;
    private String name;
    private String email;
    private String phone;
    private String company;
    private String address;
    private String city;
    private String zipCode;
    private String image;
    private String category;
    private double salary;

    public User() {
    }

    public User(String image, String name, String email, String phone,
                String company, String address, String city, String zipCode, double salary) {
        this.image = image;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.company = company;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
