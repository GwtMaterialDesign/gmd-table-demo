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

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.generator.User;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;

import javax.inject.Inject;
import java.util.List;

public class StandardView extends ViewImpl implements StandardPresenter.MyView {

    interface Binder extends UiBinder<Widget, StandardView> {
    }

    @UiField
    MaterialDataTable<User> table;

    @Inject
    StandardView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        setupTable();
    }

    @Override
    public void setupTable() {

        table.addColumn("First Name", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getFirstName();
            }
        });

        table.addColumn("First Name", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getFirstName();
            }
        });

        table.addColumn("Last Name", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getLastName();
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

        table.addColumn("Address", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getAddress();
            }
        });

        table.addColumn("City", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getCity();
            }
        });

        table.addColumn("Country", new TextColumn<User>() {
            @Override
            public String getValue(User object) {
                return object.getCountry();
            }
        });
    }

    @Override
    public void setData(List<User> users) {
        table.setRowData(0, users);
        table.getView().refresh();
    }
}
