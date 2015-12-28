package in.asid.daybook.models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Tag extends RealmObject {

    @Required
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
