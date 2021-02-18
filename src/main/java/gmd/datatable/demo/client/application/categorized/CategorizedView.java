/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2017 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package gmd.datatable.demo.client.application.categorized;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.application.categorized.components.CustomCategoryFactory;
import gmd.datatable.demo.client.application.categorized.components.CustomRenderer;
import gmd.datatable.demo.client.application.categorized.components.ProductRowFactory;
import gmd.datatable.demo.client.generator.product.Product;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.density.DisplayDensity;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.factory.CategoryMode;
import gwt.material.design.client.ui.MaterialListValueBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.ComputedColumn;
import gwt.material.design.client.ui.table.cell.DoubleColumn;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.jquery.client.api.JQueryElement;

import javax.inject.Inject;
import java.util.List;

public class CategorizedView extends ViewImpl implements CategorizedPresenter.MyView {

    interface Binder extends UiBinder<Widget, CategorizedView> {
    }

    @UiField
    MaterialDataTable<Product> table;

    @UiField
    MaterialPanel filterPanel;

    @UiField
    MaterialListValueBox<SelectionType> selectionType;

    @UiField
    MaterialListValueBox<CategoryMode> mode;

    @UiField
    MaterialListValueBox<DisplayDensity> density;

    @UiField
    MaterialTextBox tableName;

    @Inject
    CategorizedView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setupTable() {
        // We will manually add this category otherwise categories
        // can be loaded on the fly with HasDataCategory, or a custom
        // RowComponentFactory as demonstrated below
        //table.addCategory(new CustomCategoryComponent("Custom Category"));

        // We will define our own person row factory to generate the
        // category name. This can be used to generate your own
        // RowComponent's too, if custom functionality is required.
        table.setRowFactory(new ProductRowFactory());

        // If we want to generate all our categories using CustomCategoryComponent
        // We can define our own CategoryComponentFactory. There we can define our
        // own CategoryComponent implementations.
        table.setCategoryFactory(new CustomCategoryFactory());

        // It is possible to create your own custom renderer per table
        // When you use the BaseRenderer you can override certain draw
        // methods to create elements the way you would like.
        table.setRenderer(new CustomRenderer<>());

        table.addColumn(Product::getProductName, "Product Name")
            .sortable(true)
            .width("25%");

        table.addColumn(Product::getProductAdjective,"Adjective")
            .sortable(true)
            .width("15%");

        table.addColumn(Product::getColor, "Color")
            .sortable(true)
            .width("15%");

        table.addColumn(Product::getProductMaterial, "Material")
            .sortable(true)
            .width("15%");

        table.addColumn(Product::getCompany, "Company")
            .sortable(true)
            .width("20%");

        table.addColumn("Price", new DoubleColumn<Product>() {
            @Override
            public Double getValue(Product object) {
                return object.getPrice();
            }
        }
            .format(NumberFormat.getCurrencyFormat())
            .sortable(true)
            .width("10%"));

        table.addColumn("Computed", new ComputedColumn<Product, Double>() {
            @Override
            public Double compute(RowComponent<Product> row) {
                return super.compute(row);
            }
        });

        // Here we are adding a row expansion handler.
        // This is invoked when a row is expanded.
        table.addRowExpandingHandler(event -> {
            // Fake Async Task
            // This is demonstrating a fake asynchronous call to load
            // the data inside the expansion element.
            new Timer() {
                @Override
                public void run() {
                    // Clear the content first.
                    JQueryElement element = event.getExpansion().getContent().empty();
                    // Assign the jquery element to a GMD Widget
                    MaterialWidget content = new MaterialWidget(element);
                    content.setPadding(20);
                    content.add(new MaterialTitle("Expanded Panel", "Lorem ipsum dolor emet"));

                    // Hide the expansion elements overlay section.
                    // The overlay is retrieved using RowExpansion#getOverlay()
                    event.getExpansion().getOverlay().css("display", "none");
                }
            }.schedule(2000);
        });

        table.getCategories().forEach(categoryComponent -> {
            categoryComponent.setMode(CategoryMode.DISABLED);
        });
    }

    @Override
    public MaterialWidget getSideContent() {
        return filterPanel;
    }

    @Override
    public void setData(List<Product> products) {
        table.getTableTitle().setText("Products");
        table.setRowData(0, products);
        table.getView().refresh();

        /*table.openCategory();
        table.openAllCategories();
        table.*/
    }

    @Override
    public void setupOptions() {
        // Table Name
        tableName.addKeyUpHandler(event -> table.getTableTitle().setText(tableName.getValue()));

        // Selection Type
        selectionType.add(SelectionType.NONE);
        selectionType.add(SelectionType.SINGLE);
        selectionType.add(SelectionType.MULTIPLE);
        selectionType.addValueChangeHandler(event -> table.setSelectionType(event.getValue()));

        // Density
        density.add(DisplayDensity.DEFAULT);
        density.add(DisplayDensity.COMFORTABLE);
        density.add(DisplayDensity.COMPACT);
        density.addValueChangeHandler(event -> {
            table.setDensity(event.getValue());
            reload();
        });

        //States
        mode.add(CategoryMode.ENABLED);
        mode.add(CategoryMode.DISABLED);
        mode.add(CategoryMode.HIDDEN);
    }

    @UiHandler("stickyHeader")
    void stickyHeader(ValueChangeEvent<Boolean> event) {
        table.setUseStickyHeader(event.getValue());
    }

    @UiHandler("striped")
    void striped(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            table.getScaffolding().getTable().addStyleName("striped");
        } else {
            table.getScaffolding().getTable().removeStyleName("striped");
        }
    }

    @UiHandler("useCategories")
    void useCategories(ValueChangeEvent<Boolean> event) {
        table.setUseCategories(event.getValue());
        reload();
    }

    @UiHandler("rowExpansion")
    void rowExpansion(ValueChangeEvent<Boolean> event) {
        table.setUseRowExpansion(event.getValue());
        reload();
    }

    @UiHandler("openSingleCategory")
    void openSingleCategory(ClickEvent event) {
        table.openCategory(table.getView().getCategories().get(0));
    }

    @UiHandler("closeSingleCategory")
    void closeSingleCategory(ClickEvent event) {
        table.closeCategory(table.getView().getCategories().get(0));
    }

    @UiHandler("openAllCategories")
    void openAllCategories(ClickEvent event) {
        table.openAllCategories();
    }

    @UiHandler("closeAllCategories")
    void closeAllCategories(ClickEvent event) {
        table.closeAllCategories();
    }

    @UiHandler("mode")
    void states(ValueChangeEvent<CategoryMode> event) {
        table.getCategories().forEach(categoryComponent -> categoryComponent.setMode(event.getValue()));
        reload();
    }

    public void reload() {
        table.getView().setRedraw(true);
        table.getView().refresh();
    }
}
