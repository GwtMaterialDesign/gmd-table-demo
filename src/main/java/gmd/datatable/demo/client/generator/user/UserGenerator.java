package gmd.datatable.demo.client.generator.user;

public class UserGenerator {

    public static User generate() {
        return new User(image(), name(), email(), phone(), company(), address(), city(), zipCode(), Double.parseDouble(salary()));
    }

    protected static String image() {
        return "https://i.pinimg.com/originals/7c/c7/a6/7cc7a630624d20f7797cb4c8e93c09c1.png";
    }

    protected static native String name() /*-{
        return $wnd.faker.name.findName();
    }-*/;

    protected static native String email() /*-{
        return $wnd.faker.internet.email();
    }-*/;

    protected static native String phone() /*-{
        return $wnd.faker.phone.phoneNumber();
    }-*/;

    protected static native String company() /*-{
        return $wnd.faker.company.companyName();
    }-*/;

    protected static native String address() /*-{
        return $wnd.faker.address.streetAddress();
    }-*/;

    protected static native String city() /*-{
        return $wnd.faker.address.city();
    }-*/;

    protected static native String zipCode() /*-{
        return $wnd.faker.address.zipCode();
    }-*/;

    protected static native String salary() /*-{
        return $wnd.faker.finance.amount();
    }-*/;
}
