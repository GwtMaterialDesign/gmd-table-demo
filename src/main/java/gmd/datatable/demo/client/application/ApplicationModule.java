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

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import gmd.datatable.demo.client.application.categorized.CategorizedModule;
import gmd.datatable.demo.client.application.customized.CustomizedModule;
import gmd.datatable.demo.client.application.frozen.FrozenModule;
import gmd.datatable.demo.client.application.home.HomeModule;
import gmd.datatable.demo.client.application.infinite.InfiniteModule;
import gmd.datatable.demo.client.application.paged.PagedModule;
import gmd.datatable.demo.client.application.standard.StandardModule;

public class ApplicationModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        install(new HomeModule());
        install(new StandardModule());
        install(new CategorizedModule());
        install(new PagedModule());
        install(new FrozenModule());
        install(new InfiniteModule());
        install(new CustomizedModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
            ApplicationPresenter.MyProxy.class);
    }
}
