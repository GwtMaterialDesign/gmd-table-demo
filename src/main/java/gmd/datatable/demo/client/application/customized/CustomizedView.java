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
package gmd.datatable.demo.client.application.customized;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.generator.DataGenerator;
import gmd.datatable.demo.client.generator.user.User;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.density.DisplayDensity;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import javax.inject.Inject;
import java.util.List;

public class CustomizedView extends ViewImpl implements CustomizedPresenter.MyView {

    interface Binder extends UiBinder<Widget, CustomizedView> {
    }

    private List<User> users;

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

    @Inject
    CustomizedView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setupTable() {
        MaterialIcon icon = new MaterialIcon();
        icon.setPadding(4);
        icon.setIconType(IconType.ADD_CIRCLE_OUTLINE);
        icon.registerHandler(icon.addClickHandler(event -> {
            users.add(0, new DataGenerator().generateUsers(1).get(0));
            setData(users);
        }));
        table.getScaffolding().getToolPanel().add(icon);

        MaterialIcon delete = new MaterialIcon();
        delete.setPadding(4);
        delete.setIconType(IconType.DELETE);
        table.getScaffolding().getToolPanel().add(delete);
        delete.addClickHandler(event -> {
            User user = table.getView().getSelectedRowModels(true).get(0);
            users.remove(user);
            setData(users);
        });

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

        table.addColumn("Option", new WidgetColumn<User, MaterialListBox>() {
            @Override
            public MaterialListBox getValue(User object) {
                MaterialListBox listBox = new MaterialListBox();
                listBox.addItem("Option 1");
                listBox.addItem("Option 2");
                listBox.addItem("Option 3");
                return listBox;
            }
        }).width(200);

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

        // Add a row select handler, called when a user selects a row.
        table.addRowSelectHandler(event -> {
            log("RowSelectEvent", event.getModel().getName() + ": " + event.isSelected());
        });

        // Add a sort column handler, called when a user sorts a column.
        table.addColumnSortHandler(event -> {
            log("ColumnSortEvent", "Sorted: " + event.getSortContext().getSortDir() + ", columnIndex: " + event.getColumnIndex());
            table.getView().refresh();
        });

        // Add category opened handler, called when a category is opened.
        table.addCategoryOpenedHandler(event -> {
            log("CategoryOpenedEvent", "Category Opened: " + event.getName());
        });

        // Add category closed handler, called when a category is closed.
        table.addCategoryClosedHandler(event -> {
            log("CategoryClosedEvent", "Category Closed: " + event.getName());
        });

        // Add a row double click handler, called when a row is double clicked.
        table.addRowDoubleClickHandler(event -> {
            log("RowDoubleClickEvent", "Row Double Clicked: " + event.getModel().getName() + ", x:" + event.getMouseEvent().getPageX() + ", y: " + event.getMouseEvent().getPageY());
        });

        // Configure the tables long press duration configuration.
        // The short press is when a click is held less than this duration.
        table.setLongPressDuration(400);

        // Add a row long press handler, called when a row is long pressed.
        table.addRowLongPressHandler(event -> {
            log("RowLongPressEvent", "Row Long Pressed: " + event.getModel().getName() + ", x:" + event.getMouseEvent().getPageX() + ", y: " + event.getMouseEvent().getPageY());
        });

        // Add a row short press handler, called when a row is short pressed.
        table.addRowShortPressHandler(event -> {
            log("RowShortPressEvent", "Row Short Pressed: " + event.getModel().getName() + ", x:" + event.getMouseEvent().getPageX() + ", y: " + event.getMouseEvent().getPageY());
        });

        // Add rendered handler, called when 'setRowData' calls finish rendering.
        // Guaranteed to only be called once from the data set render, ignoring sort renders and refreshView renders.
        table.addRenderedHandler(e -> {
            log("RenderedEvent", "Table Rendered");
        });

        // Add components rendered handler, Called each time when components are rendered,
        // which includes sorting renders and refreshView() renders.
        table.addComponentsRenderedHandler(e -> {
            log("ComponentsRenderedEvent", "Data Table Components Rendered");
        });
    }

    protected void log(String eventName, String description) {
        GWT.log(description);
    }

    @Override
    public MaterialWidget getSideContent() {
        return filterPanel;
    }

    @Override
    public void setData(List<User> users) {
        this.users = users;
        // Customized Table Scaffolding elements
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
        selectionType.setValue(SelectionType.SINGLE, true);
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

    public void reload() {
        table.getView().setRedraw(true);
        table.getView().refresh();
    }
}
