package gmd.datatable.demo.client.generator.product;

import java.util.Random;

public class ProductGenerator {

    public static Product generate() {
        double random = new Random().nextDouble();
        double tax = 0.0 + (random * (7.0 - 0.0));
        return new Product(company(), productName(), productAdjective(), Math.abs(Double.parseDouble(price())), Math.abs(tax), color(), productMaterial(), department());
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
