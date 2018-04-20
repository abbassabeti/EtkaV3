package ir.etkastores.app.models.survey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garshasbi on 4/20/18.
 */

public class SurveyModel {

    @SerializedName("Title")
    private String title;

    @SerializedName("Questions")
    private List<Question> questions;

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

}
