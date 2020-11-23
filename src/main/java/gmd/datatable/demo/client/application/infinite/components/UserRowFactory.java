package gmd.datatable.demo.client.application.infinite.components;

import gmd.datatable.demo.client.generator.user.User;
import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.factory.CategoryPair;
import gwt.material.design.client.data.factory.RowComponentFactory;

public class UserRowFactory extends RowComponentFactory<User> {

    @Override
    public RowComponent<User> generate(DataView dataView, User model) {
        // We won't change the way it loads the RowComponent
        return super.generate(dataView, model);
    }

    @Override
    public CategoryPair getCategory(User model) {
        // We want to override the standard category retrieval
        // This is where we can define a models category.
        // This is useful when we don't want to pollute our
        // object models with the interface HasDataCategory.
        return new CategoryPair(model.getCategory());
    }
}
