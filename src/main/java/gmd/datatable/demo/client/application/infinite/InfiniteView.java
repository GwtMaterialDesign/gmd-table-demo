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
package gmd.datatable.demo.client.application.infinite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.application.categorized.components.CustomCategoryFactory;
import gmd.datatable.demo.client.application.categorized.components.CustomRenderer;
import gmd.datatable.demo.client.application.infinite.components.FakeUserService;
import gmd.datatable.demo.client.application.infinite.components.UserDataSource;
import gmd.datatable.demo.client.application.infinite.components.UserRowFactory;
import gmd.datatable.demo.client.application.infinite.components.UserServiceAsync;
import gmd.datatable.demo.client.generator.user.User;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.density.DisplayDensity;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.infinite.InfiniteDataView;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialListValueBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.table.MaterialInfiniteDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import javax.inject.Inject;
import java.util.List;

public class InfiniteView extends ViewImpl implements InfinitePresenter.MyView {

    interface Binder extends UiBinder<Widget, InfiniteView> {
    }

    @UiField(provided = true)
    MaterialInfiniteDataTable<User> table;

    @UiField
    MaterialPanel filterPanel;

    @UiField
    MaterialListValueBox<SelectionType> selectionType;

    @UiField
    MaterialListValueBox<DisplayDensity> density;

    @UiField
    MaterialTextBox tableName;

    private UserServiceAsync userService = GWT.create(FakeUserService.class);

    @Inject
    InfiniteView(Binder uiBinder) {
        table = new MaterialInfiniteDataTable(20,
            InfiniteDataView.DYNAMIC_VIEW, new UserDataSource(userService));

        initWidget(uiBinder.createAndBindUi(this));

        // We will manually add this category otherwise categories
        // can be loaded on the fly with HasDataCategory, or a custom
        // RowComponentFactory as demonstrated below
        //table.addCategory(new CustomCategoryComponent("Custom Category"));

        // We will define our own person row factory to generate the
        // category name. This can be used to generate your own
        // RowComponent's too, if custom functionality is required.
        /*table.setRowFactory(new UserRowFactory());*/

        // If we want to generate all our categories using CustomCategoryComponent
        // We can define our own CategoryComponentFactory. There we can define our
        // own CategoryComponent implementations.
        table.setCategoryFactory(new CustomCategoryFactory());

        // It is possible to create your own custom renderer per table
        // When you use the BaseRenderer you can override certain draw
        // methods to create elements the way you would like.
        table.setRenderer(new CustomRenderer<>());

        table.addColumn("Image", new WidgetColumn<User, MaterialPanel>() {
            @Override
            public MaterialPanel getValue(User object) {
                MaterialPanel panel = new MaterialPanel();
                MaterialImage image = new MaterialImage();
                image.setUrl(object.getImage());
                image.setWidth("32px");
                image.setHeight("32px");
                image.setCircle(true);
                panel.add(image);
                return panel;
            }
        });


        table.addColumn("First Name", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getName();
            }

            @Override
            public boolean sortable() {
                return true;
            }
        });

        table.addColumn("Email", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getEmail();
            }
        });

        table.addColumn("Phone", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getPhone();
            }
        });

        table.addColumn("Company", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getCompany();
            }
        });

        table.addColumn("City", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getCity();
            }
        });

        table.addColumn("Zip Code", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getZipCode();
            }
        });
    }

    @Override
    public void loadData() {
        table.getTableTitle().setText("Users");
        // Load the categories from the server
        table.getView().setLoadMask(true);

        userService.getCategories(new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> categories) {
                for (String category : categories) {
                    table.addCategory(new CategoryComponent(table, category));
                }
                table.getView().setLoadMask(false);
                reload();
            }

            @Override
            public void onFailure(Throwable throwable) {
                GWT.log("Getting people categories async call failed.", throwable);
            }
        });
    }

    @Override
    public MaterialWidget getSideContent() {
        return filterPanel;
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

    public void reload() {
        table.getView().setRedraw(true);
        table.getView().refresh();
    }
}
