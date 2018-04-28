package ir.etkastores.app.models.survey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garshasbi on 4/20/18.
 */

public class SurveyModel {

    @SerializedName("title")
    private String title;

    @SerializedName("questions")
    private List<QuestionModel> questions;

    public String getTitle() {
        return title;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

}
