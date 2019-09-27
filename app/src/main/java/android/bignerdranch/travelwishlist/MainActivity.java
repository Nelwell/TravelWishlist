package android.bignerdranch.travelwishlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WishListClickListener {

    private RecyclerView mWishListRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Initialize widgets
    private Button mAddButton;
    private EditText mNewPlaceNameEditText;

    // Initialize List of Places
    private List<Place> mPlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Holds list of Places' name and date created
        mPlaces = new ArrayList<>();

        // Example places for testing
//        mPlaces.add(new Place("Tokyo"));
//        mPlaces.add(new Place("Budapest"));
//        mPlaces.add(new Place("Machu Picchu"));

        // Gets references to string resource IDs
        mWishListRecyclerView = findViewById(R.id.wish_list);
        mAddButton = findViewById(R.id.add_new_place);
        mNewPlaceNameEditText = findViewById(R.id.new_place_name);

        // Configure RecyclerView
        mWishListRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mWishListRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WishListAdapter(mPlaces, this);
        // Draws new item to RecyclerView
        mWishListRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPlace = mNewPlaceNameEditText.getText().toString();
                if (newPlace.isEmpty()) {
                    return;
                }

                // Add new place and notify Adapter that an item was inserted
                mPlaces.add(new Place(newPlace));
                mAdapter.notifyItemInserted(mPlaces.size() -1); // The last element
                mNewPlaceNameEditText.getText().clear();
            }
        });

    }

    @Override
    public void onListClick(int position) {
        Place place = mPlaces.get(position);
        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(place.getName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
        startActivity(mapIntent);

    }

    @Override
    public void onListLongClick(int position) {
        final int itemPosition = position;

        AlertDialog confirmDeleteDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_place_message, mPlaces.get(position).getName()))
                .setTitle(getString(R.string.delete_dialog_title))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPlaces.remove(itemPosition);
                        mAdapter.notifyItemRemoved(itemPosition);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null) // No event handler needed for Cancel
                .create();
        confirmDeleteDialog.show();

    }
}
