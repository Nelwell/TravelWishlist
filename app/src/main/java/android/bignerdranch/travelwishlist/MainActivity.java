package android.bignerdranch.travelwishlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bignerdranch.travelwishlist.db.PlaceRecord;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WishListClickListener {

    private static final String TAG = "MAIN_ACTIVITY";
    private RecyclerView mWishListRecyclerView;
    private RecyclerView.Adapter mWishListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Initialize widgets
    private Button mAddButton;
    private EditText mNewPlaceNameEditText;
    private EditText mReasonEditText;

    // Initialize Place view model
    private PlaceViewModel mPlaceViewModel;

    // Initialize List of Place objects
    private List<PlaceRecord> mPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaceViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        // Gets references to string resource IDs
        mWishListRecyclerView = findViewById(R.id.wish_list);
        mAddButton = findViewById(R.id.add_new_place);
        mNewPlaceNameEditText = findViewById(R.id.new_place_name);
        mReasonEditText =findViewById(R.id.reason);

        // Configure RecyclerView
        mWishListRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mWishListRecyclerView.setLayoutManager(mLayoutManager);

        mWishListAdapter = new WishListAdapter(this, this);
        ((WishListAdapter) mWishListAdapter).setNewPlaces(mPlaces);
        // Draws new item to RecyclerView
        mWishListRecyclerView.setAdapter(mWishListAdapter);

        // This doesn't do anything for the app, but can be helpful for debugging.
        mPlaceViewModel.getAllPlaceRecords().observe(this, new Observer<List<PlaceRecord>>() {
            @Override
            public void onChanged(List<PlaceRecord> place) {
                Log.d(TAG, "places are: " + place);
                mPlaces = place;
            }
        });

        // Create Place view model
        mPlaceViewModel = new PlaceViewModel(getApplication());

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets texts of filled out fields and converts to Strings
                String newPlace = mNewPlaceNameEditText.getText().toString();
                String reason = mReasonEditText.getText().toString();
                // Caps first letter of entries
                String newPlaceCap = newPlace.substring(0, 1).toUpperCase() + newPlace.substring(1);
                String reasonCap = reason.substring(0, 1).toUpperCase() + reason.substring(1);
                if (newPlaceCap.isEmpty() || reasonCap.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill out both fields.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Example entry
                PlaceRecord testPlace = new PlaceRecord("London", new Date(), "because");
                mPlaceViewModel.insert(testPlace);

                addPlace(newPlaceCap, reasonCap);
                // Add new place and reason, then notify Adapter that an item was inserted
//                mWishListAdapter.notifyItemInserted(0);
                mWishListAdapter.notifyItemInserted(mPlaces.size()-1); // The last element
                mNewPlaceNameEditText.getText().clear();
                mReasonEditText.getText().clear();
            }
        });
    }

    private void addPlace(String name, String reason) {
        PlaceRecord place = new PlaceRecord(
                name,
                new Date(), // current time/date
                reason);

        Log.d(TAG, "Added place: " + place);

        mPlaceViewModel.insert(place);
    }

    @Override
    public void onListClick(int position) {
        PlaceRecord place = mPlaces.get(position);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place.getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
        startActivity(mapIntent);
    }

    @Override
    public void onListLongClick(int position) {
        final int itemPosition = position;

        // Create pop-up asking user to confirm they want to delete location from list
        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position).getName()))
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPlaces.remove(itemPosition);
                        mWishListAdapter.notifyItemRemoved(itemPosition);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null) // No event handler needed for Cancel
                .create();

        confirmDeleteDialog.show();
    }
}
