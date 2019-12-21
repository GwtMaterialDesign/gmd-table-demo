package gmd.datatable.demo.client.application.infinite.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gmd.datatable.demo.client.generator.user.User;
import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataSource implements DataSource<User> {

    private final UserServiceAsync UserService;

    public UserDataSource(UserServiceAsync UserService) {
        this.UserService = UserService;
    }

    @Override
    public void load(LoadConfig<User> loadConfig, LoadCallback<User> callback) {
        List<CategoryComponent> categories = loadConfig.getOpenCategories();

        List<String> categoryNames = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            categoryNames.add("Category " + i);
        }

        UserService.getUsers(loadConfig.getOffset(), loadConfig.getLimit(), categoryNames,
            new AsyncCallback<Users>() {
                @Override
                public void onSuccess(Users Users) {
                    callback.onSuccess(new LoadResult<>(Users, loadConfig.getOffset(), Users.getAbsoluteTotal()));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    GWT.log("Getting Users async call failed.", throwable);
                    callback.onFailure(throwable);
                }
            });
    }

    @Override
    public boolean useRemoteSort() {
        return false;
    }
}