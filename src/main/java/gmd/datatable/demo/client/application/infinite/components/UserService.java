package gmd.datatable.demo.client.application.infinite.components;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {

    Users getUsers(int startIndex, int viewSize, List<String> categories);

    List<String> getCategories();
}