package gmd.datatable.demo.client.application.categorized.components;

import gmd.datatable.demo.client.generator.product.Product;
import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.factory.RowComponentFactory;

public class ProductRowFactory extends RowComponentFactory<Product> {

    @Override
    public RowComponent<Product> generate(DataView dataView, Product model) {
        // We won't change the way it loads the RowComponent
        return super.generate(dataView, model);
    }

    @Override
    public String getCategory(Product model) {
        // We want to override the standard category retrieval
        // This is where we can define a models category.
        // This is useful when we don't want to pollute our
        // object models with the interface HasDataCategory.
        return model.getDepartment();
    }
}
