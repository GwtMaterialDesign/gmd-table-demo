package gmd.datatable.demo.client.generator;

import gmd.datatable.demo.client.resources.AppResources;
import gwt.material.design.client.MaterialDesign;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    static {
        MaterialDesign.injectJs(AppResources.INSTANCE.fakerJs());
    }

    public List<User> generate(int total) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            users.add(new User(firstName(), lastName(), email(), phone(), company(), address(), city(), country()));
        }
        return users;
    }

    protected static native String firstName() /*-{
        return $wnd.faker.name.findName();
    }-*/;

    protected static native String lastName() /*-{
        return $wnd.faker.name.lastName();
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

    protected static native String country() /*-{
        return $wnd.faker.address.country();
    }-*/;
}
