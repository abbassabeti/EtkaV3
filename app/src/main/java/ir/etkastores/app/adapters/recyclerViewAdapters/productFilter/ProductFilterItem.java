package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

import ir.etkastores.app.models.CategoryModel;

/**
 * Created by garshasbi on 4/19/18.
 */

public class ProductFilterItem {

    public final static int HEADER = 1;
    public final static int CATEGORY_ITEM = 2;

    private int type;
    private CategoryModel categoryItem;

    public ProductFilterItem(int type) {
        this.type = HEADER;
    }

    public ProductFilterItem(CategoryModel categoryItem) {
        this.type = CATEGORY_ITEM;
        this.categoryItem = categoryItem;
    }

    public int getType() {
        return type;
    }

    public CategoryModel getCategoryItem() {
        return categoryItem;
    }

}
