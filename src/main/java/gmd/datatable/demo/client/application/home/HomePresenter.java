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
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import gmd.datatable.demo.client.application.ApplicationPresenter;
import gmd.datatable.demo.client.place.NameTokens;
import gmd.datatable.demo.client.widget.Dashboard;
import gwt.material.design.client.constants.Color;

import java.util.Arrays;
import java.util.List;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {
    interface MyView extends View {
        void setDashboard(List<Dashboard> dashboards);
    }

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    @Inject
    HomePresenter(
        EventBus eventBus,
        MyView view,
        MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);
    }

    @Override
    protected void onBind() {
        super.onBind();


        getView().setDashboard(Arrays.asList(
            new Dashboard("Autocomplete", "Native", NameTokens.DROPZONE, Color.RED),
            new Dashboard("Avatar", "Native", NameTokens.DROPZONE, Color.AMBER),
            new Dashboard("Bubble", "Native", NameTokens.DROPZONE, Color.BLUE),
            new Dashboard("Camera", "Native", NameTokens.DROPZONE, Color.GREEN),
            new Dashboard("Carousel", "Native", NameTokens.DROPZONE, Color.PURPLE),
            new Dashboard("Circular Progress", "Native", NameTokens.DROPZONE, Color.ORANGE),
            new Dashboard("ComboBox", "Native", NameTokens.DROPZONE, Color.BROWN),
            new Dashboard("CountUp", "Native", NameTokens.DROPZONE, Color.CYAN),
            new Dashboard("CutOut", "Native", NameTokens.DROPZONE, Color.DEEP_ORANGE),
            new Dashboard("DocViewer", "Native", NameTokens.DROPZONE, Color.INDIGO),
            new Dashboard("Drag and Drop", "Native", NameTokens.DROPZONE, Color.GREY),
            new Dashboard("Empty States", "Native", NameTokens.DROPZONE, Color.LIGHT_BLUE),
            new Dashboard("FileUploader", "Native", NameTokens.DROPZONE, Color.CYAN),
            new Dashboard("Icon Morph", "Native", NameTokens.DROPZONE, Color.LIME),
            new Dashboard("Image Cropper", "Native", NameTokens.DROPZONE, Color.BLUE),
            new Dashboard("Input Mask", "Native", NameTokens.DROPZONE, Color.GREY),
            new Dashboard("Live Stamp", "Native", NameTokens.DROPZONE, Color.BROWN),
            new Dashboard("Masonry", "Native", NameTokens.DROPZONE, Color.ORANGE),
            new Dashboard("MenuBar", "Native", NameTokens.DROPZONE, Color.DEEP_ORANGE),
            new Dashboard("Overlay", "Native", NameTokens.DROPZONE, Color.LIME),
            new Dashboard("Path Animator", "Native", NameTokens.DROPZONE, Color.LIGHT_BLUE),
            new Dashboard("Rating", "Native", NameTokens.DROPZONE, Color.INDIGO),
            new Dashboard("Rich Editor", "Native", NameTokens.DROPZONE, Color.RED),
            new Dashboard("ScrollFire", "Native", NameTokens.DROPZONE, Color.AMBER),
            new Dashboard("Signature Pad", "Native", NameTokens.DROPZONE, Color.LIGHT_BLUE),
            new Dashboard("SubHeader", "Native", NameTokens.DROPZONE, Color.PURPLE),
            new Dashboard("Stepper", "Native", NameTokens.DROPZONE, Color.BROWN),
            new Dashboard("Swipeable", "Native", NameTokens.DROPZONE, Color.ORANGE),
            new Dashboard("Time Picker", "Native", NameTokens.DROPZONE, Color.DEEP_ORANGE),
            new Dashboard("Tree View", "Native", NameTokens.DROPZONE, Color.INDIGO),
            new Dashboard("Waterfall", "Native", NameTokens.DROPZONE, Color.GREY),
            new Dashboard("WebP", "Native", NameTokens.DROPZONE, Color.LIGHT_BLUE),
            new Dashboard("Window", "Native", NameTokens.DROPZONE, Color.RED)
        ));
    }

    @Override
    protected void onReveal() {
        super.onReveal();
    }
}
