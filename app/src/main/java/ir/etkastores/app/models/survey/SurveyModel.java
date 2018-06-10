package ir.etkastores.app.models.survey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garshasbi on 4/20/18.
 */

public class SurveyModel {

    @SerializedName("id")
    private int id;

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

    public int getId() {
        return id;
    }

    public static SurveyModel fromJson(String json) {
        try {
            return new Gson().fromJson(json, SurveyModel.class);
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }
}
