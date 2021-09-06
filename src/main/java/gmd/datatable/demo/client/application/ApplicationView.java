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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.ViewImpl;
import gmd.datatable.demo.client.HasRightSideNav;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialNavSection;

import java.util.Date;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {

    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    MaterialNavSection navSection;

    @UiField
    MaterialColumn mainContainer, optionsPanel, optionsContent;

    @UiField
    MaterialLabel copyright;

    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        bindSlot(ApplicationPresenter.SLOT_MAIN, mainContainer);
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        copyright.setText("Copyright @ " + DateTimeFormat.getFormat("yyyy").format(new Date()) + " GMD Project");
    }

    @Override
    public void updateSideNavContent(View view) {
        optionsContent.clear();

        if (view instanceof HasRightSideNav) {
            optionsContent.add(((HasRightSideNav) view).getSideContent());
            optionsPanel.setVisible(true);
            mainContainer.setGrid("s12 m12 l9");
        } else {
            optionsPanel.setVisible(false);
            mainContainer.setGrid("s12 m12 l12");
        }
    }
}
