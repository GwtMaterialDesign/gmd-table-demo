package gmd.datatable.demo.client.application.categorized.components;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.data.DataView;
import gwt.material.design.client.data.HasCategories;
import gwt.material.design.client.data.component.CategoryComponent;
import gwt.material.design.client.data.component.CategoryComponent.OrphanCategoryComponent;
import gwt.material.design.client.data.factory.Category;
import gwt.material.design.client.data.factory.CategoryComponentFactory;
import gwt.material.design.client.ui.table.TableSubHeader;

public class CustomCategoryFactory extends CategoryComponentFactory {

    @Override
    public CategoryComponent generate(DataView dataView, Category categoryPair) {
        CategoryComponent category = super.generate(dataView, categoryPair);

        if(!(category instanceof OrphanCategoryComponent)) {
            category = new CustomCategoryComponent(dataView, categoryPair.getName(), categoryPair.getId().toString(), false);
        }
        return category;
    }

    public static class CustomCategoryComponent extends CategoryComponent {
        public CustomCategoryComponent(HasCategories parent, String name, String id) {
            super(parent, name, id);
        }

        public CustomCategoryComponent(HasCategories parent, String name, String id, boolean openByDefault) {
            super(parent, name, id, openByDefault);
        }

        @Override
        protected void render(TableSubHeader subheader, int columnCount) {
            super.render(subheader, columnCount);

            subheader.setOpenIcon(IconType.FOLDER_OPEN);
            subheader.setCloseIcon(IconType.FOLDER);
        }
    }
}
