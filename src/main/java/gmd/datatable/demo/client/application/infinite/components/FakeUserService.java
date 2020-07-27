package gmd.datatable.demo.client.application.infinite.components;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gmd.datatable.demo.client.generator.DataGenerator;
import gmd.datatable.demo.client.generator.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeUserService implements UserServiceAsync {

    private static Map<String, List<User>> map = new HashMap<>();
    private boolean initialLoad = false;

    static {
        map.put("Category 1", new DataGenerator().generateUsers(25, "Category 1"));
        map.put("Category 2", new DataGenerator().generateUsers(25, "Category 2"));
        map.put("Category 3", new DataGenerator().generateUsers(25, "Category 3"));
        map.put("Category 4", new DataGenerator().generateUsers(25, "Category 4"));
        map.put("Category 5", new DataGenerator().generateUsers(25, "Category 5"));
    }

    @Override
    public void getUsers(int startIndex, int viewSize, List<String> categories, AsyncCallback<Users> async) {
        List<User> flatData = new ArrayList<>();
        if(categories == null) {
            // Load all data
            for(List<User> users : map.values()) {
                flatData.addAll(users);
            }
        } else {
            // Load data by categories
            for (String category : categories) {
                flatData.addAll(map.get(category));
            }
        }

        Users users = new Users();
        for(int i = startIndex; i < (startIndex + viewSize); i++) {
            try {
                users.add(flatData.get(i));
            } catch (IndexOutOfBoundsException e) {
                // ignored.
            }
        }
        users.setAbsoluteTotal(flatData.size());

        // Fake a delay for the demo
        new Timer() {
            @Override
            public void run() {
                async.onSuccess(users);
            }
        }.schedule(Math.min(200, Random.nextInt(500)));
    }

    @Override
    public void getCategories(AsyncCallback<List<String>> async) {
        new Timer() {
            @Override
            public void run() {
                async.onSuccess(new ArrayList<>(map.keySet()));
            }
        }.schedule(Math.min(200, Random.nextInt(500)));
    }
}