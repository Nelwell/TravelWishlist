package android.bignerdranch.travelwishlist;

import java.text.DateFormat;
import java.util.Date;

public class Place {

    private String name;
    private String reason;
    private Date dateCreated;

    Place(String name, String reason) {
        this.name = name;
        this.reason = reason;
        this.dateCreated = new Date();
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return DateFormat.getDateInstance().format(dateCreated);
    }

    public String getReason() {
        return reason;
    }
}
