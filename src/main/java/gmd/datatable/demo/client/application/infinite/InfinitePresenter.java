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

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import gmd.datatable.demo.client.HasRightSideNav;
import gmd.datatable.demo.client.application.ApplicationPresenter;
import gmd.datatable.demo.client.application.BasePresenter;
import gmd.datatable.demo.client.place.NameTokens;

public class InfinitePresenter extends BasePresenter<InfinitePresenter.MyView, InfinitePresenter.MyProxy> {
    interface MyView extends View, HasRightSideNav {
        void loadData();

        void setupOptions();
    }

    @ProxyStandard
    @NameToken(NameTokens.INFINITE)
    interface MyProxy extends ProxyPlace<InfinitePresenter> {
    }

    @Inject
    InfinitePresenter(
        EventBus eventBus,
        MyView view,
        MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);
    }

    @Override
    protected void onBind() {
        super.onBind();

        getView().setupOptions();
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        setHeaderTitle("Infinite Data Table", "This example shows the DataTables table body scrolling in the vertical direction with infinite scrolling. The idea of infinite scrolling means that data will be added to the table dynamically, as and when needed by the user scrolling the table.", "infinite");
        getView().loadData();
    }
}
