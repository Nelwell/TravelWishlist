package android.bignerdranch.travelwishlist;

import android.app.Application;
import android.bignerdranch.travelwishlist.db.PlaceRecord;
import android.bignerdranch.travelwishlist.db.PlaceRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/** Passes messages from View to Repository
 */
public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository mPlaceRepository;
    // Common to cache a copy of results of common queries here
    private LiveData<List<PlaceRecord>> mAllPlaceRecords;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        mPlaceRepository = new PlaceRepository(application);
        mAllPlaceRecords = mPlaceRepository.getAllPlaceRecords();
    }

    public LiveData<List<PlaceRecord>> getAllPlaceRecords() {
        return mAllPlaceRecords;
    }

    public LiveData<PlaceRecord> getRecordForName(String name) {
        return mPlaceRepository.getRecordForName(name);
    }

    public void insert(PlaceRecord place) {
        mPlaceRepository.insert(place);
    }

    public void delete(PlaceRecord placeRecord) {
        mPlaceRepository.delete(placeRecord);
    }

    public void delete(int id) {
        mPlaceRepository.delete(id);
    }
}
