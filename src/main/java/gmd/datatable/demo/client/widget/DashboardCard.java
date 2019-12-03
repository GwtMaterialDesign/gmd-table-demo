package gmd.datatable.demo.client.widget;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
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


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import gwt.material.design.client.base.helper.ColorHelper;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;

public class DashboardCard extends Composite {

    private static DashboardCardUiBinder uiBinder = GWT.create(DashboardCardUiBinder.class);
    private final Dashboard dashboard;

    @UiField
    MaterialCard card;

    @UiField
    MaterialLabel symbol, name, description;


    interface DashboardCardUiBinder extends UiBinder<MaterialColumn, DashboardCard> {
    }

    public DashboardCard(Dashboard dashboard) {
        initWidget(uiBinder.createAndBindUi(this));

        this.dashboard = dashboard;
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        symbol.setText(dashboard.getName().substring(0, 2));
        name.setText(dashboard.getName());
        description.setText(dashboard.getDescription());

        symbol.setBackgroundColor(ColorHelper.addShade(dashboard.getColor(), ColorHelper.Shade.LIGHTEN, 5));
        symbol.setTextColor(dashboard.getColor());

        card.addClickHandler(event -> {
            PlaceRequest placeRequest = new PlaceRequest.Builder()
                .nameToken(dashboard.getLink())
                .build();

            dashboard.getPlaceManager().revealPlace(placeRequest);
        });
    }

    @Override
    protected MaterialColumn getWidget() {
        return (MaterialColumn) super.getWidget();
    }
}
