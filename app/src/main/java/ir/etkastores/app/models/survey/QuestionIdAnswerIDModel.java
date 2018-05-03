package ir.etkastores.app.models.survey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 5/3/18.
 */

public class QuestionIdAnswerIDModel {

    @SerializedName("QuestionId")
    private long questionId;

    @SerializedName("AnswerId")
    private long answerId;

    public QuestionIdAnswerIDModel(long questionId, long answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

}
