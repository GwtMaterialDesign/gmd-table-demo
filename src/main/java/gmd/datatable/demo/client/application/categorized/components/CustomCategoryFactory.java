package gmd.datatable.demo.client.application.categorized.components;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.HasCategories;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.component.CategoryComponent.OrphanCategoryComponent;
import gwt.material.design.client.data.factory.CategoryComponentFactory;
import gwt.material.design.client.ui.table.TableSubHeader;

public class CustomCategoryFactory extends CategoryComponentFactory {

    @Override
    public CategoryComponent generate(DataView dataView, String categoryName) {
        CategoryComponent category = super.generate(dataView, categoryName);

        if(!(category instanceof OrphanCategoryComponent)) {
            category = new CustomCategoryComponent(dataView, categoryName, false);
        }
        return category;
    }

    public static class CustomCategoryComponent extends CategoryComponent {
        public CustomCategoryComponent(HasCategories parent, String name) {
            super(parent, name);
        }

        public CustomCategoryComponent(HasCategories parent, String name, boolean openByDefault) {
            super(parent, name, openByDefault);
        }

        @Override
        protected void render(TableSubHeader subheader, int columnCount) {
            super.render(subheader, columnCount);

            subheader.setOpenIcon(IconType.FOLDER_OPEN);
            subheader.setCloseIcon(IconType.FOLDER);
        }
    }
}
