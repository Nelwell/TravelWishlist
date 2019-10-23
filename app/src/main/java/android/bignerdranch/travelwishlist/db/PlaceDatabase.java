package android.bignerdranch.travelwishlist.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/** Creates database on device */
// Implemented as a thread-safe Singleton
@Database(entities = {PlaceRecord.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PlaceDatabase extends RoomDatabase {

    private static volatile PlaceDatabase INSTANCE;

    public abstract PlaceDAO placeDAO(); // Abstract method

    static PlaceDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlaceDatabase.class) { // Only one thread can run this code at once
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlaceDatabase.class, "Place").build();
                }
            }
        }
        return INSTANCE;
    }
}
