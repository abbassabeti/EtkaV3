package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

public class DepartmentModel {

    @SerializedName("Id")
    private int id;

    @SerializedName("Title")
    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
