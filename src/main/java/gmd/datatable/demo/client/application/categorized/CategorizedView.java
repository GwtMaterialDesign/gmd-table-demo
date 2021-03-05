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

import com.google.gwt.core.client.GWT;
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
import gwt.material.design.client.data.Categories;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.data.factory.Category;
import gwt.material.design.client.data.factory.Mode;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.table.cell.*;
import gwt.material.design.client.ui.table.MaterialDataTable;
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
    MaterialListValueBox<Mode> mode;

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
            .width("30%");

        table.addColumn(Product::getProductAdjective, "Adjective")
            .sortable(true)
            .width("10%");

        table.addColumn(Product::getColor, "Color")
            .sortable(true)
            .width("10%");

        table.addColumn(Product::getProductMaterial, "Material")
            .sortable(true)
            .width("10%");

        table.addColumn(Product::getCompany, "Company")
            .sortable(true)
            .addFooter(new FooterColumn<>(entireData -> NumberFormat.getDecimalFormat().format(0.0)))
            .width("10%");

        table.addColumn("Tax", new DoubleColumn<Product>() {
            @Override
            public Double getValue(Product object) {
                return object.getTax();
            }
        }
            .format(NumberFormat.getDecimalFormat())
            .sortable(true)
            .addFooter(new FooterColumn<>(entireData -> {
                double totalPrice = entireData.stream().mapToDouble(Product::getTax).sum();
                return NumberFormat.getDecimalFormat().format(totalPrice);
            }))
            .width("10%"));

        table.addColumn("Price", new DoubleColumn<Product>() {
            @Override
            public Double getValue(Product object) {
                return object.getPrice();
            }
        }
            .format(NumberFormat.getCurrencyFormat())
            .sortable(true)
            .addFooter(new FooterColumn<>(entireData -> {
                double totalPrice = entireData.stream().mapToDouble(Product::getPrice).sum();
                return NumberFormat.getDecimalFormat().format(totalPrice);
            }))
            .width("10%"));

        table.addColumn("Computed", new ComputedColumn<Product, Double>() {
            @Override
            public Double compute(RowComponent<Product> row) {
                Product data = row.getData();
                List<Product> allData = row.getDataView().getData();
                List<Product> categoryData = row.getCategory().getData();

                double currentPrice = data.getPrice();
                double allPrices = allData.stream().mapToDouble(Product::getPrice).sum();
                double categoryPrices = categoryData.stream().mapToDouble(Product::getPrice).sum();

                GWT.log("--------------------------------");
                GWT.log(data.getProductName());
                GWT.log("--------------------------------");
                GWT.log("Current Price   : " + NumberFormat.getCurrencyFormat().format(currentPrice));
                GWT.log("All Prices(" + allData.size() + " rows): " + NumberFormat.getCurrencyFormat().format(allPrices));
                GWT.log("Category Prices (" + categoryData.size() + " rows): " + NumberFormat.getCurrencyFormat().format(categoryPrices));
                GWT.log("Computed Price (current - (all / category)) : " + NumberFormat.getCurrencyFormat().format(currentPrice - (allPrices / categoryPrices)));

                return currentPrice - (allPrices / categoryPrices);
            }
        })
            .format(NumberFormat.getCurrencyFormat())
            .addFooter(new FooterColumn<>(entireData -> NumberFormat.getDecimalFormat().format(0.0)))
            .width("10%")
            .blankPlaceholder("-");

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

        //TODO: Standardized and make it easier to define category columns
        table.addComponentsRenderedHandler(event -> {
            Categories<Product> categories = table.getCategories();
            categories.forEach(category -> {
                List<Column<Product, ?>> columns = table.getColumns();
                for (Column<Product, ?> column : columns) {
                    if (column.name().equalsIgnoreCase("Price")) {
                        category.addColumn(new CategoryColumn<>(column, (entireData, categoryComponent) -> {
                            double categoryPrices = categoryComponent.getData().stream().mapToDouble(Product::getPrice).sum();
                            return NumberFormat.getCurrencyFormat().format(categoryPrices);
                        }));
                    } else if (column.name().equalsIgnoreCase("Tax")) {
                        category.addColumn(new CategoryColumn<>(column, (entireData, categoryComponent) -> {
                            double categoryTaxes = categoryComponent.getData().stream().mapToDouble(Product::getTax).sum();
                            return NumberFormat.getDecimalFormat().format(categoryTaxes);
                        }));
                    } else if (column.name().equalsIgnoreCase("Computed")) {
                        category.addColumn(new CategoryColumn<>(column, (entireData, categoryComponent) -> {
                            //TODO: Get computed values.
                            double categoryTaxes = categoryComponent.getData().stream().mapToDouble(Product::getTax).sum();
                            return NumberFormat.getDecimalFormat().format(categoryTaxes);
                        }));
                    }
                }
            });
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
        mode.add(Mode.ENABLED);
        mode.add(Mode.DISABLED);
        mode.add(Mode.HIDDEN);
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
    void states(ValueChangeEvent<Mode> event) {
        table.getCategories().forEach(categoryComponent -> categoryComponent.setMode(event.getValue()));

        reload();
    }

    public void reload() {
        table.getView().setRedraw(true);
        table.getView().refresh();
    }
}
