package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

/**
 * Created by garshasbi on 4/19/18.
 */

public class ProductFilterItem {

    public final static int HEADER = 1;
    public final static int CATEGORY_ITEM = 2;

    private int type;
    private CategoryItem categoryItem;

    public ProductFilterItem(int type) {
        this.type = HEADER;
    }

    public ProductFilterItem(CategoryItem categoryItem) {
        this.type = CATEGORY_ITEM;
        this.categoryItem = categoryItem;
    }

    public int getType() {
        return type;
    }

    public CategoryItem getCategoryItem() {
        return categoryItem;
    }

}
