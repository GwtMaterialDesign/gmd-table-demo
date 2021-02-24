package gmd.datatable.demo.client.generator.product;

public class ProductGenerator {

    public static Product generate() {
        return new Product(company(), productName(), productAdjective(), Double.parseDouble(price()), color(), productMaterial(), department());
    }

    protected static native String company() /*-{
        return $wnd.faker.company.companyName();
    }-*/;

    protected static native String productName() /*-{
        return $wnd.faker.commerce.productName();
    }-*/;

    protected static native String productAdjective() /*-{
        return $wnd.faker.commerce.productAdjective();
    }-*/;

    protected static native String price() /*-{
        return $wnd.faker.commerce.price();
    }-*/;

    protected static native String color() /*-{
        return $wnd.faker.commerce.color();
    }-*/;

    protected static native String productMaterial() /*-{
        return $wnd.faker.commerce.productMaterial();
    }-*/;

    protected static native String department() /*-{
        return $wnd.faker.commerce.department();
    }-*/;
}
