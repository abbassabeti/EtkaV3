package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

/**
 * Created by garshasbi on 4/20/18.
 */

public class CategoryItem {

    private String title;
    private long id;
    private boolean isSelected;

    public CategoryItem(String title, long id) {
        this.title = title;
        this.id = id;
    }

    public CategoryItem(String title, long id, boolean isSelected) {
        this.title = title;
        this.id = id;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void toggle(){
        if (isSelected){
            isSelected = false;
        }else{
            isSelected = true;
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
