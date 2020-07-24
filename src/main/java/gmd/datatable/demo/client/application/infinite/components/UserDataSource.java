package gmd.datatable.demo.client.application.infinite.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gmd.datatable.demo.client.generator.user.User;
import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import gwt.material.design.client.data.loader.LoadResult;

public class UserDataSource implements DataSource<User> {

    private final UserServiceAsync userService;

    public UserDataSource(UserServiceAsync UserService) {
        this.userService = UserService;
    }

    @Override
    public void load(LoadConfig<User> loadConfig, LoadCallback<User> callback) {
        userService.getUsers(loadConfig.getOffset(), loadConfig.getLimit(),
            new AsyncCallback<Users>() {
                @Override
                public void onSuccess(Users users) {
                    callback.onSuccess(new LoadResult<>(users, loadConfig.getOffset(), users.getAbsoluteTotal()));
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