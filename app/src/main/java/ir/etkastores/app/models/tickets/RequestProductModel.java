package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

public class RequestProductModel {

    @SerializedName("Id")
    public long id;

    @SerializedName("Title")
    public String title;

    @SerializedName("Date")
    public String date;

    @SerializedName("Answer")
    public RequestProductAnswerModel answer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RequestProductAnswerModel getAnswer() {
        return answer;
    }

    public void setAnswer(RequestProductAnswerModel answer) {
        this.answer = answer;
    }

}
