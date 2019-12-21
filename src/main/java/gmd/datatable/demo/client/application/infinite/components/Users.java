package gmd.datatable.demo.client.application.infinite.components;

import gmd.datatable.demo.client.generator.user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Users extends ArrayList<User> implements Serializable {

    private int absoluteTotal = 0;

    public Users() {
    }

    public void setAbsoluteTotal(int absoluteTotal) {
        this.absoluteTotal = absoluteTotal;
    }

    public int getAbsoluteTotal() {
        return absoluteTotal;
    }
}