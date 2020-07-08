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
package gmd.datatable.demo.client.application;

import com.google.gwt.dom.client.Document;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.presenter.slots.NestedSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import gmd.datatable.demo.client.events.PageRevealEvent;
import gmd.datatable.demo.client.resources.AppResources;
import gwt.material.design.client.JQueryMigrate;
import gwt.material.design.client.MaterialDesignBase;
import gwt.material.design.client.base.TableDarkThemeLoader;
import gwt.material.design.client.base.helper.ColorHelper;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.pwa.PwaManager;
import gwt.material.design.client.pwa.push.js.Notification;
import gwt.material.design.client.theme.dark.CoreDarkThemeLoader;
import gwt.material.design.client.theme.dark.DarkThemeManager;
import gwt.material.design.client.ui.MaterialToast;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
    implements PageRevealEvent.PageRevealChangeHandler {

    static {
        MaterialDesignBase.injectCss(AppResources.INSTANCE.highlightCSs());
        MaterialDesignBase.injectJs(AppResources.INSTANCE.highlightJs());
    }

    interface MyView extends View {
        void updateSideNavContent(View view);
    }

    private PlaceManager placeManager;

    public static final NestedSlot SLOT_MAIN = new NestedSlot();

    @ProxyStandard
    interface MyProxy extends Proxy<ApplicationPresenter> {
    }

    @Inject
    ApplicationPresenter(EventBus eventBus,
                         MyView view,
                         MyProxy proxy,
                         PlaceManager placeManager) {
        super(eventBus, view, proxy, RevealType.Root);

        this.placeManager = placeManager;
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(PageRevealEvent.TYPE, this);

        // Dark Theme Mode
        DarkThemeManager.get()
            .register(new CoreDarkThemeLoader())
            .register(new TableDarkThemeLoader())
            .load();

        // Enable PWA
        if (PwaManager.isPwaSupported()) {
            PwaManager.getInstance()
                .setServiceWorker(new AppServiceWorkerManager())
                .setThemeColor(ColorHelper.setupComputedBackgroundColor(Color.BLUE_DARKEN_3))
                .setWebManifest("manifest.url")
                .load();

            // Will request a notification
            Notification.requestPermission(status -> MaterialToast.fireToast("Permission Status: " + status));
        }

        // Inject Resources
        MaterialDesignBase.injectCss(AppResources.INSTANCE.appCss());

        // Remove Splashscreen once js files are loaded
        Document.get().getElementById("splashscreen").removeFromParent();

        // TODO: Turn off later before merging to master.
        // Load JQuery Migrate Plugin
        JQueryMigrate.load(true);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
    }

    @Override
    public void onPageRevealChange(PageRevealEvent event) {
        getView().updateSideNavContent(event.getView());
    }
}
