package android.bignerdranch.travelwishlist.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;

@Entity
public class PlaceRecord {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name;
    private Date dateCreated;
    private String reason;

    public PlaceRecord(@NonNull String name, Date dateCreated, String reason) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "PlaceRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", reason='" + reason + '\'' +
                '}';
    }
}