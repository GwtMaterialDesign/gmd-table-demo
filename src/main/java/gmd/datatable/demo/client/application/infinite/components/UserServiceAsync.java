package gmd.datatable.demo.client.application.infinite.components;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface UserServiceAsync {

    void getUsers(int startIndex, int viewSize, List<String> categories, AsyncCallback<Users> async);

    void getCategories(AsyncCallback<List<String>> async);
}