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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.HasRightSideNav;
import gmd.datatable.demo.client.resources.AppResources;
import gwt.material.design.client.MaterialDesignBase;
import gwt.material.design.client.base.TableDarkThemeLoader;
import gwt.material.design.client.base.helper.ColorHelper;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.pwa.PwaManager;
import gwt.material.design.client.pwa.push.js.Notification;
import gwt.material.design.client.pwa.serviceworker.ServiceWorkerManager;
import gwt.material.design.client.pwa.serviceworker.js.ServiceWorkerOption;
import gwt.material.design.client.theme.dark.CoreDarkThemeLoader;
import gwt.material.design.client.theme.dark.DarkThemeLoader;
import gwt.material.design.client.theme.dark.DarkThemeManager;
import gwt.material.design.client.ui.*;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {

    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    MaterialNavSection navSection;

    @UiField
    MaterialPanel mainContainer;

    @UiField
    MaterialIcon filter;

    @UiField
    MaterialSideNavContent sideContent;

    @UiField
    MaterialSideNavPush sidenav;

    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        bindSlot(ApplicationPresenter.SLOT_MAIN, mainContainer);
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        // Dark Theme Mode
        DarkThemeManager.get()
            .register(new CoreDarkThemeLoader())
            .register(new TableDarkThemeLoader())
            .load();

        // Enable PWA
        if (PwaManager.isPwaSupported()) {
            ServiceWorkerManager manager = new ServiceWorkerManager("/gmd-table-demo/service-worker.js");
            ServiceWorkerOption option = ServiceWorkerOption.create();
            option.setScope("/gmd-table-demo/");
            manager.setOption(option);
            PwaManager.getInstance()
                    .setServiceWorker(manager)
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
    }

    @Override
    public void updateSideNavContent(View view) {
        sideContent.clear();
        if (view instanceof HasRightSideNav) {
            sideContent.add(((HasRightSideNav) view).getSideContent());
            sidenav.setVisible(true);
            filter.setVisible(true);
            Scheduler.get().scheduleDeferred(() -> sidenav.open());
        } else {
            Scheduler.get().scheduleDeferred(() -> sidenav.close());
            filter.setVisible(false);
            sidenav.setVisible(false);
        }
    }
}