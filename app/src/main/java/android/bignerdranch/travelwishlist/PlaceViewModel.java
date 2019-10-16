package android.bignerdranch.travelwishlist;

import android.app.Application;
import android.bignerdranch.travelwishlist.db.PlaceRecord;
import android.bignerdranch.travelwishlist.db.PlaceRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// Passes message from View to Repository
public class PlaceViewModel extends AndroidViewModel {

    private PlaceRepository mPlaceRepository;
    // Common to cache a copy of results of common queries here
    private LiveData<List<PlaceRecord>> allPlaceRecords;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        mPlaceRepository = new PlaceRepository(application);
//        allPlaceRecords = mPlaceRepository.getAllPlaceRecords(results);
    }

    public LiveData<List<PlaceRecord>> getAllPlaceRecords(int results) {
        return mPlaceRepository.getAllPlaceRecords(results);
    }

    public LiveData<PlaceRecord> getRecordForName(String name) {
        return mPlaceRepository.getRecordForName(name);
    }

    public void insert(PlaceRecord placeRecord) {
        mPlaceRepository.insert(placeRecord);
    }

    public void delete(PlaceRecord placeRecord) {
        mPlaceRepository.delete(placeRecord);
    }

    public void delete(int id) {
        mPlaceRepository.delete(id);
    }
}
