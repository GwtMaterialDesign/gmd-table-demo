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

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import gmd.datatable.demo.client.HasRightSideNav;
import gmd.datatable.demo.client.application.ApplicationPresenter;
import gmd.datatable.demo.client.application.BasePresenter;
import gmd.datatable.demo.client.generator.DataGenerator;
import gmd.datatable.demo.client.generator.user.User;
import gmd.datatable.demo.client.place.NameTokens;

import java.util.List;

public class StandardPresenter extends BasePresenter<StandardPresenter.MyView, StandardPresenter.MyProxy> {
    interface MyView extends View, HasRightSideNav {
        void setupTable();
        void setData(List<User> users);
        void setupOptions();
    }

    @ProxyStandard
    @NameToken(NameTokens.STANDARD)
    interface MyProxy extends ProxyPlace<StandardPresenter> {
    }

    @Inject
    StandardPresenter(
        EventBus eventBus,
        MyView view,
        MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);
    }

    @Override
    protected void onBind() {
        super.onBind();

        getView().setupOptions();
        getView().setupTable();
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        getView().setData(new DataGenerator().generateUsers(50));
    }
}
