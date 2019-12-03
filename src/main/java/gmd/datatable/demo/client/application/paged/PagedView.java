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
package gmd.datatable.demo.client.application.paged;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.generator.user.User;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.density.DisplayDensity;
import gwt.material.design.client.data.ListDataSource;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialListValueBox;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.pager.DataPagerLocaleProvider;
import gwt.material.design.client.ui.pager.MaterialDataPager;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import javax.inject.Inject;
import java.util.List;

public class PagedView extends ViewImpl implements PagedPresenter.MyView {

    interface Binder extends UiBinder<Widget, PagedView> {
    }

    @UiField
    MaterialDataTable<User> table;

    @UiField
    MaterialPanel filterPanel;

    @UiField
    MaterialListValueBox<SelectionType> selectionType;

    @UiField
    MaterialListValueBox<DisplayDensity> density;

    @UiField
    MaterialTextBox tableName;

    private MaterialDataPager<User> pager = new MaterialDataPager<>();
    private ListDataSource<User> dataSource;

    @Inject
    PagedView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setupTable() {
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
    public MaterialWidget getSideContent() {
        return filterPanel;
    }

    @Override
    public void setData(List<User> users) {
        // Setup DataTable Pager
        dataSource = new ListDataSource<>();
        dataSource.add(0, users);

        pager.setLocaleProvider(new DataPagerLocaleProvider() {
            @Override
            public String RowsPerPage() {
                return "Rows Per Page";
            }

            @Override
            public String Page() {
                return "Page";
            }

            @Override
            public String of() {
                return "of";
            }
        });
        pager.setTable(table);
        pager.setDataSource(dataSource);
        table.add(pager);

        table.getTableTitle().setText("Customers");
        table.setRowData(0, users);
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

    @UiHandler("first")
    void first(ClickEvent e) {
        pager.firstPage();
    }

    @UiHandler("last")
    void last(ClickEvent e) {
        pager.lastPage();
    }

    public void reload() {
        table.getView().setRedraw(true);
        table.getView().refresh();
    }
}
