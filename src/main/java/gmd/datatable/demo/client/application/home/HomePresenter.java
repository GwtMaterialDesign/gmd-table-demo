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
package gmd.datatable.demo.client.application.home;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import gmd.datatable.demo.client.application.ApplicationPresenter;
import gmd.datatable.demo.client.application.BasePresenter;
import gmd.datatable.demo.client.place.NameTokens;
import gmd.datatable.demo.client.widget.Dashboard;
import gwt.material.design.client.constants.Color;

import java.util.Arrays;
import java.util.List;

public class HomePresenter extends BasePresenter<HomePresenter.MyView, HomePresenter.MyProxy> {
    interface MyView extends View {
        void setDashboard(List<Dashboard> dashboards);
    }

    private PlaceManager placeManager;

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    @Inject
    HomePresenter(
        EventBus eventBus,
        MyView view,
        MyProxy proxy,
        PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        this.placeManager = placeManager;
    }

    @Override
    protected void onBind() {
        super.onBind();


        getView().setDashboard(Arrays.asList(
            new Dashboard(placeManager, "Standard", "Simple DataTable implementation", NameTokens.STANDARD, Color.RED),
            new Dashboard(placeManager, "Categorized", "Categorized your rows.", NameTokens.CATEGORIZED, Color.AMBER),
            new Dashboard(placeManager, "Paged", "Provided a data pager for datatable.", NameTokens.PAGED, Color.PURPLE),
            new Dashboard(placeManager, "Frozen", "Provided a frozen column.", NameTokens.FROZEN, Color.GREEN),
            new Dashboard(placeManager, "Infinite", "Provided Table Rows Infinite Scrolling. ", NameTokens.INFINITE, Color.ORANGE),
            new Dashboard(placeManager, "Customized", "Build your desired Table Look and Feel.", NameTokens.CUSTOMIZED, Color.BLUE)
        ));
    }

    @Override
    protected void onReveal() {
        super.onReveal();
    }
}
