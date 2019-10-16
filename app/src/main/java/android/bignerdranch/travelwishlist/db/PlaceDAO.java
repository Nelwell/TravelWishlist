package android.bignerdranch.travelwishlist.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlaceDAO {

    @Query("SELECT * FROM placeRecord ORDER BY name DESC LIMIT :results")
    LiveData<List<PlaceRecord>> getAllPlaceRecords(int results);

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Ignore new record for an existing place
    void insert(PlaceRecord... pr);

    @Delete
    void delete(PlaceRecord... pr);

    @Query("SELECT * FROM PlaceRecord WHERE name = :name LIMIT 1")
    LiveData<PlaceRecord> getRecordForName(String name);

    @Query("DELETE FROM placerecord WHERE id = :id")
    void delete(int id);

//    @Query("SELECT * FROM PlaceRecord")
//    LiveData<List<PlaceRecord>> getAllRecords();
}
