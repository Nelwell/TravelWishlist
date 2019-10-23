package android.bignerdranch.travelwishlist.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/** Where app will access needed data */
// Define methods app will call to query database
public class PlaceRepository {

    private PlaceDAO placeDAO;

    public PlaceRepository(Application application) {
        PlaceDatabase db = PlaceDatabase.getDatabase(application);
        placeDAO = db.placeDAO();
    }

    // LiveData can wrap one item or a list of items inside it,
    // let's you know if something in db changes
    public LiveData<List<PlaceRecord>> getAllPlaceRecords() {
        return placeDAO.getAllPlaceRecords();
    }

    public LiveData<PlaceRecord> getRecordForName(String name) {
        return placeDAO.getRecordForName(name);
    }

    public void insert(PlaceRecord placeRecord) {
        // Insert record asynchronously in the background so other processes can continue seamlessly
        new InsertPlaceAsync(placeDAO).execute(placeRecord); // when called, calls doInBackground
        // method automatically and passes 'placeRecord' arg
    }

    public void delete(PlaceRecord placeRecord) {
        // Update record asynchronously in the background
        new DeletePlaceAsync(placeDAO).execute(placeRecord); // when called, calls doInBackground
        // method automatically and passes 'placeRecord' arg
    }

    public void delete(int id) {
        new DeletePlaceIDAsyncTask(placeDAO).execute(id); }


    // Database tasks must run the background, not on the UI thread
    static class InsertPlaceAsync extends AsyncTask<PlaceRecord, Void, Void> {

        private PlaceDAO dao;

        // Constructor
        InsertPlaceAsync(PlaceDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PlaceRecord... places) {
            dao.insert(places[0]);
            return null;
        }
    }

    // Database tasks must run in the background, not on the UI thread
    static class DeletePlaceAsync extends AsyncTask<PlaceRecord, Void, Void> {

        private PlaceDAO dao;

        // Constructor
        DeletePlaceAsync(PlaceDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PlaceRecord... places) {
            dao.delete(places[0]);
            return null;
        }
    }

    private static class DeletePlaceIDAsyncTask extends AsyncTask<Integer, Void, Void> {

        PlaceDAO dao;

        public DeletePlaceIDAsyncTask(PlaceDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            dao.delete(id[0]);
            return null;
        }
    }

}