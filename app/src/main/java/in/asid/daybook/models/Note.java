package in.asid.daybook.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Note extends RealmObject {

    @Required
    private Date date;

    private String text;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
