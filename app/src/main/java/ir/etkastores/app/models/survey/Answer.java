package ir.etkastores.app.models.survey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 4/20/18.
 */

public class Answer {

    @SerializedName("id")
    private long id;

    @SerializedName("text")
    private String text;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
