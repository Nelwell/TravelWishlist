package android.bignerdranch.travelwishlist.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// Define methods app will call to query database
public class PlaceRepository {

    private PlaceDAO placeDAO;

    public PlaceRepository(Application application) {
        PlaceDatabase db = PlaceDatabase.getDatabase(application);
        placeDAO = db.placeDAO();
    }

    public void insert(PlaceRecord placeRecord) {
        // Insert record asynchronously in the background so other processes can continue seamlessly
        new InsertPlaceAsync(placeDAO).execute(placeRecord); // when called, calls doInBackground
        // method automatically and passes 'record' arg
    }

    // Database tasks must run the background, not on the UI thread
    static class InsertPlaceAsync extends AsyncTask<PlaceRecord, Void, Void> {

        private PlaceDAO placeDAO;

        // Constructor
        InsertPlaceAsync(PlaceDAO placeDAO) {
            this.placeDAO = placeDAO;
        }

        @Override
        protected Void doInBackground(PlaceRecord... placeRecords) {
            placeDAO.insert(placeRecords);
            return null;
        }
    }

    public void update(PlaceRecord placeRecord) {
        // Update record asynchronously in the background
        new UpdatePlaceAsync(placeDAO).execute(placeRecord); // when called, calls doInBackground
        // method automatically and passes 'placeRecord' arg
    }

    // Database tasks must run in the background, not on the UI thread
    static class UpdatePlaceAsync extends AsyncTask<PlaceRecord, Void, Void> {

        private PlaceDAO placeDAO;

        // Constructor
        UpdatePlaceAsync(PlaceDAO placeDAO) {
            this.placeDAO = placeDAO;
        }

        @Override
        protected Void doInBackground(PlaceRecord... placeRecords) {
            placeDAO.update(placeRecords);
            return null;
        }
    }
    // LiveData can wrap one item or a list of items inside it,
    // let's you know if something in db changes
    public LiveData<List<PlaceRecord>> getAllRecords() {
        return placeDAO.getAllRecords();
    }

    public LiveData<PlaceRecord> getRecordForName(String day) {
        return placeDAO.getRecordForName(day);
    }

}
