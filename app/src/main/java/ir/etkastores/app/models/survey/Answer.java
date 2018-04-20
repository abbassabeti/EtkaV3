package ir.etkastores.app.models.survey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 4/20/18.
 */

public class Answer {

    @SerializedName("Id")
    private long id;

    @SerializedName("Text")
    private String text;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

}
