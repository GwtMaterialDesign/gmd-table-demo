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
package gmd.datatable.demo.client.application.standard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.generator.DataGenerator;
import gmd.datatable.demo.client.generator.user.User;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.density.DisplayDensity;
import gwt.material.design.client.base.helper.ScrollHelper;
import gwt.material.design.client.constants.OffsetPosition;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.table.cell.FooterColumn;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static gwt.material.design.jquery.client.api.JQuery.$;

public class StandardView extends ViewImpl implements StandardPresenter.MyView {

    interface Binder extends UiBinder<Widget, StandardView> {
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

    @UiField
    MaterialPanel events;

    @Inject
    StandardView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        table.setUseStickyFooter(true);
    }

    @Override
    public void setupTable() {
        // Setting default formats on each columns
        MaterialDataTable.getGlobals().getFormatProvider()
            .setDateFormat(DateTimeFormat.getFormat("MM/dd/yyyy"))
            .setIntegerFormat(NumberFormat.getCurrencyFormat("CAD"))
            .setLongFormat(NumberFormat.getFormat("##"))
            .setDoubleFormat(NumberFormat.getPercentFormat())
            .setFloatFormat(NumberFormat.getDecimalFormat())
            .setBigDecimalFormat(NumberFormat.getDecimalFormat())
            .setShortFormat(NumberFormat.getDecimalFormat());

        // Will set the global default blank placeholder
        MaterialDataTable.getGlobals().setDefaultBlankPlaceholder("-");

        // Default Blank Placeholder for the table's instance only
        table.setDefaultBlankPlaceholder("N/A");
        table.getFormatProvider().setDateFormat(DateTimeFormat.getFormat("MM"));

        table.setRowClickCooldown(0);
        table.addColumn(new WidgetColumn<User, MaterialPanel>() {
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
        })
        .sortable(true)
        .name("Image")
        .width("60px");

        table.addColumn(User::getName, "First Name")
            .setHidden(false)
            .setHideable(false)
            .sortable(true);

        // Demoing date formats and placeholder
        table.addColumn(new DateColumn<User>() {
            @Override
            public Date getValue(User object) {
                return new Date();
            }
        })
            .format(DateTimeFormat.getFormat("MM/dd/yyyy"))
            .blankPlaceholder("-")
            .name("Date")
            .sortable(true);

        table.addColumn(new DoubleColumn<User>() {
            @Override
            public Double getValue(User object) {
                return 0.1126132;
            }
        })
            .format(NumberFormat.getPercentFormat())
            .defaultValue(0.0)
            .name("Percent")
            .sortable(true);

        table.addColumn(new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getEmail();
            }
        })
            .blankPlaceholder("N/A")
            .name("Email")
            .sortable(true);

        table.addColumn(new ComputedColumn<User, Double>() {

            @Override
            public Double compute(RowComponent<User> row) {
                User currentData = row.getData();
                List<User> entireData = row.getDataView().getData();
                double totalSalary = entireData.stream().mapToDouble(User::getSalary).sum();
                double computedValue = totalSalary / currentData.getSalary();
                GWT.log("Total Salary : " + totalSalary);
                return computedValue;
            }
        })
            .format(NumberFormat.getDecimalFormat())
            .defaultValue(0.0)
            .addFooter(new FooterColumn<>(entireData -> "Total"))
            .name("Computed")
            .sortable(true);

        table.addColumn(new DoubleColumn<User>() {
            @Override
            public Double getValue(User object) {
                return object.getSalary();
            }
        })
            .format(NumberFormat.getCurrencyFormat())
            .defaultValue(0.0)
            .name("Salary")
            .addFooter(new FooterColumn<>(entireData -> {
                double totalSalary = entireData.stream().mapToDouble(User::getSalary).sum();
                return NumberFormat.getCurrencyFormat().format(totalSalary);
            }))
            .sortable(true);

        table.addColumn(User::getPhone, "Phone")
            .sortable(true);

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
    }

    protected void log(String eventName, String description) {
        MaterialLabel label = new MaterialLabel("[" + DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT).format(new Date()) + "] " +
            " " + eventName + " - " + description);
        label.setFontSize("0.8em");
        events.add(label);

        ScrollHelper scrollHelper = new ScrollHelper();
        scrollHelper.setOffsetPosition(OffsetPosition.BOTTOM);
        scrollHelper.setContainer(events);
        scrollHelper.scrollTo(label);
    }

    @Override
    public MaterialWidget getSideContent() {
        return filterPanel;
    }

    @Override
    public void setData(List<User> users) {
        table.getView().setLoadMask(true);
        Scheduler.get().scheduleFixedDelay(() -> {
            events.clear();

            table.getTableTitle().setText("Customers");
            table.setRowData(0, users);

            // Setting the first row to be disabled. Available Modes are HIDDEN, DISABLED and ENABLED (By Default)
            //table.getRow(0).setMode(Mode.DISABLED);

            reload();
            table.getView().setLoadMask(false);
            return false;
        }, 1000);

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

    @UiHandler("clear")
    void clearTable(ClickEvent event) {
        setData(new DataGenerator().generateUsers(0));
    }

    @UiHandler("addRow")
    void addRow(ClickEvent event) {
        setData(new DataGenerator().generateUsers(1));
    }

    @UiHandler("addAllRows")
    void addAllRows(ClickEvent event) {
        setData(new DataGenerator().generateUsers(50));
    }

    @UiHandler("stickyHeader")
    void stickyHeader(ValueChangeEvent<Boolean> event) {
        table.setUseStickyHeader(event.getValue());
    }

    @UiHandler("stickyFooter")
    void stickyFooter(ValueChangeEvent<Boolean> event) {
        table.setUseStickyFooter(event.getValue());
    }

    @UiHandler("striped")
    void striped(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            table.getScaffolding().getTable().addStyleName("striped");
        } else {
            table.getScaffolding().getTable().removeStyleName("striped");
        }
    }

    @UiHandler("responsive")
    void responsive(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            table.getScaffolding().getTable().addStyleName("responsive");
        } else {
            table.getScaffolding().getTable().removeStyleName("responsive");
        }
    }

    @UiHandler("highlightRow")
    void highlightRow(ClickEvent event) {
        RowComponent<User> row = table.getRow(20);
        row.highlight();
    }

    public void reload() {
        table.getView().setRedraw(true);
        table.getView().refresh();
    }
}
