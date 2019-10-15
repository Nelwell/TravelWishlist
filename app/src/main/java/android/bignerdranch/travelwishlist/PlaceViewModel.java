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

    private PlaceRepository repository;

    // Common to cache a copy of results of common queries here
    private LiveData<List<PlaceRecord>> allRecords;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        repository = new PlaceRepository(application);
        allRecords = repository.getAllRecords();
    }

    public LiveData<List<PlaceRecord>> getAllRecords() {
        return allRecords;
    }

    public LiveData<PlaceRecord> getRecordForName(String name) {
        return repository.getRecordForName(name);
    }

    public void insert(PlaceRecord placeRecord) {
        repository.insert(placeRecord);
    }

    public void update(PlaceRecord placeRecord) {
        repository.update(placeRecord);
    }

}
