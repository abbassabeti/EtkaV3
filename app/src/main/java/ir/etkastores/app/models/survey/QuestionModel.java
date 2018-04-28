package ir.etkastores.app.models.survey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garshasbi on 4/20/18.
 */

public class QuestionModel {

    @SerializedName("id")
    private long id;

    @SerializedName("text")
    private String text;

    @SerializedName("answers")
    private List<Answer> answers;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
