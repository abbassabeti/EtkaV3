package ir.etkastores.app.models.survey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garshasbi on 5/3/18.
 */

public class SurveySubmitRequestModel {

    @SerializedName("SurveyId")
    private int surveyId;

    @SerializedName("Comment")
    private String comment;

    @SerializedName("Answers")
    private List<QuestionIdAnswerIDModel> answers;

    public SurveySubmitRequestModel(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<QuestionIdAnswerIDModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionIdAnswerIDModel> answers) {
        this.answers = answers;
    }

    public void addAnswer(QuestionIdAnswerIDModel item){
        if (answers == null) answers = new ArrayList<>();
        answers.add(item);
    }
}
