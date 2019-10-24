package android.bignerdranch.travelwishlist;

import android.bignerdranch.travelwishlist.db.PlaceRecord;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private final static String TAG = "PLACE_LIST_ADAPTER";

    // Adapter's internal data store
    private List<PlaceRecord> mPlaces;

    // Click and long-click listener
    private WishListClickListener mListener;

    // Constructor to set data
    public WishListAdapter(MainActivity mainActivity, WishListClickListener listener) {
        this.mListener = listener;
    }

    public void setNewPlaces(List<PlaceRecord> mPlaces) {
        this.mPlaces = mPlaces;
        notifyDataSetChanged();
    }

    // Objects of this class represent the view for one data item
    static class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{

        LinearLayout layout;
        TextView nameTextView;
        TextView dateCreatedTextView;
        TextView reasonTextView;

        WishListClickListener mListener;

        WishListViewHolder(LinearLayout layout, WishListClickListener listener) {
            super(layout);
            this.mListener = listener;
            this.layout = layout;
            nameTextView = layout.findViewById(R.id.placeNameTextView);
            dateCreatedTextView = layout.findViewById(R.id.dateCreatedTextView);
            reasonTextView = layout.findViewById(R.id.reasonToGoTextView);
            layout.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Notify the listener of the event, and which item was clicked
            mListener.onListClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            // Notify listener of the event and which item was long-clicked
            mListener.onListLongClick(getAdapterPosition());
            return true; // Indicates event is consumed, no further processing.
            // If this is false, in this app, the click event is fired too.
        }
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get a reference to the wish_list_element LinearLayout container and inflate in, in this context
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_element, parent, false);
        // Create a new viewHolder, to contain this LinearLayout
        WishListViewHolder viewHolder = new WishListViewHolder(layout, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        // Configures a ViewHolder to display the data for the given position
        // In Android terminology, bind the view and its data
        PlaceRecord place = mPlaces.get(position);
        holder.nameTextView.setText(place.getName());
        holder.dateCreatedTextView.setText("Created on " + place.getDateCreated());
        holder.reasonTextView.setText(place.getReason());
    }

    @Override
    public int getItemCount() {
        if (mPlaces == null) {
            return 0;
        } else {

        return mPlaces.size();
        }
    }
}
